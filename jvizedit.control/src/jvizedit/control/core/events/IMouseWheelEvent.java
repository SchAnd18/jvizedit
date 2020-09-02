package jvizedit.control.core.events;

public interface IMouseWheelEvent extends IWrappedEvent {

	double wheelCount();
	
	double getX();
	
	double getY();
	
}
