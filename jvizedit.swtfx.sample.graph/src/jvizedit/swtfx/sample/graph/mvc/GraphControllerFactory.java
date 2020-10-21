package jvizedit.swtfx.sample.graph.mvc;

import jvizedit.mvc.IController;
import jvizedit.mvc.IControllerFactory;
import jvizedit.mvc.IEdgeController;
import jvizedit.mvc.content.core.IContentManager;
import jvizedit.swtfx.sample.graph.gml.Connection;
import jvizedit.swtfx.sample.graph.gml.GraphModelRoot;
import jvizedit.swtfx.sample.graph.gml.GraphNode;

public class GraphControllerFactory implements IControllerFactory {

	@Override
	public IController createController(Object model, IContentManager contentManager, IController parent) {
		if(model instanceof GraphModelRoot) {
			return new GraphModelRootController(contentManager, parent, (GraphModelRoot)model);
		}
		if(model instanceof GraphNode) {
			return new GraphNodeController(contentManager, parent, (GraphNode)model);
		}
		return null;
	}

	@Override
	public IEdgeController createEdgeController(Object model, IContentManager contentManager) {
		if(model instanceof Connection) {
			final Connection connection = (Connection)model;
			return new ConnectionController(contentManager, connection);
		}
		return null;
	}

}
