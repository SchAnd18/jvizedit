package jvizedit.mvc.content;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import jvizedit.mvc.IController;
import jvizedit.mvc.IControllerBase;
import jvizedit.mvc.IControllerFactory;
import jvizedit.mvc.IEdgeController;
import jvizedit.mvc.content.IContentChangeListener.ContentChange;
import jvizedit.mvc.content.core.IContentManager;

public class ContentManager implements IContentManager {

	private final List<IContentChangeListener> contentChangeListeners = new ArrayList<>();
	private final List<IControllerFactory> controlerFactories = new ArrayList<>();
	
	//TODO: make one map out of these two
	private final Map<Object,IController> modelControllerMap = new HashMap<>();
	private final Map<Object,IEdgeController> edgeControllerMap = new HashMap<>();
	
	private final Set<IController> invalidatedControllers = new HashSet<>();
	private final Set<IEdgeController> invalidatedEdgeControllers = new HashSet<>();
	
	private IController rootController;
	private Object newRootObject;
	private boolean rootUpdateRequired;

	
	@Override
	public void addContentChangeListener(IContentChangeListener listener) {
		this.contentChangeListeners.add(listener);
	}
	
	@Override
	public void removeContentChangeListener(IContentChangeListener listener) {
		this.contentChangeListeners.remove(listener);
	}
	
	
	@Override
	public void addControllerFactory(IControllerFactory controllerFactory) {
		this.controlerFactories.add(controllerFactory);
	}
	
	@Override
	public IController getController(Object model) {
		return modelControllerMap.get(model);
	}
	
	@Override
	public IEdgeController getEdgeController(Object model) {
		return edgeControllerMap.get(model);
	}
	
	@Override
	public void invalidateController(IControllerBase controller) {
		if(controller instanceof IEdgeController) {
			invalidatedEdgeControllers.add((IEdgeController)controller);
		} else if(controller instanceof IController) {
			invalidatedControllers.add((IController)controller);
		} else {
			throw new IllegalStateException("Unknown type of controller " + controller);
		}
		
	}

	@Override
	public void performRefresh() {
		final ControllerUpdateContext updateContext = new ControllerUpdateContext(this);
		Set<IController> invalidatedControllers = new HashSet<>(this.invalidatedControllers);
		this.invalidatedControllers.clear();
		
		
		///// root refresh
		if(rootUpdateRequired) {
			final IController oldRootController = rootController;
			if(rootController != null) {
				this.rootController = null;
			}
			if(newRootObject != null) {
				this.rootController = updateContext.getController(newRootObject, null);
				invalidatedControllers.add(this.rootController);
				newRootObject = null;
			}
			rootUpdateRequired = false;
			contentChangeListeners.forEach(l->l.onRootChange(oldRootController, this.rootController));
		}
		
		final ControllerTreeUpdater cu = new ControllerTreeUpdater();
		final ContentChange cc = cu.update(invalidatedControllers, this);
		
		for(IController o: cc.getRemovedControllers()) {
			modelControllerMap.remove(o.getModel());
		}	
		
		contentChangeListeners.forEach(l->l.onContentChange(cc));
	}
	
	
	@Override
	public void setRoot(Object root) {
		this.newRootObject = root;
		this.rootUpdateRequired = true;
	}

	@Override
	public IController createController(final Object model, final IController parent) {
		for(IControllerFactory factory: controlerFactories) {
			final IController c = factory.createController(model, this, parent);
			if(c != null) {
				final IController oldController = this.modelControllerMap.put(model, c);
				if(oldController != null) {
					throw new IllegalStateException("There was already a registered controller for " + model);
				}
				return c;
			}
		}
		throw new IllegalArgumentException("Unable to create controller for object " + model);
	}
	
	@Override
	public IEdgeController createEdgeController(Object model, IController parent) {
		for(IControllerFactory factory: controlerFactories) {
			final IEdgeController c = factory.createEdgeController(model, this, parent);
			if(c != null) {
				final IEdgeController oldController = this.edgeControllerMap.put(model, c);
				if(oldController != null) {
					throw new IllegalStateException("There aw already a registered edge controller for " + model);
				}
				return c;
			}
		}
		throw new IllegalArgumentException("Unable to create controller for object " + model);
	}

	@Override
	public IController getRootController() {
		return this.rootController;
	}

	@Override
	public Collection<?> getControllers() {
		return Collections.unmodifiableCollection(this.modelControllerMap.values());
	}

	@Override
	public <T> Collection<T> getControllersOfType(Class<T> type) {
		final List<T> result = this.modelControllerMap.values().stream()
				.filter(type::isInstance) //
				.map(type::cast) //
				.collect(Collectors.toList());
		return result;
	}

}
