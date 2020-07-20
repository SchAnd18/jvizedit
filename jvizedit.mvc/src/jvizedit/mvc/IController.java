package jvizedit.mvc;

import java.util.Collection;
import java.util.Collections;

import jvizedit.mvc.content.core.IContentHandler;

public interface IController extends IControllerBase {
	
	Collection<?> getModelChildren();
	
	void updateView();
	
	IContentHandler<IController> getConentHandler();
	
	IController getParent();
	
	//TODO: remove this method
	void relocateToNewParent(IController newParent);
	
//	void addConnectedEdge(IEdgeController edge);
//	
//	void removeConnectedEdge(IEdgeController edge);
//	
//	Collection<IEdgeController> getConnectedEdges();
	
	
	default Collection<IController> getControllerChildren() {
		final IContentHandler<IController> ch = getConentHandler();
		if(ch == null) {
			return Collections.emptyList();
		}
		return ch.getContent();
	}
	
	default boolean hasControllerChildren() {
		final IContentHandler<IController> ch = getConentHandler();
		return ch != null && ch.getContent().isEmpty() == false;
	}
	
}
