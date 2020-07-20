package jvizedit.control.selection;

import java.util.ArrayList;
import java.util.List;

import jvizedit.control.core.ControlState;
import jvizedit.control.core.ControlStateMachine;
import jvizedit.control.core.IControlStateTransition;
import jvizedit.control.core.events.IMouseEvent;
import jvizedit.control.core.events.IMouseEvent.MouseButton;

public class SelectOnClick implements IControlStateTransition<IMouseEvent> {
	
	public static final String STATE_LEFT_MOUSE_DOWN_ON_SELECTABLE = "LeftMouseDownOnSelectable";
	public static final String STATE_LEFT_MOUSE_DOWN = "LeftMouseDown";
	
	private final ControlState init;
	private final ControlState mouseDownOnSelectable;
	private final ControlState mouseDown;
	
	private final List<ISelectOnClickListener> listeners = new ArrayList<>();
	private final ISelectableFinder controllerFinder;
	
	private ISelectableController currentSelectable;

	public SelectOnClick(final ControlStateMachine cstm, final ISelectableFinder controllerFinder ) {
		this.controllerFinder = controllerFinder;
		
		init = cstm.getInitState();
		mouseDownOnSelectable = cstm.getOrCreateState(STATE_LEFT_MOUSE_DOWN_ON_SELECTABLE);
		mouseDown = cstm.getOrCreateState(STATE_LEFT_MOUSE_DOWN);
		
		init.addStateTransition(mouseDownOnSelectable, this);
		init.addStateTransition(mouseDown, this);
		
		mouseDownOnSelectable.addStateTransition(init, this);
		mouseDown.addStateTransition(init, this);
	}
	
	@Override
	public Class<IMouseEvent> getExpectedEventType() {
		return IMouseEvent.class;
	}
	
	public void addSelectOnClickListener(final ISelectOnClickListener listener) {
		listeners.add(listener);
	}
	
	public void removeSelectOnClickListener(final ISelectOnClickListener listener) {
		listeners.remove(listener);
	}
	
	public ControlState getMouseDownOnSelectableState() {
		return mouseDownOnSelectable;
	}
	
	public ControlState getMouseDownState() {
		return mouseDown;
	}
	
	public boolean updateSelection(final IMouseEvent mouseEvent, ControlState srcState, final SelectionUpdate update) {
		if(srcState == mouseDownOnSelectable) {
			if(currentSelectable != null) {
				for(ISelectOnClickListener listener: this.listeners) {
					listener.updateSelection(mouseEvent, currentSelectable, update);
				}
			}
			return true;
		} else if(srcState == mouseDown) {
			if(update == SelectionUpdate.SET) {
				for(ISelectOnClickListener listener: this.listeners) {
					listener.clearSelection(mouseEvent);
				}
			}
			this.currentSelectable = null;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean handleInputEvent(ControlState srcState, ControlState targetState, IMouseEvent event) {
		
		if(srcState == init && event.isButtonDown() && event.getButton() == MouseButton.LEFT) {
			final ISelectableController controller = controllerFinder.findControllerAt(event.getX(), event.getY(), event);
			if(targetState == mouseDownOnSelectable) {
				if(controller != null) {
					this.currentSelectable = controller;
					return true;
				} else {
					return false;
				}
			} else if(targetState == mouseDown) {
				return controller == null;
			}		
		}
		
		if(event.isButtonUp() && event.getButton() == MouseButton.LEFT) {	
			final SelectionUpdate update = event.isControlDown() ? SelectionUpdate.TOGGLE : SelectionUpdate.SET;
			return updateSelection(event, srcState, update);
		}
		return false;
	}
	
	
	public interface ISelectOnClickListener {
		//TODO: Add mouse events with coordinates here
		
		void updateSelection(IMouseEvent mouseEvent, ISelectableController controller, SelectionUpdate update);
		
		void clearSelection(IMouseEvent mouseEvent);
		
	}
	
	public static enum SelectionUpdate {
		SET,
		SET_IF_NOT_SELECTED,
		TOGGLE
	}
	
}
