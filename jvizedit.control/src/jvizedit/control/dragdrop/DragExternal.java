package jvizedit.control.dragdrop;

import java.util.ArrayList;
import java.util.List;

import jvizedit.control.core.ControlState;
import jvizedit.control.core.ControlStateMachine;
import jvizedit.control.core.IControlStateEventHandler;
import jvizedit.control.core.events.IDragEvent;
import jvizedit.control.core.events.IDragEvent.DragEventType;

public class DragExternal implements IControlStateEventHandler<IDragEvent> {

	public static final String STATE_DRAG_EXTERNAL = "DragExternal";

	private final ControlState init;
	private final ControlState dragExternal;

	private final List<IDragDropListener> listeners = new ArrayList<>();

	public DragExternal(final ControlStateMachine cstm) {
		this.init = cstm.getInitState();
		this.dragExternal = cstm.getOrCreateState(STATE_DRAG_EXTERNAL);

		this.init.addStateTransition(this.dragExternal, this);
		this.dragExternal.addStateTransition(this.dragExternal, this);
		this.dragExternal.addStateTransition(this.init, this);
	}

	@Override
	public Class<IDragEvent> getExpectedEventType() {
		return IDragEvent.class;
	}

	@Override
	public boolean handleInputEvent(final ControlState srcState, final ControlState targetState, final IDragEvent event) {
		final DragEventType dragEventType = event.getType();

		final boolean potDragEnterEvent = (dragEventType == DragEventType.dragEnter) || (dragEventType == DragEventType.dragOver);
		if ((srcState == this.init) && (targetState == this.dragExternal) && potDragEnterEvent) {
			notifyListeners(EDiagramDragEventType.startDrag, event);
			return true;
		}
		if ((srcState == this.dragExternal) && (targetState == this.dragExternal)) {
			if (dragEventType == DragEventType.dragOver) {
				notifyListeners(EDiagramDragEventType.continueDrag, event);
				return true;
			}
			if (dragEventType == DragEventType.dragDropped) {
				notifyListeners(EDiagramDragEventType.drop, event);
				return true;
			}
		}
		if ((srcState == this.dragExternal) && (targetState == this.init) && (dragEventType == DragEventType.dragExit)) {
			notifyListeners(EDiagramDragEventType.abortDrag, event);
			return true;
		}
		return false;
	}

	public void addDragDropListener(final IDragDropListener listener) {
		this.listeners.add(listener);
	}

	public void removeDragDropListener(final IDragDropListener listener) {
		this.listeners.remove(listener);
	}

	private void notifyListeners(final EDiagramDragEventType type, final IDragEvent sourceEvent) {
		final RealDragEventInfo realDragEvent = new RealDragEventInfo(sourceEvent, type);
		for (final IDragDropListener l : this.listeners) {
			l.dragEvent(realDragEvent);
		}
	}

}
