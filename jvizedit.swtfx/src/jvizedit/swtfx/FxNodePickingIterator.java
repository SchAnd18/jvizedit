package jvizedit.swtfx;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.function.BiFunction;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import jvizedit.mvc.IController;

public class FxNodePickingIterator {

	public static <T> Optional<T> findControllerOfType(final IController start, final Class<T> controllerType,
			final double x, final double y) {
		final List<T> result = new ArrayList<>(1);
		final FxNodePickingIterator npi = new FxNodePickingIterator();
		npi.setOnEnterNode((c, n) -> {
			if (controllerType.isInstance(c)) {
				result.add(controllerType.cast(c));
				return ENavigationCommand.quit;
			}
			return ENavigationCommand.goOn;
		});
		npi.pickNode(start, x, y);
		if (result.isEmpty()) {
			return Optional.empty();
		} else {
			return Optional.of(result.get(0));
		}
	}

	private BiFunction<IController, Node, ENavigationCommand> enterNodeFunction = (c, n) -> {
		return ENavigationCommand.goOn;
	};

	private BiFunction<IController, Node, ENavigationCommand> leaveNodeFunction = (c, n) -> {
		return ENavigationCommand.goOn;
	};

	public FxNodePickingIterator setOnEnterNode(final BiFunction<IController, Node, ENavigationCommand> onEnterNode) {
		this.enterNodeFunction = onEnterNode;
		return this;
	}

	public FxNodePickingIterator setOnLeaveNode(final BiFunction<IController, Node, ENavigationCommand> onLeaveNode) {
		this.leaveNodeFunction = onLeaveNode;
		return this;
	}

	public void pickNode(final IController controller, final double sceneX, final double sceneY) {
		final Node node = controller.getViewAsType(Node.class);
		final Point2D local = node.sceneToLocal(sceneX, sceneY);
		if (node.getBoundsInLocal().contains(local) && node.contains(local)) {
			final ENavigationCommand nc = this.enterNodeFunction.apply(controller, node);
			switch (nc) {
			case quit:
			case skip:
				return;
			case goOn:
			}

			if (node instanceof Parent) {
				final Parent asParent = (Parent) node;
				pickNode(controller, asParent, local);
			}
			this.leaveNodeFunction.apply(controller, node);
		}
	}

	private ENavigationCommand pickNode(final IController controller, final Parent parent, final Point2D parentPos) {
		final List<? extends Node> children = parent.getChildrenUnmodifiable();
		final ListIterator<? extends Node> iterator = children.listIterator(children.size());
		while (iterator.hasPrevious()) {
			final Node node = iterator.previous();
			final Point2D local = node.parentToLocal(parentPos);
			if (node.getBoundsInLocal().contains(local) && node.contains(local)) {

				final IController nodeController = getController(node).orElse(controller);
				final ENavigationCommand ncEnter = this.enterNodeFunction.apply(nodeController, node);
				switch (ncEnter) {
				case quit:
					return ENavigationCommand.quit;
				case goOn:
					if (node instanceof Parent) {
						final Parent asParent = (Parent) node;
						final ENavigationCommand ncSub = pickNode(nodeController, asParent, local);
						if (ncSub == ENavigationCommand.quit) {
							return ENavigationCommand.quit;
						}
					}
				case skip:
				}

				final ENavigationCommand ncLeave = this.leaveNodeFunction.apply(nodeController, node);
				switch (ncLeave) {
				case quit:
					return ENavigationCommand.quit;
				case skip:
					throw new IllegalStateException("leaveNode is not allowed to return 'skip'");
				case goOn:
				}
			}
		}
		return ENavigationCommand.goOn;
	}

	private Optional<IController> getController(final Node node) {
		final Object userData = node.getUserData();
		if (!(userData instanceof IController)) {
			return Optional.empty();
		}
		final IController c = (IController) userData;
		return Optional.of(c);
	}

	public static enum ENavigationCommand {
		/**
		 * Stops iterating
		 */
		quit,
		/**
		 * Only allowed when a node is entered. It signals the iterator not not enter
		 * this part of the tree any further
		 */
		skip,
		/**
		 * Go on with iteration
		 */
		goOn
	}
}
