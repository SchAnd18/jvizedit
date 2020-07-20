package jvizedit.control;

import java.util.ArrayList;
import java.util.List;

import jvizedit.control.core.ControlState;
import jvizedit.control.core.ControlStateMachine;
import jvizedit.control.core.IControlStateEventHandler;
import jvizedit.control.core.events.IMouseEvent;
import jvizedit.control.core.events.IMouseEvent.MouseButton;

public class OpenContextMenu implements IControlStateEventHandler<IMouseEvent> {

	public static final String STATE_RIGHT_MOUSE_DOWN = "RightMouseDown";
	
	private final List<IOpenContextMenuListener> listeners = new ArrayList<>();
	private final ControlState rightMouseDown;
	private final ControlState initState;
	
	public OpenContextMenu(final ControlStateMachine cstm) {
		rightMouseDown = cstm.getOrCreateState(STATE_RIGHT_MOUSE_DOWN);
		initState = cstm.getInitState();
		
		initState.addStateTransition(rightMouseDown, this);
		rightMouseDown.addStateTransition(initState, this);
	}
	
	public ControlState getRightMouseDownState() {
		return rightMouseDown;
	}
	
	@Override
	public Class<IMouseEvent> getExpectedEventType() {
		return IMouseEvent.class;
	}
	
	@Override
	public boolean handleInputEvent(ControlState srcState, ControlState targetState, IMouseEvent event) {
		final boolean mouseUp = event.isButtonUp() && event.getButton() == MouseButton.RIGHT;
		if(srcState == rightMouseDown && targetState == initState && mouseUp) {
			final double x = event.getX();
			final double y = event.getY();
			this.listeners.forEach(i->{i.showContextMenu(x, y);});
			return true;
		}
		
		final boolean mouseDown = event.isButtonDown() && event.getButton() == MouseButton.RIGHT;
		if(srcState == initState && targetState == rightMouseDown && mouseDown) {
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
		
		void showContextMenu(final double x, final double y);
		
	}
	
}
