package jvizedit.control.core.events;

public interface IKeyEvent extends IWrappedEvent {

	boolean isKeyReleased();
	
	boolean isKeyPressed();
	
	Key getKey();
	
	
	enum Key {
		UNKNOWN,
		ESCAPE
	}
}
