package jvizedit.control.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ControlStateMachine {

	public static final String STATE_INIT = "Init";

	private final List<IControlStateUpdateListener> listeners = new ArrayList<>();
	private final Map<String, ControlState> stateMap = new HashMap<>();

	private ControlState currentState;

	public ControlStateMachine() {
		setCurrentState(getInitState());
	}

	public ControlState getInitState() {
		return getOrCreateState(STATE_INIT);
	}

	public ControlState getOrCreateState(final String name) {
		ControlState state = this.stateMap.get(name);
		if (state == null) {
			state = new ControlState(name);
			this.stateMap.put(name, state);
		}
		return state;
	}

	public void setCurrentState(final ControlState currentState) {
		this.currentState = currentState;
	}

	public ControlState getCurrentState() {
		return this.currentState;
	}

	public boolean handleEvent(final Object event) {
		final ControlState oldState = this.currentState;
		final int index = this.currentState.handleEvent(event); // TODO: return a structure, not an index
		if (index >= 0) {
			final ControlState newState = this.currentState.getState(index);
			final IControlStateEventHandler<?> transition = this.currentState.getTransition(index);
			this.currentState = newState;
			notifyUpdate(oldState, transition, event);
			return true;
		} else {
			return false;
		}
	}

	private void notifyUpdate(final ControlState oldState, final IControlStateEventHandler<?> transition,
			final Object event) {
		for (final IControlStateUpdateListener listener : this.listeners) {
			listener.controlStateChanged(oldState, this.currentState, transition, event);
		}
	}

	public void addUpdateListener(final IControlStateUpdateListener listener) {
		this.listeners.add(listener);
	}

	public void removeUpdateListener(final IControlStateUpdateListener listener) {
		this.listeners.remove(listener);
	}

	public static interface IControlStateUpdateListener {

		public void controlStateChanged(final ControlState oldState, final ControlState newState,
				final IControlStateEventHandler<?> transition, final Object event);

	}

}
