package jvizedit.mvc.content.core;

import java.util.Collection;
import java.util.Optional;

import jvizedit.mvc.IController;
import jvizedit.mvc.IControllerBase;
import jvizedit.mvc.IControllerFactory;
import jvizedit.mvc.IEdgeController;
import jvizedit.mvc.content.IContentChangeListener;

public interface IContentManager {
	
	void addControllerFactory(IControllerFactory controllerFactory);
	
	void invalidateController(IControllerBase controller);
	
	void performRefresh();
	
	IController getController(Object model);
	
	default <T> Optional<T> getControllerOfType(Class<T> type, Object model) {
		final IController c = getController(model);
		if(type.isInstance(c)) {
			return Optional.of(type.cast(c));
		}
		return Optional.empty();
	}
	
	Collection<?> getControllers();
	
	<T> Collection<T> getControllersOfType(Class<T> type);
	
	IController createController(Object model,IController parent);
	
	IEdgeController getEdgeController(Object model);
	
	IEdgeController createEdgeController(Object model, IController parent);
	
	void setRoot(Object model);
	
	IController getRootController();
	
	void addContentChangeListener(IContentChangeListener listener);
	
	void removeContentChangeListener(IContentChangeListener listener);
}
