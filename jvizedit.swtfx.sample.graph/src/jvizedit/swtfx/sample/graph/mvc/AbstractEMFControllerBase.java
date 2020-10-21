package jvizedit.swtfx.sample.graph.mvc;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;

import jvizedit.mvc.IControllerBase;
import jvizedit.mvc.content.core.IContentManager;

public abstract class AbstractEMFControllerBase implements IControllerBase {

	private final EObject model;
	private final IContentManager contentManager;

	private boolean isDisposed = false;
	
	private final Adapter adapter;
	
	public AbstractEMFControllerBase(final IContentManager contentManager, final EObject model) {
		this.adapter = createEMFInvalidationAdapter();
		this.contentManager = contentManager;
		this.model = model;
		if(this.model != null) {			
			this.model.eAdapters().add(adapter);
		}
	}
	
	@Override
	public void dispose() {
		this.model.eAdapters().remove(adapter);
		isDisposed = true;
	}

	@Override
	public boolean isDisposed() {
		return isDisposed;
	}

	@Override
	public Object getModel() {
		return model;
	}
	
	public IContentManager getContentManager() {
		return contentManager;
	}
	
	public void invalidate() {
		contentManager.invalidateController(AbstractEMFControllerBase.this);
	}

	protected Adapter createEMFInvalidationAdapter() {
		return new AdapterImpl() {
			public void notifyChanged(org.eclipse.emf.common.notify.Notification msg) {
				invalidate();
			};
		};
	}
}
