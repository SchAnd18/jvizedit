package jvizedit.control.selection;

import java.util.ArrayList;
import java.util.List;

import jvizedit.control.core.ControlState;
import jvizedit.control.core.ControlStateMachine;
import jvizedit.control.core.ControlStateMachine.IControlStateUpdateListener;
import jvizedit.control.core.IControlStateEventHandler;
import jvizedit.control.core.events.IKeyEvent;
import jvizedit.control.core.events.IKeyEvent.Key;
import jvizedit.control.core.events.IMouseEvent.MouseButton;
import jvizedit.control.core.events.IMouseEvent;
import jvizedit.control.core.events.Point2D;

public class SelectionAreaOnDrag implements IControlStateUpdateListener {

	private final List<ISelectionAreaListener> listeners = new ArrayList<>();
	private final ControlState init;
	private final ControlState mouseDown;
	private final ControlState selectionArea;

	private Point2D selectionAreaStart;

	public SelectionAreaOnDrag(final ControlStateMachine cstm, final SelectOnClick selectOnClick) {
		init = cstm.getOrCreateState("Init");
		this.mouseDown = selectOnClick.getMouseDownState();
		this.selectionArea = cstm.getOrCreateState("SelectionArea");

		this.mouseDown.addStateTransition(selectionArea, performDrag);
		this.selectionArea.addStateTransition(selectionArea, performDrag);
		this.selectionArea.addStateTransition(init, performDrag);

		this.selectionArea.addStateTransition(init, abortDrag);

		cstm.addUpdateListener(this);
	}

	public void addSelectionAreaListener(final ISelectionAreaListener listener) {
		this.listeners.add(listener);
	}

	public void removeSelectionAreaListener(final ISelectionAreaListener listener) {
		this.listeners.remove(listener);
	}

	private final IControlStateEventHandler<IKeyEvent> abortDrag = new IControlStateEventHandler<IKeyEvent>() {
		public boolean handleInputEvent(ControlState srcState, ControlState targetState, IKeyEvent event) {
			final boolean escUp = event.isKeyReleased() && event.getKey() == Key.ESCAPE;
			if (srcState == selectionArea && targetState == init && escUp) {
				selectionAreaStart = null;
				cancelSelectionArea();
				return true;
			}
			return false;
		}

		public Class<IKeyEvent> getExpectedEventType() {
			return IKeyEvent.class;
		}
	};

	private final IControlStateEventHandler<IMouseEvent> performDrag = new IControlStateEventHandler<IMouseEvent>() {

		@Override
		public boolean handleInputEvent(final ControlState srcState, final ControlState targetState,
				final IMouseEvent event) {
			if (selectionAreaStart == null) {
				return false;
			}

			final boolean isDrag = event.isDrag() && event.getButton() == MouseButton.LEFT;
			final boolean isMouseUp = event.isButtonUp() && event.getButton() == MouseButton.LEFT;

			if (srcState == mouseDown && targetState == selectionArea && isDrag) {
				updateSelectionArea(event, ESelectionAreaEvent.init);
				return true;
			}
			if (srcState == selectionArea && targetState == selectionArea && isDrag) {
				updateSelectionArea(event, ESelectionAreaEvent.update);
				return true;
			}
			if (srcState == selectionArea && targetState == init && isMouseUp) {
				updateSelectionArea(event, ESelectionAreaEvent.apply);
				return true;
			}
			return false;
		};

		public Class<IMouseEvent> getExpectedEventType() {
			return IMouseEvent.class;
		}
	};

	private void updateSelectionArea(final IMouseEvent event, final ESelectionAreaEvent areaEvent) {
		final boolean toggle = event.isControlDown();
		final double x = Math.min(selectionAreaStart.getX(), event.getX());
		final double y = Math.min(selectionAreaStart.getY(), event.getY());
		final double width = Math.max(selectionAreaStart.getX(), event.getX()) - x;
		final double height = Math.max(selectionAreaStart.getY(), event.getY()) - y;

		for (ISelectionAreaListener listener : this.listeners) {
			listener.onSelectionArea(areaEvent, x, y, width, height, toggle);
		}
	}

	private void cancelSelectionArea() {
		for (ISelectionAreaListener listener : this.listeners) {
			listener.onSelectionArea(ESelectionAreaEvent.cancel, 0, 0, 0, 0, false);
		}
	}

	@Override
	public void controlStateChanged(ControlState oldState, ControlState newState,
			IControlStateEventHandler<?> transition, Object event) {
		if (newState == mouseDown && event instanceof IMouseEvent) {
			final IMouseEvent me = (IMouseEvent) event;
			selectionAreaStart = new Point2D(me.getX(), me.getY());
		}
	}

	public interface ISelectionAreaListener {

		void onSelectionArea(ESelectionAreaEvent event, double x, double y, double width, double height,
				boolean toggle);

	}

	public static enum ESelectionAreaEvent {
		init, update, apply, cancel
	}

}
