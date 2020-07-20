package jvizedit.swtfx.events;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import jvizedit.control.core.events.IKeyEvent;

public class FxKeyEvent implements IKeyEvent {

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
