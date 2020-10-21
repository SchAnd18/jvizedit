package jvizedit.mvc.content;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jvizedit.mvc.IController;

public interface IContentChangeListener {
	
	default void onRootChange(IController oldRootController, IController newRootController) {}
	
	default void onContentChange(IContentChange contentChange) {}
	
	interface IContentChange {
		
		Set<IController> getAddedControllers();
		
		Set<IController> getRemovedControllers();
		
		Set<IController> getRelocatedControllers();
		
		default List<Object> getAddedModelObjects() {
			final Set<Object> removedObjects = getRemovedControllers().stream().map(IController::getModel).collect(Collectors.toSet());
			final List<Object> newObjects = getAddedControllers().stream().map(IController::getModel).filter(m->!removedObjects.contains(m)).collect(Collectors.toList());
			return newObjects;
		}
		
		default List<Object> getRemovedModelObjects() {
			final Set<Object> addedObjects = getAddedControllers().stream().map(IController::getModel).collect(Collectors.toSet());
			final List<Object> removedObjects = getRemovedControllers().stream().map(IController::getModel).filter(m->!addedObjects.contains(m)).collect(Collectors.toList());
			return removedObjects;
		}
		
	}
	
	public static class ContentChange implements IContentChange {
		
		private final Set<IController> addedControllers;
		
		//TODO: removed controllers should be a map to see old parents (removed controllers current parent should be null)
		private final Set<IController> removedControllers;
		
		// TODO: relocated controllers should be a map to see old parents
		private final Set<IController> relocatedControllers;
		
		
		public ContentChange (Set<IController> added, Set<IController> removed, Set<IController> relocatedControllers) {
			this.addedControllers = added;
			this.removedControllers = removed;
			this.relocatedControllers = relocatedControllers;
		}

		@Override
		public Set<IController> getAddedControllers() {
			return addedControllers;
		}

		@Override
		public Set<IController> getRemovedControllers() {
			return removedControllers;
		}
		
		@Override
		public Set<IController> getRelocatedControllers() {
			return relocatedControllers;
		}
	}
	
}
