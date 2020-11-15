package jvizedit.control;

import jvizedit.control.core.ControlState;
import jvizedit.control.core.ControlStateMachine;
import jvizedit.control.core.IControlStateEventHandler;
import jvizedit.control.core.events.IMouseWheelEvent;

public class Zoom implements IControlStateEventHandler<IMouseWheelEvent> {

	private final INavigableArea modelLayer;
	private final ControlState initState;

	public Zoom(final ControlStateMachine cstm, final INavigableArea modelLayer) {
		initState = cstm.getInitState();
		initState.addStateTransition(initState, this);
		this.modelLayer = modelLayer;
	}

	@Override
	public Class<IMouseWheelEvent> getExpectedEventType() {
		return IMouseWheelEvent.class;
	}

	@Override
	public boolean handleInputEvent(ControlState srcState, ControlState targetState, IMouseWheelEvent event) {
		if (srcState == initState && targetState == initState) {
			zoom(event);
			return true;
		}
		return false;
	}

	private void zoom(final IMouseWheelEvent mouseWheelEvent) {

		final double scale = modelLayer.getScale();
		final double factor = 1d + mouseWheelEvent.wheelCount();

		double newScale = Math.max(scale * factor, 0.0005);
		newScale = Math.min(newScale, 1000);
		if (newScale == scale) {
			return;
		}
		modelLayer.setScale(newScale);

		// translate x
		final double mouseX = mouseWheelEvent.getX();
		final double x = modelLayer.getOffsetX();
		final double xDiff = x - mouseX;
		final double newXDiff = xDiff * factor;
		final double xCorrection = newXDiff - xDiff;
		final double newX = x + xCorrection;

		// translate y
		final double mouseY = mouseWheelEvent.getY();
		final double y = modelLayer.getOffsetY();
		final double yDiff = y - mouseY;
		final double newYDiff = yDiff * factor;
		final double yCorrection = newYDiff - yDiff;
		final double newY = y + yCorrection;

		modelLayer.setOffset(newX, newY);
	}
}
