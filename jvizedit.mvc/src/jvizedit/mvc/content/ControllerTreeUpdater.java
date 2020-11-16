package jvizedit.mvc.content;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import jvizedit.mvc.IController;
import jvizedit.mvc.content.IContentChangeListener.ContentChange;
import jvizedit.mvc.content.core.IContentHandler;
import jvizedit.mvc.content.core.IContentManager;
import jvizedit.mvc.content.core.IContentUpdate;

public class ControllerTreeUpdater {

	public ContentChange update(final Set<IController> initiallyInvalidatedControllers, final IContentManager contentManager) {

		final Map<IController, List<IController>> reusedControllerToNewParentsMap = new HashMap<>();
		final Map<IController, IContentUpdate<IController>> contentUpdates = new HashMap<>();
		final Map<IController, Runnable> updateRunnableMap = new HashMap<>();

		Set<IController> invalidatedControllers = initiallyInvalidatedControllers;
		while (!invalidatedControllers.isEmpty()) {
			final Set<IController> furtherInvalidatedControllers = new HashSet<>();
			for (final IController invController : invalidatedControllers) {

				final ControllerUpdateContext context = new ControllerUpdateContext(contentManager);
				createContentUpdate(invController, updateRunnableMap, contentUpdates, context);

				final Set<IController> createdControllers = context.getCreatedControllers();
				furtherInvalidatedControllers.addAll(createdControllers);

				final Set<IController> reusedControllers = context.getReusedControllers();
				for (final IController reusedController : reusedControllers) {
					final IController parent = reusedController.getParent();
					if (parent != null) {
						furtherInvalidatedControllers.add(parent);
						reusedControllerToNewParentsMap.computeIfAbsent(reusedController, __ -> new ArrayList<>()).add(invController);
					}
				}
			}
			// continue to update the old parents that are not updated yet
			furtherInvalidatedControllers.removeAll(updateRunnableMap.keySet());
			invalidatedControllers = furtherInvalidatedControllers;
		}

		final Map<IController, Set<IController>> reusedControllerToParentMap = createControllerToParentMap(reusedControllerToNewParentsMap,
				contentUpdates);

		// all controllers that had somewhere been removed and never reused anywhere
		// else. They are removed!
		final Set<IController> actuallyRemoved = contentUpdates.values().stream().flatMap(c -> c.getRemoved().stream())
				.collect(Collectors.toSet());
		actuallyRemoved.removeAll(reusedControllerToNewParentsMap.keySet());

		// after this, allRemoved will contain all nodes hat have been directly removed
		// and their children that are removed because of it's removed parents
		// this can also include elements that had been added if they where added
		// somewhere below a parent that had been removed
		//
		// reusedControllerToParentMap will contain only the parents that are not
		// removed
		final List<IController> allRemoved = new ArrayList<>(actuallyRemoved);
		for (final IController removedParent : actuallyRemoved) {
			collectAllRemovedChildren(removedParent, reusedControllerToParentMap, allRemoved);
		}

		// validate if some reused controller still has more than one parent
		for (final Entry<IController, Set<IController>> e : reusedControllerToParentMap.entrySet()) {
			final IController child = e.getKey();
			final Set<IController> parents = e.getValue();
			if (parents.size() > 1) {
				throw createMultipleParentsException(child, parents);
			}
			// perform relocation (might be better to do that later after remove and before
			// update
			// TODO: consider that new created elements can be also relocated, but should no
			// be in the result of relocated-elements in the update notification
			// This means that not every IController where relocatToNewParent is called is
			// actually in the relocated-elements list, this might be confusing to the user
			// Possible solutions => Send parent information always with update, store
			// child->parent relation in ContentManager?
			if (parents.size() == 1) {
				final IController newParent = parents.iterator().next();
				if (child.getParent() != newParent) {
					child.relocateToNewParent(newParent);
				}
			}
		}

		// dispose all removed nodes
		allRemoved.forEach(c -> {
			if (!c.isDisposed()) {
				c.dispose();
			}
			updateRunnableMap.remove(c);
		});

		// perform updates
		updateRunnableMap.values().forEach(Runnable::run);

		//// prepare report
		// determine what are the actually new nodes (all nodes, added somewhere, not
		//// removed and not reused)
		final Set<IController> allNew = contentUpdates.values().stream().flatMap(u -> u.getAdded().stream()).collect(Collectors.toSet());
		allNew.removeAll(reusedControllerToParentMap.keySet());
		allNew.removeAll(allRemoved);

		final Set<IController> allReused = new HashSet<>(reusedControllerToParentMap.keySet());
		allReused.removeAll(allRemoved);

		return new ContentChange(allNew, new HashSet<>(allRemoved), allReused);
	}

	private RuntimeException createMultipleParentsException(final IController child, final Set<IController> parents) {
		return new RuntimeException(child.getModel() + " has multible parents after update");
	}

	private void collectAllRemovedChildren(final IController parentRemovedController,
			final Map<IController, Set<IController>> reusedControllerToParentMap, final List<IController> allRemovedResult) {
		final IContentHandler<IController> contentHandler = parentRemovedController.getContentHandler();
		if (contentHandler == null) {
			return;
		}
		for (final IController child : contentHandler.getContent()) {
			final Set<IController> allParents = reusedControllerToParentMap.get(child);
			final boolean isReusedController = allParents != null;
			final boolean removeChild;
			if (isReusedController) {
				allParents.remove(parentRemovedController); // remove this parent because it is not part of tree anymore
				final boolean allParentsRemoved = allParents.isEmpty();
				removeChild = allParentsRemoved;
			} else {
				removeChild = true;
			}
			if (removeChild) {
				allRemovedResult.add(child);
				collectAllRemovedChildren(child, reusedControllerToParentMap, allRemovedResult);
			}
		}

	}

	private Map<IController, Set<IController>> createControllerToParentMap(
			final Map<IController, List<IController>> reusedControllerToNewParentsMap,
			final Map<IController, IContentUpdate<IController>> contentUpdates) {
		final Map<IController, Set<IController>> result = new HashMap<>();
		for (final Entry<IController, List<IController>> e : reusedControllerToNewParentsMap.entrySet()) {
			final Set<IController> parents = new HashSet<>(e.getValue());
			final IController c = e.getKey();
			final IController originalParent = c.getParent();
			final IContentUpdate<IController> update = contentUpdates.get(originalParent);
			final boolean wasRemovedFromOriginalParent = update.getRemoved().contains(c);
			if (!wasRemovedFromOriginalParent) {
				parents.add(originalParent);
			}
			result.put(c, parents);
		}
		return result;
	}

	private void createContentUpdate(final IController controller, final Map<IController, Runnable> updateRunnableMap,
			final Map<IController, IContentUpdate<IController>> contentUpdates, final ControllerUpdateContext context) {
		final IContentHandler<IController> contentHandler = controller.getContentHandler();
		final IContentUpdate<IController> update;
		if (contentHandler == null) {
			update = null;
		} else {
			contentHandler.updateContent(context);
			update = contentHandler.getLastUpdate();
			contentUpdates.put(controller, update);
		}

		updateRunnableMap.put(controller, () -> {
			controller.updateView();
		});
	}

}
