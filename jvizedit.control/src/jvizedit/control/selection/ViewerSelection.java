package jvizedit.control.selection;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jvizedit.mvc.IController;
import jvizedit.mvc.IControllerBase;
import jvizedit.mvc.content.IContentChangeListener;
import jvizedit.mvc.content.core.IContentManager;

public class ViewerSelection implements IContentChangeListener {

	private final boolean contentIsController;

	private final List<IViewerSelectionChangeListener> selectionChangedListeners = new ArrayList<>();

	private final LinkedHashSet<Object> selectedObjects = new LinkedHashSet<>();

	private final IContentManager modelContent;

	private ViewerSelectionUpdate currentUpdate;

	public ViewerSelection(final IContentManager modelContent) {
		this(modelContent, false);
	}

	public ViewerSelection(final IContentManager modelContent, final boolean contentIsController) {
		this.modelContent = modelContent;
		this.modelContent.addContentChangeListener(this);
		this.contentIsController = contentIsController;
	}

	public ViewerSelectionUpdate startSelectionUpdate() {
		if (this.currentUpdate == null) {
			this.currentUpdate = new ViewerSelectionUpdate();
		}
		return this.currentUpdate;
	}

	@Override
	public void onContentChange(final IContentChange contentChange) {
		/*
		 * Set the selection status for new controllers after content change. This is
		 * important because their model-objects might had already been added to the
		 * selection
		 */
		contentChange.getAddedControllers().stream() //
				.filter(ISelectableController.class::isInstance) //
				.map(ISelectableController.class::cast)//
				.forEach(sc -> {
					final Object model = sc.getModel();
					final boolean isSelected = this.selectedObjects.contains(model);
					sc.setSelectionStatus(isSelected);
				});
	}

	private void checkType(final Object model) {
		if (this.contentIsController) {
			return;
		}
		if (model instanceof IController) {
			throw new IllegalArgumentException("Use model, not controller here.");
		}
	}

	public boolean isSelected(final Object model) {
		return this.selectedObjects.contains(model);
	}

	public List<ISelectableController> getSelectedControllers() {
		return this.selectedObjects.stream().map(this.modelContent::getController)
				.filter(ISelectableController.class::isInstance).map(ISelectableController.class::cast)
				.collect(Collectors.toList());
	}

	public <T> List<T> getSelectedControllers(final Class<T> controllerType) {
		return this.selectedObjects.stream().map(this.modelContent::getController).filter(controllerType::isInstance)
				.map(controllerType::cast).collect(Collectors.toList());
	}

	public List<Object> getSelectedObjects() {
		return this.getSelectedControllers(ISelectableController.class).stream().map(IControllerBase::getModel)
				.collect(Collectors.toList());
	}

	private void notifySelectionChange() {
		this.selectionChangedListeners.forEach(c -> c.onSelectionChange(this));
	}

	public void addSelectionChangeListener(final IViewerSelectionChangeListener listener) {
		this.selectionChangedListeners.add(listener);
	}

	public void removeSelectionChangeListener(final IViewerSelectionChangeListener listener) {
		this.selectionChangedListeners.remove(listener);
	}

	public interface IViewerSelectionChangeListener {

		void onSelectionChange(ViewerSelection viewerSelection);

	}

	public class ViewerSelectionUpdate implements AutoCloseable {

		private boolean clearSelection = false;

		private final Set<Object> addToSelection = new LinkedHashSet<>();

		private final Set<Object> removeFromSelection = new LinkedHashSet<>();

		public void addToSelection(final Object object) {
			checkType(object);
			this.addToSelection.add(object);
			this.removeFromSelection.remove(object);
		}

		public void removeFromSelection(final Object object) {
			checkType(object);
			this.removeFromSelection.add(object);
			this.addToSelection.remove(object);
		}

		public void clearSelection() {
			this.clearSelection = true;
			this.addToSelection.clear();
			this.removeFromSelection.clear();
		}

		public boolean isSelectedBeforeUpdate(final Object object) {
			return ViewerSelection.this.selectedObjects.contains(object);
		}

		public boolean isSelected(final Object object) {
			final boolean isSelectedBeforeUpdate = isSelectedBeforeUpdate(object);
			if (isSelectedBeforeUpdate) {
				final boolean isRemoved = this.removeFromSelection.contains(object);
				return !isRemoved;
			} else {
				final boolean isAdded = this.addToSelection.contains(object);
				return isAdded;
			}
		}

		@Override
		public void close() throws RuntimeException {
			ViewerSelection.this.currentUpdate = null;
			if (this.clearSelection) {
				this.removeFromSelection.addAll(ViewerSelection.this.selectedObjects);
				this.removeFromSelection.removeAll(this.addToSelection); // an object to add could be in the list
			}
			this.addToSelection.forEach(o -> {
				final boolean added = ViewerSelection.this.selectedObjects.add(o);
				if (added) {
					ViewerSelection.this.modelContent.getControllerOfType(ISelectableController.class, o)
							.ifPresent(c -> {
								c.setSelectionStatus(true);
							});
				}
			});
			this.removeFromSelection.forEach(o -> {
				final boolean removed = ViewerSelection.this.selectedObjects.remove(o);
				if (removed) {
					ViewerSelection.this.modelContent.getControllerOfType(ISelectableController.class, o)
							.ifPresent(c -> {
								c.setSelectionStatus(false);
							});
				}
			});
			notifySelectionChange();
		}

	}

}
