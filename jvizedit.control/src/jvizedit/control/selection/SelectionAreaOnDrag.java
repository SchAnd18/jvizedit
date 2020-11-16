package jvizedit.control.selection;

import java.util.ArrayList;
import java.util.List;

import jvizedit.control.core.ControlState;
import jvizedit.control.core.ControlStateMachine;
import jvizedit.control.core.ControlStateMachine.IControlStateUpdateListener;
import jvizedit.control.core.IControlStateEventHandler;
import jvizedit.control.core.events.IKeyEvent;
import jvizedit.control.core.events.IKeyEvent.Key;
import jvizedit.control.core.events.IMouseEvent;
import jvizedit.control.core.events.IMouseEvent.MouseButton;
import jvizedit.control.core.events.Point2D;

public class SelectionAreaOnDrag implements IControlStateUpdateListener {

	private final List<ISelectionAreaListener> listeners = new ArrayList<>();
	private final ControlState init;
	private final ControlState mouseDown;
	private final ControlState selectionArea;

	private Point2D selectionAreaStart;

	public SelectionAreaOnDrag(final ControlStateMachine cstm, final SelectOnClick selectOnClick) {
		this.init = cstm.getOrCreateState("Init");
		this.mouseDown = selectOnClick.getMouseDownState();
		this.selectionArea = cstm.getOrCreateState("SelectionArea");

		this.mouseDown.addStateTransition(this.selectionArea, this.performDrag);
		this.selectionArea.addStateTransition(this.selectionArea, this.performDrag);
		this.selectionArea.addStateTransition(this.init, this.performDrag);

		this.selectionArea.addStateTransition(this.init, this.abortDrag);

		cstm.addUpdateListener(this);
	}

	public void addSelectionAreaListener(final ISelectionAreaListener listener) {
		this.listeners.add(listener);
	}

	public void removeSelectionAreaListener(final ISelectionAreaListener listener) {
		this.listeners.remove(listener);
	}

	private final IControlStateEventHandler<IKeyEvent> abortDrag = new IControlStateEventHandler<IKeyEvent>() {
		@Override
		public boolean handleInputEvent(final ControlState srcState, final ControlState targetState,
				final IKeyEvent event) {
			final boolean escUp = event.isKeyReleased() && (event.getKey() == Key.ESCAPE);
			if ((srcState == SelectionAreaOnDrag.this.selectionArea) && (targetState == SelectionAreaOnDrag.this.init)
					&& escUp) {
				SelectionAreaOnDrag.this.selectionAreaStart = null;
				cancelSelectionArea();
				return true;
			}
			return false;
		}

		@Override
		public Class<IKeyEvent> getExpectedEventType() {
			return IKeyEvent.class;
		}
	};

	private final IControlStateEventHandler<IMouseEvent> performDrag = new IControlStateEventHandler<IMouseEvent>() {

		@Override
		public boolean handleInputEvent(final ControlState srcState, final ControlState targetState,
				final IMouseEvent event) {
			if (SelectionAreaOnDrag.this.selectionAreaStart == null) {
				return false;
			}

			final boolean isDrag = event.isDrag() && (event.getButton() == MouseButton.LEFT);
			final boolean isMouseUp = event.isButtonUp() && (event.getButton() == MouseButton.LEFT);

			if ((srcState == SelectionAreaOnDrag.this.mouseDown)
					&& (targetState == SelectionAreaOnDrag.this.selectionArea) && isDrag) {
				updateSelectionArea(event, ESelectionAreaEvent.init);
				return true;
			}
			if ((srcState == SelectionAreaOnDrag.this.selectionArea)
					&& (targetState == SelectionAreaOnDrag.this.selectionArea) && isDrag) {
				updateSelectionArea(event, ESelectionAreaEvent.update);
				return true;
			}
			if ((srcState == SelectionAreaOnDrag.this.selectionArea) && (targetState == SelectionAreaOnDrag.this.init)
					&& isMouseUp) {
				updateSelectionArea(event, ESelectionAreaEvent.apply);
				return true;
			}
			return false;
		};

		@Override
		public Class<IMouseEvent> getExpectedEventType() {
			return IMouseEvent.class;
		}
	};

	private void updateSelectionArea(final IMouseEvent event, final ESelectionAreaEvent areaEvent) {
		final boolean toggle = event.isControlDown();
		final double x = Math.min(this.selectionAreaStart.getX(), event.getX());
		final double y = Math.min(this.selectionAreaStart.getY(), event.getY());
		final double width = Math.max(this.selectionAreaStart.getX(), event.getX()) - x;
		final double height = Math.max(this.selectionAreaStart.getY(), event.getY()) - y;

		for (final ISelectionAreaListener listener : this.listeners) {
			listener.onSelectionArea(areaEvent, x, y, width, height, toggle);
		}
	}

	private void cancelSelectionArea() {
		for (final ISelectionAreaListener listener : this.listeners) {
			listener.onSelectionArea(ESelectionAreaEvent.cancel, 0, 0, 0, 0, false);
		}
	}

	@Override
	public void controlStateChanged(final ControlState oldState, final ControlState newState,
			final IControlStateEventHandler<?> transition, final Object event) {
		if ((newState == this.mouseDown) && (event instanceof IMouseEvent)) {
			final IMouseEvent me = (IMouseEvent) event;
			this.selectionAreaStart = new Point2D(me.getX(), me.getY());
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
