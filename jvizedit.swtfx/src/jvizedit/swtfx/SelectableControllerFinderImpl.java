package jvizedit.swtfx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import jvizedit.control.core.events.IMouseEvent;
import jvizedit.control.selection.ISelectableController;
import jvizedit.control.selection.ISelectableFinder;
import jvizedit.mvc.IController;
import jvizedit.mvc.content.core.IContentManager;
import jvizedit.swtfx.events.FxMouseEvent;

public class SelectableControllerFinderImpl implements ISelectableFinder {

	private final IContentManager objectContent;

	public SelectableControllerFinderImpl(final IContentManager objectContent) {
		this.objectContent = objectContent;
	}

	protected boolean isSelectable(final IController controller) {
		if (!(controller instanceof ISelectableController)) {
			return false;
		}
		final ISelectableController selectableController = (ISelectableController) controller;
		return selectableController.isSelectable();
	}

	@Override
	public ISelectableController findControllerAt(final double x, final double y, final IMouseEvent mouseDown) {
		if (!(mouseDown instanceof FxMouseEvent)) {
			throw new IllegalArgumentException("Unexpected mouse event implementation!");
		}
		final FxMouseEvent fxMouseDown = (FxMouseEvent) mouseDown;
		IController controller = findController(fxMouseDown.getRealEvent());
		while (controller != null) {
			if (isSelectable(controller)) {
				return (ISelectableController) controller;
			}
			controller = controller.getParent();
		}
		return null;
	}

	@Override
	public Collection<ISelectableController> findControllersIn(final double x, final double y, final double width,
			final double height) {
		final Bounds selectionBoundsScene = new BoundingBox(x, y, width, height);
		final List<ISelectableController> result = new ArrayList<>();
		final List<IController> root = Collections.singletonList(this.objectContent.getRootController());
		findInBounds(root, selectionBoundsScene, result);
		return result;
	}

	/*
	 * TODO: Optimization possible This method goes down the node tree and tries to
	 * find the selectable nodes. It might be expensive because it visits the full
	 * tree In an editor where selection on different layers is allowed at the same
	 * time, this might be ok. In an editor that only allows selection on one
	 * defined layer in the node tree, this might be far to expensive.
	 *
	 * Possible solution: Currently there is the flag
	 * ISelectableController::isSelectable(). When it is false and the controller
	 * has children it goes down the tree and looks for potentially further
	 * selectables. Extend this to an enum: - SELECTABLE ----------------- The node
	 * can be selected. - NOT-SELECTABLE-NODE -------- The node can not be selected,
	 * but maybe it's children. - NOT-SELECTABLE-TREE-PART --- The node and its
	 * children can not be selected.
	 */
	private void findInBounds(final Collection<IController> currentChildren, final Bounds selectionBoundsScene,
			final List<ISelectableController> result) {

		// it is assumed that many children are contained in the same parent node,
		// therefore we cache the calculated selection bounds for the parents
		final Map<Parent, Bounds> selectionBoundsInParentMap = new HashMap<>();
		for (final IController child : currentChildren) {

			final boolean isSelectable = isSelectable(child);
			final boolean hasChildren = child.hasControllerChildren();

			if ((isSelectable == false) && (hasChildren == false)) {
				continue;
			}

			final Object view = child.getView();
			if (!(view instanceof Node)) {
				throw new RuntimeException("Unexpected view type " + view);
			}
			final Node node = (Node) view;
			final Parent parent = node.getParent();

			Bounds selectionBoundsInParent = selectionBoundsInParentMap.get(parent);
			if (selectionBoundsInParent == null) {
				selectionBoundsInParent = parent.sceneToLocal(selectionBoundsScene);
				selectionBoundsInParentMap.put(parent, selectionBoundsInParent);
			}

			final Bounds boundsInParent = node.getBoundsInParent();

			if (isSelectable && selectionBoundsInParent.contains(boundsInParent)) {
				final ISelectableController selectable = (ISelectableController) child;
				result.add(selectable);
				continue;
			}

			if (hasChildren && selectionBoundsInParent.intersects(boundsInParent)) {
				findInBounds(child.getControllerChildren(), selectionBoundsScene, result);
			}
		}
	}

	@Override
	public Collection<ISelectableController> findControllersIntersecting(final int x, final int y, final int width,
			final int height) {
		final Bounds selectionBoundsScene = new BoundingBox(x, y, width, height);
		final List<ISelectableController> result = new ArrayList<>();
		final List<IController> root = Collections.singletonList(this.objectContent.getRootController());
		findIntersectionsWithBounds(root, selectionBoundsScene, result);
		return result;
	}

	// TODO: Optimization possible (see comment above)
	private boolean findIntersectionsWithBounds(final Collection<IController> currentChildren,
			final Bounds selectionBoundsScene, final List<ISelectableController> result) {
		boolean foundIntersectingSelectables = false;
		// it is assumed that many children are contained in the same parent node,
		// therefore we cache the calculated selection bounds for the parents
		final Map<Parent, Bounds> selectionBoundsInParentMap = new HashMap<>();
		for (final IController child : currentChildren) {

			final boolean isSelectable = isSelectable(child);
			final boolean hasChildren = child.hasControllerChildren();

			if ((isSelectable == false) && (hasChildren == false)) {
				continue;
			}

			final Object view = child.getView();
			if (!(view instanceof Node)) {
				throw new RuntimeException("Unexpected view type " + view);
			}
			final Node node = (Node) view;
			final Parent parent = node.getParent();

			Bounds selectionBoundsInParent = selectionBoundsInParentMap.get(parent);
			if (selectionBoundsInParent == null) {
				selectionBoundsInParent = parent.sceneToLocal(selectionBoundsScene);
				selectionBoundsInParentMap.put(parent, selectionBoundsInParent);
			}

			final Bounds boundsInParent = node.getBoundsInParent();

//			if(isSelectable && selectionBoundsInParent.contains(boundsInParent)) {
//				continue;
//			}

			if (selectionBoundsInParent.intersects(boundsInParent)) {
				boolean foundInChildren = false;
				if (hasChildren) {
					foundInChildren = findIntersectionsWithBounds(child.getControllerChildren(), selectionBoundsScene,
							result);
				}
				if (foundInChildren) {
					foundIntersectingSelectables = true;
				} else if (isSelectable) {
					final ISelectableController selectable = (ISelectableController) child;
					result.add(selectable);
					foundIntersectingSelectables = true;
				}
			}
		}

		return foundIntersectingSelectables;
	}

	public static IController findController(final MouseEvent event) {
		if (!(event.getTarget() instanceof Node)) {
			return null;
		}
		final Node node = (Node) event.getTarget();
		return findController(node);
	}

	private static IController findController(final Object eventSource) {
		if (!(eventSource instanceof Node)) {
			return null;
		}
		Node node = (Node) eventSource;
		while (node != null) {
			final Object userData = node.getUserData();
			if (userData instanceof IController) {
				return (IController) userData;
			}
			node = node.getParent();
		}
		return null;
	}
}
