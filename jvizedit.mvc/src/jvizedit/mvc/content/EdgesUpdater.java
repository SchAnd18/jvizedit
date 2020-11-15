package jvizedit.mvc.content;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jvizedit.mvc.IController;
import jvizedit.mvc.IEdgeController;
import jvizedit.mvc.content.IContentChangeListener.ContentChange;

public class EdgesUpdater {

	private ContentManager contentManager;

	public EdgesUpdater(ContentManager contentManager) {
		this.contentManager = contentManager;
	}

	public void update(ContentChange nodeContentChange, Set<IEdgeController> invalidatedEdges) {
		invalidateEdges(nodeContentChange, contentManager, invalidatedEdges);
		performUpdate(invalidatedEdges);
	}

	private void invalidateEdges(final ContentChange nodeContentChange, final ContentManager contentManager,
			Set<IEdgeController> invalidatedEdges) {

		// invalidate all edge controllers that are connected to removed controllers
		nodeContentChange.getRemovedControllers().stream() //
				.map(contentManager::getConnectedEdgeControllers) //
				.flatMap(Collection::stream) //
				.forEach(invalidatedEdges::add);

		/*
		 * invalidate all edge controllers that are connected to a relocated or child of
		 * a relocated controller, but NOT inside of one of these controllers
		 */
		final Set<IController> relocatedWithChildren = getAllControllers(nodeContentChange.getRelocatedControllers());
		getRelocatedEdgeControllers(relocatedWithChildren).forEach(invalidatedEdges::add);
	}

	private Set<IEdgeController> getRelocatedEdgeControllers(Set<IController> controllers) {
		final Set<IEdgeController> relocatedControllers = new HashSet<>();
		for (IController controller : controllers) {
			final Set<IEdgeController> edgeControllers = contentManager.getConnectedEdgeControllers(controller);
			for (IEdgeController edgeController : edgeControllers) {
				final boolean added = relocatedControllers.add(edgeController);
				final boolean controllerIsContained = !added;
				if (controllerIsContained) {
					relocatedControllers.remove(edgeController);
					// TODO consider a check to make sure that controller is not added a third or
					// further times
				}
			}
		}
		return relocatedControllers;
	}

	private void performUpdate(Set<IEdgeController> invalidatedEdges) {
		final Map<Object, IEdgeController> edgeControllerMap = contentManager.getEdgeControllerMap();

		Collection<?> edges = contentManager.getModelEdges();

		final List<IEdgeController> edgeControllerToRemove = new ArrayList<>();

		// check what connections to remove and remove them
		final Set<Object> edgeModelToRemove = new HashSet<>(edgeControllerMap.keySet());
		edgeModelToRemove.removeAll(edges);
		edgeModelToRemove.forEach(e -> {
			final IEdgeController ec = contentManager.getEdgeController(e);
			if (ec != null) {
				edgeControllerToRemove.add(ec);
			}
		});

		// check what connections to add and add them (and invalidate them)
		final Set<Object> edgeModelToAdd = new HashSet<>(edges);
		edgeModelToAdd.removeAll(edgeControllerMap.keySet());
		edgeModelToAdd.forEach(e -> {
			final IEdgeController ec = contentManager.createEdgeController(e);
			invalidatedEdges.add(ec);
		});

		// check what connections can not be shown
		invalidatedEdges.forEach(ec -> {
			final Object sourceNode = ec.getSourceNode();
			final Object targetNode = ec.getTargetNode();
			final IController newSrcController = contentManager.getController(sourceNode);
			final IController newTargetController = contentManager.getController(targetNode);

			final IController currentSrcController = ec.getConnectedSourceController();
			final IController currentTargetController = ec.getConnectedTargetController();

			if (newSrcController == null || newTargetController == null) {
				edgeControllerToRemove.add(ec);
			} else {
				contentManager.updateEdgeControllerConnection(ec, currentSrcController, newSrcController);
				contentManager.updateEdgeControllerConnection(ec, currentTargetController, newTargetController);

				ec.updateView(newSrcController, newTargetController); // this update can not be collected
			}
		});

		for (IEdgeController ec : edgeControllerToRemove) {
			edgeControllerMap.remove(ec.getModel());
			final IController currentSrcController = ec.getConnectedSourceController();
			final IController currentTargetController = ec.getConnectedTargetController();
			if (currentSrcController != null) {
				contentManager.updateEdgeControllerConnection(ec, currentSrcController, null);
			}
			if (currentTargetController != null) {
				contentManager.updateEdgeControllerConnection(ec, currentTargetController, null);
			}
			ec.dispose();
		}
	}

	private Set<IController> getAllControllers(Collection<IController> controllers) {
		final List<IController> result = new ArrayList<>();
		result.addAll(controllers);
		controllers.forEach(c -> {
			getAllControllers(c, result);
		});
		return new HashSet<IController>(result);
	}

	private void getAllControllers(IController parent, List<IController> result) {
		result.addAll(parent.getControllerChildren());
		parent.getControllerChildren().forEach(c -> {
			getAllControllers(c, result);
		});
	}

}
