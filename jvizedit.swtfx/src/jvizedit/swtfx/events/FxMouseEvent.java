package jvizedit.swtfx.events;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import jvizedit.control.core.ControlStateMachine;
import jvizedit.control.core.events.IMouseEvent;

public class FxMouseEvent implements IMouseEvent {

	public static EventHandler<Event> addMouseEventFilter(final Scene scene, final ControlStateMachine cstm) {
		final EventHandler<Event> handler = event -> {
			if (event instanceof MouseEvent) {
				final MouseEvent mouseEvent = (MouseEvent) event;
				final FxMouseEvent fxKeyEvent = new FxMouseEvent(mouseEvent);
				cstm.handleEvent(fxKeyEvent);
			}

		};
		scene.addEventFilter(EventType.ROOT, handler);
		return handler;
	}

	private final MouseEvent wrappedMouseEvent;

	public FxMouseEvent(final MouseEvent wrappedMouseEvent) {
		this.wrappedMouseEvent = wrappedMouseEvent;
	}

	@Override
	public MouseEvent getRealEvent() {
		return wrappedMouseEvent;
	}

	@Override
	public boolean isDrag() {
		return MouseEvent.DRAG_DETECTED.equals(wrappedMouseEvent.getEventType())
				|| MouseEvent.MOUSE_DRAGGED.equals(wrappedMouseEvent.getEventType());
	}

	@Override
	public boolean isButtonDown() {
		return MouseEvent.MOUSE_PRESSED.equals(wrappedMouseEvent.getEventType());
	}

	@Override
	public boolean isButtonUp() {
		return MouseEvent.MOUSE_RELEASED.equals(wrappedMouseEvent.getEventType());
	}

	@Override
	public double getX() {
		return wrappedMouseEvent.getSceneX();
	}

	@Override
	public double getY() {
		return wrappedMouseEvent.getSceneY();
	}

	@Override
	public boolean isControlDown() {
		return wrappedMouseEvent.isControlDown();
	}

	@Override
	public MouseButton getButton() {
		switch (wrappedMouseEvent.getButton()) {
		case NONE:
			return MouseButton.NONE;
		case PRIMARY:
			return MouseButton.LEFT;
		case SECONDARY:
			return MouseButton.RIGHT;
		case MIDDLE:
		default:
			return MouseButton.UNKNOWN;
		}
	}

}
