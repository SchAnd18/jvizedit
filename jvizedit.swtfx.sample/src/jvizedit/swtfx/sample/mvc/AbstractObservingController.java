package jvizedit.swtfx.sample.mvc;

import jvizedit.mvc.AbstractController;
import jvizedit.mvc.IController;
import jvizedit.mvc.content.core.IContentManager;
import jvizedit.swtfx.sample.model.AbstractObervableObject;
import jvizedit.swtfx.sample.model.IObjectChangeListener;

public abstract class AbstractObservingController<T extends AbstractObervableObject> extends AbstractController
		implements IObjectChangeListener {

	private final IContentManager contentManager;
	private final T modelObj;
	private boolean isDisposed = false;

	public AbstractObservingController(final IContentManager contentManager, final IController parent,
			final T modelObj) {
		super(parent);
		this.contentManager = contentManager;
		this.modelObj = modelObj;
		this.modelObj.addListener(this);
	}

	@Override
	public boolean isDisposed() {
		return this.isDisposed;
	}

	@Override
	public void dispose() {
		this.modelObj.removeListener(this);
		this.isDisposed = true;
	}

	@Override
	public T getModel() {
		return this.modelObj;
	}

	@Override
	public void objectChanged(final Object obj) {
		this.contentManager.invalidateController(this);
	}

}
