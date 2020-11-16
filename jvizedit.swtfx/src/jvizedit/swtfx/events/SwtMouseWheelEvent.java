package jvizedit.swtfx.events;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Event;

import jvizedit.control.core.events.IMouseWheelEvent;

public class SwtMouseWheelEvent implements IMouseWheelEvent {

	private final Event swtEvent;

	public SwtMouseWheelEvent(final Event swtEvent) {
		if (swtEvent.type != SWT.MouseWheel) {
			throw new IllegalArgumentException("Mouse Wheel event expected!");
		}
		this.swtEvent = swtEvent;
	}

	@Override
	public Event getRealEvent() {
		return this.swtEvent;
	}

	@Override
	public double wheelCount() {
		return this.swtEvent.count / 20d;
	}

	@Override
	public double getX() {
		return this.swtEvent.x;
	}

	@Override
	public double getY() {
		return this.swtEvent.y;
	}

}
