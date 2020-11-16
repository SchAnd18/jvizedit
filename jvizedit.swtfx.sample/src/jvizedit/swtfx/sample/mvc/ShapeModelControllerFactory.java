package jvizedit.swtfx.sample.mvc;

import jvizedit.mvc.IController;
import jvizedit.mvc.IControllerFactory;
import jvizedit.mvc.IEdgeController;
import jvizedit.mvc.content.core.IContentManager;
import jvizedit.swtfx.sample.model.ShapeObject;
import jvizedit.swtfx.sample.model.ShapesRoot;

public class ShapeModelControllerFactory implements IControllerFactory {

	@Override
	public IController createController(final Object model, final IContentManager contentManager,
			final IController parent) {
		if (model instanceof ShapesRoot) {
			final ShapesRoot sr = (ShapesRoot) model;
			return new ShapesRootController(contentManager, parent, sr);
		}
		if (model instanceof ShapeObject) {
			final ShapeObject so = (ShapeObject) model;
			return new ShapeObjectController(contentManager, parent, so);
		}
		return null;
	}

	@Override
	public IEdgeController createEdgeController(final Object model, final IContentManager contentManager) {
		return null;
	}

}
