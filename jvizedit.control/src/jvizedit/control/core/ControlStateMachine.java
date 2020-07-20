package jvizedit.control.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlStateMachine {
	
	public static final String STATE_INIT = "Init";
	
	private final List<IControlStateUpdateListener> listeners = new ArrayList<>();
	private final Map<String,ControlState> stateMap = new HashMap<>();
	
	private ControlState currentState;
	
	public ControlStateMachine() {
		setCurrentState(getInitState());
	}
	
	public ControlState getInitState() {
		return getOrCreateState(STATE_INIT);
	}
	
	public ControlState getOrCreateState(final String name) {
		ControlState state = stateMap.get(name);
		if(state == null) {
			state = new ControlState(name);
			stateMap.put(name, state);
		}
		return state;
	}
	
	public void setCurrentState(ControlState currentState) {
		this.currentState = currentState;
	}
	
	public ControlState getCurrentState() {
		return currentState;
	}
	
	public void handleEvent(Object event) {
		final ControlState oldState = currentState;
		final int index = currentState.handleEvent(event); //TODO: return a structure, not an index
		if(index >= 0) {
			final ControlState newState = currentState.getState(index);
			final IControlStateTransition<?> transition = currentState.getTransition(index);
			currentState = newState;
			notifyUpdate(oldState, transition, event);
		}
	}
	
	private void notifyUpdate(final ControlState oldState, final IControlStateTransition<?> transition, final Object event) {
		for(final IControlStateUpdateListener listener: listeners) {
			listener.controlStateChanged(oldState, currentState, transition, event);
		}
	}
	
	public void addUpdateListener(final IControlStateUpdateListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeUpdateListener(final IControlStateUpdateListener listener) {
		this.listeners.remove(listener);
	}
	
	
	public static interface IControlStateUpdateListener {
		
		public void controlStateChanged(final ControlState oldState, final ControlState newState, final IControlStateTransition<?> transition, final Object event);
		
	}
	
}
