package jvizedit.control.core;

//TODO: find another name for this. E.g. IControlStateMachineExtension
public interface IControlStateTransition<T> {
	
	boolean handleInputEvent(ControlState srcState, ControlState targetState, T event);
	
	Class<T> getExpectedEventType();
}
