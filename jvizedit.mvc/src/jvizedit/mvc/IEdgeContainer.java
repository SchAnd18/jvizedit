package jvizedit.mvc;

import java.util.Collection;

import jvizedit.mvc.content.core.IContentHandler;

/**
 * Use this only when edge location is correctly handled by model
 */
public interface IEdgeContainer {

	Collection<?> getModelEdges();
	
	IContentHandler<IEdgeController> getEdgeConentHandler();

	void updateEdges();
}
