package jvizedit.swtfx.events;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Scene;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import jvizedit.control.core.ControlStateMachine;
import jvizedit.control.core.events.IDragEvent;
import jvizedit.control.dragdrop.EDragDropTransfer;

public class FxDragEvent implements IDragEvent {


	public static EventHandler<Event> addDragEventFilter(final Scene scene, final ControlStateMachine cstm) {
		final EventHandler<Event> handler = event -> {
			if(event instanceof DragEvent) {
				final DragEvent dragEvent = (DragEvent)event;
				final FxDragEvent fxKeyEvent = new FxDragEvent(dragEvent);
				cstm.handleEvent(fxKeyEvent);
			}
		};
		scene.addEventFilter(EventType.ROOT, handler);
		return handler;
	}
	
	private DragEvent wrappedDragEvent;
	
	public FxDragEvent(DragEvent dragEvent) {
		this.wrappedDragEvent = dragEvent;
	}

	@Override
	public DragEvent getRealEvent() {
		return wrappedDragEvent;
	}
	
	@Override
	public double getX() {
		return wrappedDragEvent.getSceneX();
	}

	@Override
	public double getY() {
		return wrappedDragEvent.getSceneY();
	}
	
	@Override
	public EDragDropTransfer getTransferMode() {
		final TransferMode mode = wrappedDragEvent.getTransferMode();
		switch(mode) {
		case COPY:
			return EDragDropTransfer.copy;
		case LINK:
			return EDragDropTransfer.link;
		case MOVE:
			return EDragDropTransfer.move;
		}
		throw new IllegalStateException("Unexpected transfer mode " + mode + ".");
	}
	
	@Override
	public void acceptTransferModes(EDragDropTransfer... transferModes) {
		final List<TransferMode> modes = Arrays.asList(transferModes).stream() //
				.filter(Objects::nonNull) //
				.filter(m -> m != EDragDropTransfer.pseudo) //
				.map(m -> {
					switch (m) {
					case copy:
						return TransferMode.COPY;
					case move:
						return TransferMode.MOVE;
					case link:
						return TransferMode.LINK;
					default:
						throw new IllegalStateException("Unexpected transfer mode " + m + ".");
					}
				}).collect(Collectors.toList());
		final TransferMode [] modeArray = modes.toArray(new TransferMode[0]);
		wrappedDragEvent.acceptTransferModes(modeArray);
	}
	
	@Override
	public boolean isAccepted() {
		return wrappedDragEvent.isAccepted();
	}

	@Override
	public DragEventType getType() {
		EventType<?> type = wrappedDragEvent.getEventType();
		if(type.equals(DragEvent.DRAG_ENTERED) || type.equals(DragEvent.DRAG_ENTERED_TARGET)) {
			if(wrappedDragEvent.getTarget() instanceof Scene) {				
				return DragEventType.dragEnter;
			} else {
				return DragEventType.dragOver;	
			}
		}
		if(type.equals(DragEvent.DRAG_EXITED) || type.equals(DragEvent.DRAG_EXITED_TARGET)) {
			if(wrappedDragEvent.getTarget() instanceof Scene) {				
				return DragEventType.dragExit;
			} else {
				return DragEventType.dragOver;	
			}
		}
		if(type.equals(DragEvent.DRAG_OVER)) {
			return DragEventType.dragOver;
		}
		if(type.equals(DragEvent.DRAG_DROPPED)) {
			return DragEventType.dragDropped;
		}
		throw new IllegalStateException("Unexpected FX Drag Event type: " + type + ".");
	}


}
