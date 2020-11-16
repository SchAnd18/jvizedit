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

	private final Map<Object, IController> modelControllerMap = new HashMap<>();
	private final Map<Object, IEdgeController> edgeControllerMap = new HashMap<>();
	private final Map<IController, Set<IEdgeController>> modelControllerToEdgeMap = new HashMap<>();

	private final Set<IController> invalidatedControllers = new HashSet<>();
	private final Set<IEdgeController> invalidatedEdgeControllers = new HashSet<>();

	private IController rootController;
	private Object newRootObject;
	private boolean rootUpdateRequired;

	private Supplier<Collection<?>> modelEdgeSupplier;

	@Override
	public void addContentChangeListener(final IContentChangeListener listener) {
		this.contentChangeListeners.add(listener);
	}

	@Override
	public void removeContentChangeListener(final IContentChangeListener listener) {
		this.contentChangeListeners.remove(listener);
	}

	@Override
	public void addControllerFactory(final IControllerFactory controllerFactory) {
		this.controlerFactories.add(controllerFactory);
	}

	@Override
	public IController getController(final Object model) {
		return this.modelControllerMap.get(model);
	}

	@Override
	public IEdgeController getEdgeController(final Object model) {
		return this.edgeControllerMap.get(model);
	}

	@Override
	public void invalidateController(final IControllerBase controller) {
		if (controller instanceof IEdgeController) {
			this.invalidatedEdgeControllers.add((IEdgeController) controller);
		} else if (controller instanceof IController) {
			this.invalidatedControllers.add((IController) controller);
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
		if (this.rootUpdateRequired) {
			final IController oldRootController = this.rootController;
			if (this.rootController != null) {
				this.rootController = null;
			}
			if (this.newRootObject != null) {
				this.rootController = updateContext.getController(this.newRootObject, null);
				invalidatedControllers.add(this.rootController);
				this.newRootObject = null;
			}
			this.rootUpdateRequired = false;
			this.contentChangeListeners.forEach(l -> l.onRootChange(oldRootController, this.rootController));
		}

		// node tree refresh
		final ControllerTreeUpdater cu = new ControllerTreeUpdater();
		final ContentChange cc = cu.update(invalidatedControllers, this);

		// edges refresh
		final EdgesUpdater eu = new EdgesUpdater(this);
		eu.update(cc, invalidatedEdgeControllers);

		for (final IController o : cc.getRemovedControllers()) {
			final IController removedController = this.modelControllerMap.remove(o.getModel());
			this.modelControllerToEdgeMap.remove(removedController);
		}
		this.contentChangeListeners.forEach(l -> l.onContentChange(cc));
	}

	@Override
	public void setRoot(final Object root) {
		this.newRootObject = root;
		this.rootUpdateRequired = true;
	}

	@Override
	public void setModelEdgeSupplier(final Supplier<Collection<?>> edgeSupplier) {
		this.modelEdgeSupplier = edgeSupplier;
	}

	protected Collection<?> getModelEdges() {
		if (this.modelEdgeSupplier == null) {
			return Collections.emptyList();
		}
		final Collection<?> result = this.modelEdgeSupplier.get();
		if (result == null) {
			return Collections.emptyList();
		}
		return result;
	}

	@Override
	public IController createController(final Object model, final IController parent) {
		for (final IControllerFactory factory : this.controlerFactories) {
			final IController c = factory.createController(model, this, parent);
			if (c != null) {
				final IController oldController = this.modelControllerMap.put(model, c);
				if (oldController != null) {
					throw new IllegalStateException("There was already a registered controller for " + model);
				}
				return c;
			}
		}
		throw new IllegalArgumentException("Unable to create controller for object " + model);
	}

	@Override
	public IEdgeController createEdgeController(final Object model) {
		for (final IControllerFactory factory : this.controlerFactories) {
			final IEdgeController c = factory.createEdgeController(model, this);
			if (c != null) {
				final IEdgeController oldController = this.edgeControllerMap.put(model, c);
				if (oldController != null) {
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
	public <T> Collection<T> getControllersOfType(final Class<T> type) {
		final List<T> result = this.modelControllerMap.values().stream().filter(type::isInstance) //
				.map(type::cast) //
				.collect(Collectors.toList());
		return result;
	}

	@Override
	public Set<IEdgeController> getConnectedEdgeControllers(final IController controller) {
		final Set<IEdgeController> result = this.modelControllerToEdgeMap.getOrDefault(controller,
				Collections.emptySet());
		return Collections.unmodifiableSet(result);
	}

	protected void updateEdgeControllerConnection(final IEdgeController edgeController, final IController oldConnected,
			final IController newConnected) {
		if (Objects.equals(oldConnected, newConnected)) {
			return;
		}
		if (oldConnected != null) {
			getConnectedEdgeControllersModifieable(oldConnected).remove(edgeController);
		}
		if (newConnected != null) {
			getConnectedEdgeControllersModifieable(newConnected).add(edgeController);
		}
	}

	private Set<IEdgeController> getConnectedEdgeControllersModifieable(final IController controller) {
		final boolean isKnownController = this.modelControllerMap.containsKey(controller.getModel());
		if (!isKnownController) {
			throw new IllegalStateException("Controller is not managed by this content manager " + controller + ".");
		}
		return this.modelControllerToEdgeMap.computeIfAbsent(controller, __ -> new HashSet<>());
	}

	// TODO: remove this method
	protected Map<Object, IEdgeController> getEdgeControllerMap() {
		return this.edgeControllerMap;
	}
}
