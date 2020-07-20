package jvizedit.control.core.events;

public interface IMouseEvent {

	boolean isDrag();
	
	boolean isButtonDown();
	
	boolean isButtonUp();
	
	double getX();
	
	double getY();

	boolean isControlDown();
	
	MouseButton getButton();
	
	public enum MouseButton {
		UNKNOWN,
		NONE,
		LEFT,
		RIGHT
	}
}
