package jvizedit.control.selection;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import jvizedit.control.core.events.IMouseEvent;
import jvizedit.control.selection.SelectOnClick.ISelectOnClickListener;
import jvizedit.control.selection.SelectOnClick.SelectionUpdate;
import jvizedit.control.selection.SelectionAreaOnDrag.ESelectionAreaEvent;
import jvizedit.control.selection.SelectionAreaOnDrag.ISelectionAreaListener;
import jvizedit.control.selection.ViewerSelection.ViewerSelectionUpdate;

public class ViewerSelectionUpdater implements ISelectionAreaListener, ISelectOnClickListener {

	private final ViewerSelection viewerSelection;
	private final ISelectableFinder selectableFinder;

	public ViewerSelectionUpdater(final ViewerSelection viewerSelection, final ISelectableFinder selectableFinder) {
		this.viewerSelection = viewerSelection;
		this.selectableFinder = selectableFinder;
	}

	@Override
	public void updateSelection(final IMouseEvent mouseEvent, final ISelectableController controller, final SelectionUpdate update) {
		try (ViewerSelectionUpdate selectionUpdate = this.viewerSelection.startSelectionUpdate()) {
			final Object model = controller.getModel();
			switch (update) {
			case SET:
				selectionUpdate.clearSelection();
				selectionUpdate.addToSelection(model);
				break;
			case SET_IF_NOT_SELECTED:
				if (!selectionUpdate.isSelected(model)) {
					selectionUpdate.clearSelection();
					selectionUpdate.addToSelection(model);
				}
				break;
			case TOGGLE:
				if (selectionUpdate.isSelected(model)) {
					selectionUpdate.removeFromSelection(model);
				} else {
					selectionUpdate.addToSelection(model);
				}
				break;
			}
		}
	}

	@Override
	public void clearSelection(final IMouseEvent mouseEvent) {
		try (ViewerSelectionUpdate selectionUpdate = this.viewerSelection.startSelectionUpdate()) {
			selectionUpdate.clearSelection();
		}
	}

	@Override
	public void onSelectionArea(final ESelectionAreaEvent event, final double x, final double y, final double width, final double height,
			final boolean toggle) {
		if (event != ESelectionAreaEvent.apply) {
			return;
		}
		try (ViewerSelectionUpdate selectionUpdate = this.viewerSelection.startSelectionUpdate()) {
			final Collection<ISelectableController> controllers = this.selectableFinder.findControllersIn(x, y, width, height);
			final Set<Object> objects = controllers.stream().map(ISelectableController::getModel).collect(Collectors.toSet());
			if (toggle) {
				final Set<Object> alreadySelected = new HashSet<>(this.viewerSelection.getSelectedObjects());
				alreadySelected.retainAll(objects);
				objects.forEach(selectionUpdate::addToSelection);
				alreadySelected.forEach(selectionUpdate::removeFromSelection);
			} else {
				selectionUpdate.clearSelection();
				objects.forEach(selectionUpdate::addToSelection);
			}
		}
	}

}
