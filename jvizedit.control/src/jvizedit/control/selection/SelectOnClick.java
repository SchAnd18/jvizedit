package jvizedit.control.selection;

import java.util.ArrayList;
import java.util.List;

import jvizedit.control.core.ControlState;
import jvizedit.control.core.ControlStateMachine;
import jvizedit.control.core.IControlStateEventHandler;
import jvizedit.control.core.events.IMouseEvent;
import jvizedit.control.core.events.IMouseEvent.MouseButton;

public class SelectOnClick implements IControlStateEventHandler<IMouseEvent> {

	public static final String STATE_LEFT_MOUSE_DOWN_ON_SELECTABLE = "LeftMouseDownOnSelectable";
	public static final String STATE_LEFT_MOUSE_DOWN = "LeftMouseDown";

	private final ControlState init;
	private final ControlState mouseDownOnSelectable;
	private final ControlState mouseDown;

	private final List<ISelectOnClickListener> listeners = new ArrayList<>();
	private final ISelectableFinder controllerFinder;

	private ISelectableController currentSelectable;

	public SelectOnClick(final ControlStateMachine cstm, final ISelectableFinder controllerFinder) {
		this.controllerFinder = controllerFinder;

		this.init = cstm.getInitState();
		this.mouseDownOnSelectable = cstm.getOrCreateState(STATE_LEFT_MOUSE_DOWN_ON_SELECTABLE);
		this.mouseDown = cstm.getOrCreateState(STATE_LEFT_MOUSE_DOWN);

		this.init.addStateTransition(this.mouseDownOnSelectable, this);
		this.init.addStateTransition(this.mouseDown, this);

		this.mouseDownOnSelectable.addStateTransition(this.init, this);
		this.mouseDown.addStateTransition(this.init, this);
	}

	@Override
	public Class<IMouseEvent> getExpectedEventType() {
		return IMouseEvent.class;
	}

	public void addSelectOnClickListener(final ISelectOnClickListener listener) {
		this.listeners.add(listener);
	}

	public void removeSelectOnClickListener(final ISelectOnClickListener listener) {
		this.listeners.remove(listener);
	}

	public ControlState getMouseDownOnSelectableState() {
		return this.mouseDownOnSelectable;
	}

	public ControlState getMouseDownState() {
		return this.mouseDown;
	}

	public boolean updateSelection(final IMouseEvent mouseEvent, final ControlState srcState,
			final SelectionUpdate update) {
		if (srcState == this.mouseDownOnSelectable) {
			if (this.currentSelectable != null) {
				for (final ISelectOnClickListener listener : this.listeners) {
					listener.updateSelection(mouseEvent, this.currentSelectable, update);
				}
			}
			return true;
		} else if (srcState == this.mouseDown) {
			if (update == SelectionUpdate.SET) {
				for (final ISelectOnClickListener listener : this.listeners) {
					listener.clearSelection(mouseEvent);
				}
			}
			this.currentSelectable = null;
			return true;
		}
		return false;
	}

	@Override
	public boolean handleInputEvent(final ControlState srcState, final ControlState targetState,
			final IMouseEvent event) {

		if ((srcState == this.init) && event.isButtonDown() && (event.getButton() == MouseButton.LEFT)) {
			final ISelectableController controller = this.controllerFinder.findControllerAt(event.getX(), event.getY(),
					event);
			if (targetState == this.mouseDownOnSelectable) {
				if (controller != null) {
					this.currentSelectable = controller;
					return true;
				} else {
					return false;
				}
			} else if (targetState == this.mouseDown) {
				return controller == null;
			}
		}

		if (event.isButtonUp() && (event.getButton() == MouseButton.LEFT)) {
			final SelectionUpdate update = event.isControlDown() ? SelectionUpdate.TOGGLE : SelectionUpdate.SET;
			return updateSelection(event, srcState, update);
		}
		return false;
	}

	public interface ISelectOnClickListener {

		void updateSelection(IMouseEvent mouseEvent, ISelectableController controller, SelectionUpdate update);

		void clearSelection(IMouseEvent mouseEvent);

	}

	public static enum SelectionUpdate {
		SET, SET_IF_NOT_SELECTED, TOGGLE
	}

}
