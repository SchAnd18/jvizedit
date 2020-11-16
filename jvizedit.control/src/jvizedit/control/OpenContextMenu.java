package jvizedit.control;

import java.util.ArrayList;
import java.util.List;

import jvizedit.control.core.ControlState;
import jvizedit.control.core.ControlStateMachine;
import jvizedit.control.core.IControlStateEventHandler;
import jvizedit.control.core.events.IMouseEvent;
import jvizedit.control.core.events.IMouseEvent.MouseButton;
import jvizedit.control.selection.ISelectableController;
import jvizedit.control.selection.ISelectableFinder;

public class OpenContextMenu implements IControlStateEventHandler<IMouseEvent> {

	public static final String STATE_RIGHT_MOUSE_DOWN = "RightMouseDown";

	private final List<IOpenContextMenuListener> listeners = new ArrayList<>();
	private final ISelectableFinder controllerFinder;
	private final ControlState rightMouseDown;
	private final ControlState initState;

	public OpenContextMenu(final ControlStateMachine cstm, final ISelectableFinder controllerFinder) {
		this.controllerFinder = controllerFinder;

		this.rightMouseDown = cstm.getOrCreateState(STATE_RIGHT_MOUSE_DOWN);
		this.initState = cstm.getInitState();

		this.initState.addStateTransition(this.rightMouseDown, this);
		this.rightMouseDown.addStateTransition(this.initState, this);
	}

	public ControlState getRightMouseDownState() {
		return this.rightMouseDown;
	}

	@Override
	public Class<IMouseEvent> getExpectedEventType() {
		return IMouseEvent.class;
	}

	@Override
	public boolean handleInputEvent(final ControlState srcState, final ControlState targetState,
			final IMouseEvent event) {
		final boolean mouseUp = event.isButtonUp() && (event.getButton() == MouseButton.RIGHT);
		if ((srcState == this.rightMouseDown) && (targetState == this.initState) && mouseUp) {
			final ISelectableController selectableController = this.controllerFinder.findControllerAt(event.getX(),
					event.getY(), event);
			final double x = event.getX();
			final double y = event.getY();
			this.listeners.forEach(listener -> {
				listener.showContextMenu(selectableController, x, y);
			});
			return true;
		}

		final boolean mouseDown = event.isButtonDown() && (event.getButton() == MouseButton.RIGHT);
		if ((srcState == this.initState) && (targetState == this.rightMouseDown) && mouseDown) {
			return true;
		}
		return false;
	}

	public void addOpenContextMenuListener(final IOpenContextMenuListener listener) {
		this.listeners.add(listener);
	}

	public void removeOpenContextMenuListener(final IOpenContextMenuListener listener) {
		this.listeners.remove(listener);
	}

	public interface IOpenContextMenuListener {

		void showContextMenu(ISelectableController controller, double x, double y);

	}

}
