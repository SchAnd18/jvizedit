package jvizedit.control.core;

public interface IControlStateEventHandler<T> {

	boolean handleInputEvent(ControlState srcState, ControlState targetState, T event);

	Class<T> getExpectedEventType();
}
