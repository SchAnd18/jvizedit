package jvizedit.mvc;

import jvizedit.mvc.content.core.IContentManager;

public interface IControllerFactory {

	/**
	 * Create a new controller
	 * 
	 * @param model          The model that shall be managed by the controller
	 * @param contentManager The content manager context that manages the current
	 *                       controller tree
	 * @param parent         The parent controller, this argument is null when the
	 *                       root controller shall be created
	 * @return
	 */
	IController createController(Object model, IContentManager contentManager, IController parent);

	IEdgeController createEdgeController(Object model, IContentManager contentManager);

}
