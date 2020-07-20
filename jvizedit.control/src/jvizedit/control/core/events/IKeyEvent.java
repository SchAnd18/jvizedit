package jvizedit.control.core.events;

public interface IKeyEvent {

	boolean isKeyReleased();
	
	boolean isKeyPressed();
	
	Key getKey();
	
	
	enum Key {
		UNKNOWN,
		ESCAPE
	}
}
