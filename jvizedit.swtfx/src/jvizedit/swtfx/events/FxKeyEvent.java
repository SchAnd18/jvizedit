package jvizedit.swtfx.events;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jvizedit.control.core.ControlStateMachine;
import jvizedit.control.core.events.IKeyEvent;

public class FxKeyEvent implements IKeyEvent {

	public static FilteredEventHandler<KeyEvent> addKeyEventFilter(final Scene scene, final ControlStateMachine cstm) {
		final EventHandler<KeyEvent> handler = event -> {
			final KeyEvent keyEvent = event;
			final FxKeyEvent fxKeyEvent = new FxKeyEvent(keyEvent);
			final boolean handled = cstm.handleEvent(fxKeyEvent);
			if (handled) {
				event.consume();
			}
		};
		final FilteredEventHandler<KeyEvent> filtered = new FilteredEventHandler<>(handler);
		scene.addEventFilter(KeyEvent.ANY, filtered);
		return filtered;
	}

	private final KeyEvent fxEvent;

	public FxKeyEvent(final KeyEvent fxEvent) {
		this.fxEvent = fxEvent;
	}

	@Override
	public KeyEvent getRealEvent() {
		return this.fxEvent;
	}

	@Override
	public boolean isKeyReleased() {
		return this.fxEvent.getEventType() == KeyEvent.KEY_RELEASED;
	}

	@Override
	public boolean isKeyPressed() {
		return this.fxEvent.getEventType() == KeyEvent.KEY_PRESSED;
	}

	@Override
	public Key getKey() {
		if (this.fxEvent.getCode() == KeyCode.ESCAPE) {
			return Key.ESCAPE;
		}
		return Key.UNKNOWN;
	}

}
