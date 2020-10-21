package jvizedit.mvc.content;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
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
	
	private final Map<Object,IController> modelControllerMap = new HashMap<>();
	private final Map<Object,IEdgeController> edgeControllerMap = new HashMap<>();
	private final Map<IController,Set<IEdgeController>> modelControllerToEdgeMap = new HashMap<>();
	
	private final Set<IController> invalidatedControllers = new HashSet<>();
	private final Set<IEdgeController> invalidatedEdgeControllers = new HashSet<>();
	
	private IController rootController;
	private Object newRootObject;
	private boolean rootUpdateRequired;
	
	private Supplier<Collection<?>> modelEdgeSupplier;

	
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
		final Set<IController> invalidatedControllers = new HashSet<>(this.invalidatedControllers);
		this.invalidatedControllers.clear();
		
		final Set<IEdgeController> invalidatedEdgeControllers = new HashSet<>(this.invalidatedEdgeControllers);
		this.invalidatedEdgeControllers.clear();
		
		// root node refresh
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
		
		// node tree refresh
		final ControllerTreeUpdater cu = new ControllerTreeUpdater();
		final ContentChange cc = cu.update(invalidatedControllers, this);
		
		
		// edges refresh
		final EdgesUpdater eu = new EdgesUpdater(this);
		eu.update(cc, invalidatedEdgeControllers);
		
		for(IController o: cc.getRemovedControllers()) {
			final IController removedController = modelControllerMap.remove(o.getModel());
			modelControllerToEdgeMap.remove(removedController);
		}
		contentChangeListeners.forEach(l->l.onContentChange(cc));
	}
	
	@Override
	public void setRoot(Object root) {
		this.newRootObject = root;
		this.rootUpdateRequired = true;
	}
	
	@Override
	public void setModelEdgeSupplier(Supplier<Collection<?>> edgeSupplier) {
		this.modelEdgeSupplier = edgeSupplier;
	}
	
	protected Collection<?> getModelEdges() {
		if(modelEdgeSupplier == null) {
			return Collections.emptyList();
		}
		final Collection<?> result = modelEdgeSupplier.get();
		if(result == null) {
			return Collections.emptyList();
		}
		return result;
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
	public IEdgeController createEdgeController(Object model) {
		for(IControllerFactory factory: controlerFactories) {
			final IEdgeController c = factory.createEdgeController(model, this);
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

	@Override
	public Set<IEdgeController> getConnectedEdgeControllers(IController controller) {
		final Set<IEdgeController> result = modelControllerToEdgeMap.getOrDefault(controller, Collections.emptySet());
		return Collections.unmodifiableSet(result);
	}
	
	protected void updateEdgeControllerConnection(IEdgeController edgeController, IController oldConnected, IController newConnected) {
		if(Objects.equals(oldConnected, newConnected)) {
			return;
		}
		if(oldConnected != null) {
			getConnectedEdgeControllersModifieable(oldConnected).remove(edgeController);
		}
		if(newConnected != null) {
			getConnectedEdgeControllersModifieable(newConnected).add(edgeController);
		}
	}
	
	private Set<IEdgeController> getConnectedEdgeControllersModifieable(IController controller) {
		final boolean isKnownController = modelControllerMap.containsKey(controller.getModel());
		if(!isKnownController) {
			throw new IllegalStateException("Controller is not managed by this content manager " + controller + ".");
		}
		return modelControllerToEdgeMap.computeIfAbsent(controller, __-> new HashSet<>());
	}
	
	//TODO: remove this method
	protected Map<Object, IEdgeController> getEdgeControllerMap() {
		return edgeControllerMap;
	}
}
