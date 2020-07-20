package jvizedit.control.core.events;

public interface IMouseWheelEvent {

	//1d + (mouseWheelEvent.count / 20d);
	double wheelFactor();
	
	double getX();
	
	double getY();
	
}
