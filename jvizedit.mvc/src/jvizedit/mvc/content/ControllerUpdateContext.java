package jvizedit.mvc.content;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import jvizedit.mvc.IController;
import jvizedit.mvc.content.core.IContentManager;

public class ControllerUpdateContext implements IContentUpdateContext<IController> {

	private final IContentManager contentManager;

	private final Set<IController> createdControllers = new HashSet<>();
	private final Set<IController> reusedControllers = new HashSet<>();

	public ControllerUpdateContext(final IContentManager contentManager) {
		this.contentManager = contentManager;
	}

	@Override
	public IController getController(final Object model, final IController parent) {

		final IController existingController = this.contentManager.getController(model);
		if (existingController != null) {
			final boolean wasJustCreated = this.createdControllers.contains(existingController);
			final boolean alreadyReused = !this.reusedControllers.add(existingController);
			if (wasJustCreated || alreadyReused) {
				throw new IllegalStateException("parent has multiple times same child");
			}
			return existingController;
		}
		final IController newController = this.contentManager.createController(model, parent);
		this.createdControllers.add(newController);
		return newController;
	}

	public Set<IController> getCreatedControllers() {
		return this.createdControllers;
	}

	public Set<IController> getReusedControllers() {
		return this.reusedControllers;
	}

	@Override
	public Collection<?> getModelChildren(final IController controller) {
		return controller.getModelChildren();
	}

}
