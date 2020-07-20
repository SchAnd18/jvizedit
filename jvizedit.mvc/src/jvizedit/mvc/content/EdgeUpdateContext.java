package jvizedit.mvc.content;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import jvizedit.mvc.IController;
import jvizedit.mvc.IEdgeContainer;
import jvizedit.mvc.IEdgeController;
import jvizedit.mvc.content.core.IContentManager;

public class EdgeUpdateContext implements IContentUpdateContext<IEdgeController>{
	
	
	private final IContentManager contentManager;
	
	private final Set<IEdgeController> createdControllers = new HashSet<>();
	private final Set<IEdgeController> reusedControllers = new HashSet<>();
	
	
	public EdgeUpdateContext(final IContentManager contentManager) {
		this.contentManager = contentManager;
	}
	
	@Override
	public IEdgeController getController(final Object model, final IController parent) {
		
		final IEdgeController existingController = contentManager.getEdgeController(model);
		if(existingController != null) {
			final boolean wasJustCreated = createdControllers.contains(existingController);
			final boolean alreadyReused = !reusedControllers.add(existingController);
			if(wasJustCreated || alreadyReused) {
				throw new IllegalStateException("parent has multiple times same child");
			}
			return existingController;
		}
		final IEdgeController newController = contentManager.createEdgeController(model, parent);
		createdControllers.add(newController);
		return newController;
	}
	
	public Set<IEdgeController> getCreatedControllers() {
		return createdControllers;
	}
	
	public Set<IEdgeController> getReusedControllers() {
		return reusedControllers;
	}

	@Override
	public Collection<?> getModelChildren(IController controller) {
		if(!(controller instanceof IEdgeContainer)) {
			throw new IllegalArgumentException("Edge Container expected");
		}
		final IEdgeContainer ec = (IEdgeContainer)controller;
		return ec.getModelEdges();
	}
	
}
