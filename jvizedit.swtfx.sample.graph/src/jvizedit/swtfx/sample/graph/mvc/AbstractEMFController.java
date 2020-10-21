package jvizedit.swtfx.sample.graph.mvc;

import org.eclipse.emf.ecore.EObject;

import jvizedit.mvc.IController;
import jvizedit.mvc.content.core.IContentManager;
import jvizedit.mvc.content.core.UnorderedContentHandler;

public abstract class AbstractEMFController extends AbstractEMFControllerBase implements IController{

	private final UnorderedContentHandler<IController> contentHandler = new UnorderedContentHandler<>(this);
	private IController parent;
	
	public AbstractEMFController(IContentManager contentManager, IController parent, EObject model) {
		super(contentManager, model);
		this.parent = parent;
	}
	
	@Override
	public UnorderedContentHandler<IController> getConentHandler() {
		return contentHandler;
	}

	@Override
	public IController getParent() {
		return parent;
	}

	@Override
	public void relocateToNewParent(IController newParent) {
		this.parent = newParent;
	}
}
