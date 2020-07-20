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
	
	private final LinkedHashSet<Object> selectedObjects = new LinkedHashSet<Object>();
	
	private final IContentManager modelContent;
	
	private ViewerSelectionUpdate currentUpdate;
	
	public ViewerSelection(final IContentManager modelContent) {
		this(modelContent,false);
	}
	
	public ViewerSelection(final IContentManager modelContent, boolean contentIsController) {
		this.modelContent = modelContent;
		this.modelContent.addContentChangeListener(this);
		this.contentIsController = contentIsController;
	}
	
	public ViewerSelectionUpdate startSelectionUpdate() {
		if(currentUpdate == null) {
			currentUpdate = new ViewerSelectionUpdate();
		}
		return currentUpdate;
	}
	
	@Override
	public void onContentChange(IContentChange contentChange) {
		/*
		 * Set the selection status for new controllers after content change.
		 * This is important because their model-objects might had already been added to the selection
		 */
		contentChange.getAddedControllers().stream() //
			.filter(ISelectableController.class::isInstance) //
			.map(ISelectableController.class::cast)//
			.forEach(sc -> {
				final Object model = sc.getModel();
				final boolean isSelected = selectedObjects.contains(model);
				sc.setSelectionStatus(isSelected);
			});
	}
	
	private void checkType(Object model) {
		if(contentIsController) {
			return;
		}
		if(model instanceof IController) {
			throw new IllegalArgumentException("Use model, not controller here.");
		}
	}
	
	public boolean isSelected(Object model) {
		return selectedObjects.contains(model);
	}

	public Set<ISelectableController> getSelectedControllers() {
		return selectedObjects.stream().map(modelContent::getController).filter(ISelectableController.class::isInstance).map(ISelectableController.class::cast).collect(Collectors.toSet());
	}
	
	public <T> Set<T> getSelectedControllers(Class<T> controllerType) {
		return selectedObjects.stream().map(modelContent::getController).filter(controllerType::isInstance).map(controllerType::cast).collect(Collectors.toSet());
	}
	
	public Set<Object> getSelectedObjects() {
		return this.getSelectedControllers(ISelectableController.class).stream().map(IControllerBase::getModel).collect(Collectors.toSet());
	}
	
	private void notifySelectionChange() {
		selectionChangedListeners.forEach(c->c.onSelectionChange(this));
	}
	
	public void addSelectionChangeListener(IViewerSelectionChangeListener listener) {
		this.selectionChangedListeners.add(listener);
	}
	
	public void removeSelectionChangeListener(IViewerSelectionChangeListener listener) {
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
		
		public boolean isSelectedBeforeUpdate(Object object) {
			return selectedObjects.contains(object);
		}
		
		public boolean isSelected(Object object) {
			final boolean isSelectedBeforeUpdate = isSelectedBeforeUpdate(object);
			if(isSelectedBeforeUpdate) {
				final boolean isRemoved = removeFromSelection.contains(object);
				return !isRemoved;
			} else {
				final boolean isAdded = addToSelection.contains(object);
				return isAdded;
			}
		}

		@Override
		public void close() throws RuntimeException {
			currentUpdate = null;
			if(clearSelection) {
				removeFromSelection.addAll(selectedObjects);
			}
			addToSelection.forEach(o -> {
				final boolean added = selectedObjects.add(o);
				if(added) {
					modelContent.getControllerOfType(ISelectableController.class, o).ifPresent(c -> {
						c.setSelectionStatus(true);
					});
				}
			});
			removeFromSelection.forEach(o -> {
				final boolean removed = selectedObjects.remove(o);
				if(removed) {
					modelContent.getControllerOfType(ISelectableController.class, o).ifPresent(c -> {
						c.setSelectionStatus(false);
					});
				}
			});
			notifySelectionChange();
		}
		
	}
	
}
