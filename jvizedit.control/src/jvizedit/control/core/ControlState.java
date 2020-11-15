package jvizedit.control.core;

import java.util.ArrayList;
import java.util.List;

public class ControlState {

	private final List<IControlStateEventHandler<?>> transitions = new ArrayList<>();
	private final List<ControlState> targetStates = new ArrayList<>();
	private final String stateName;

	public ControlState(final String stateName) {
		this.stateName = stateName;
	}

	public String getStateName() {
		return stateName;
	}

	public void addStateTransition(ControlState targetState, final IControlStateEventHandler<?> stateTransition) {
		transitions.add(0, stateTransition);
		targetStates.add(0, targetState);
	}

	public int handleEvent(Object event) {
		for (int i = 0; i < transitions.size(); i++) {
			final IControlStateEventHandler<?> transition = transitions.get(i);
			final ControlState targetState = targetStates.get(i);

			final boolean match = tryHandleEvent(targetState, transition, event);
			if (match) {
				return i;
			}
		}
		return -1;
	}

	private <T> boolean tryHandleEvent(final ControlState targetState, final IControlStateEventHandler<T> transition,
			final Object event) {
		final Class<T> expectedEventType = transition.getExpectedEventType();
		if (!expectedEventType.isInstance(event)) {
			return false;
		}
		final T eventTyped = expectedEventType.cast(event);
		final boolean result = transition.handleInputEvent(this, targetState, eventTyped);
		return result;
	}

	public IControlStateEventHandler<?> getTransition(int index) {
		return transitions.get(index);
	}

	public ControlState getState(int index) {
		return targetStates.get(index);
	}

}
