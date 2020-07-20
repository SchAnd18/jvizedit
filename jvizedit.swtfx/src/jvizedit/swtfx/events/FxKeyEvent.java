package jvizedit.swtfx.events;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jvizedit.control.core.ControlStateMachine;
import jvizedit.control.core.events.IKeyEvent;

public class FxKeyEvent implements IKeyEvent {

	public static EventHandler<Event> addKeyEventFilter(final Scene scene, final ControlStateMachine cstm) {
		final EventHandler<Event> handler = event -> {
			if(event instanceof KeyEvent) {
				final KeyEvent keyEvent = (KeyEvent)event;
				final FxKeyEvent fxKeyEvent = new FxKeyEvent(keyEvent);
				cstm.handleEvent(fxKeyEvent);
			}
		};
		scene.addEventFilter(EventType.ROOT, handler);
		return handler;
	}
	
	
	private final KeyEvent fxEvent;
	
	public FxKeyEvent(final KeyEvent fxEvent) {
		this.fxEvent = fxEvent;
	}
	
	@Override
	public boolean isKeyReleased() {
		return fxEvent.getEventType() == KeyEvent.KEY_RELEASED;
	}

	@Override
	public boolean isKeyPressed() {
		return fxEvent.getEventType() == KeyEvent.KEY_PRESSED;
	}

	@Override
	public Key getKey() {
		if(fxEvent.getCode() == KeyCode.ESCAPE) {
			return Key.ESCAPE;
		}
		return Key.UNKNOWN;
	}

}
