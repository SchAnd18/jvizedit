package jvizedit.control;

import java.util.ArrayList;
import java.util.List;

import jvizedit.control.core.ControlState;
import jvizedit.control.core.ControlStateMachine;
import jvizedit.control.core.IControlStateEventHandler;
import jvizedit.control.core.events.IKeyEvent;
import jvizedit.control.core.events.IKeyEvent.Key;
import jvizedit.control.core.events.IMouseEvent;
import jvizedit.control.core.events.IMouseEvent.MouseButton;
import jvizedit.control.selection.ISelectableController;
import jvizedit.control.selection.ISelectableFinder;
import jvizedit.control.selection.SelectOnClick;
import jvizedit.control.selection.SelectOnClick.SelectionUpdate;

public class DragSelection {

	public static final String STATE_DRAG_SELECTION = "DragSelection";
	
	private final ISelectableFinder selectableFinder;
	
	private final ControlState init;
	private final ControlState dragSelection;
	private final ControlState mouseDownState;

	private final List<IDragDropListener> listeners = new ArrayList<>();
	
	private final SelectOnClick selectOnClick;
	
	private ISelectableController currentSource;
	private double currentMouseX;
	private double currentMouseY;

	public DragSelection(final ControlStateMachine cstm, final SelectOnClick usc, final ISelectableFinder selectableFinder) {
		init = cstm.getInitState();
		dragSelection = cstm.getOrCreateState(STATE_DRAG_SELECTION);
		mouseDownState = usc.getMouseDownOnSelectableState();

		mouseDownState.addStateTransition(dragSelection, performDrag);
		dragSelection.addStateTransition(dragSelection, performDrag);
		dragSelection.addStateTransition(init, performDrag);
		dragSelection.addStateTransition(init, abortDrag);

		this.selectOnClick = usc;
		
		this.selectableFinder = selectableFinder;
	}
	
	public ControlState getDragSelection() {
		return dragSelection;
	}
	
	public void addDragDropListener(final IDragDropListener listener) {
		this.listeners.add(listener);
	}
	
	public void removeDragDropListener(final IDragDropListener listener) {
		this.listeners.remove(listener);
	}

	private final IControlStateEventHandler<IKeyEvent> abortDrag = new IControlStateEventHandler<IKeyEvent>() {
		public boolean handleInputEvent(ControlState srcState, ControlState targetState, IKeyEvent event) {
			final boolean escUp = event.isKeyReleased() && event.getKey() == Key.ESCAPE;
			if (srcState == dragSelection && escUp) {
				notifyListeners(EDragDropEventType.abortDrag, currentMouseX, currentMouseY);
				return true;
			}
			return false;
		}
		
		public Class<IKeyEvent> getExpectedEventType() {
			return IKeyEvent.class;
		}
	};
	
	private final IControlStateEventHandler<IMouseEvent> performDrag = new IControlStateEventHandler<IMouseEvent>() {

		@Override
		public boolean handleInputEvent(ControlState srcState, ControlState targetState, IMouseEvent event) {
			final boolean isMouseUp = event.getButton() == MouseButton.LEFT && event.isButtonUp();
			final boolean isDragEvent = event.getButton() == MouseButton.LEFT && event.isDrag();
			final double x = event.getX();
			final double y = event.getY();
			currentMouseX = x;
			currentMouseY = y;
			
			EDragDropEventType eventType = null;
			if (srcState == mouseDownState && targetState == dragSelection && isDragEvent) {
				final SelectionUpdate update = event.isControlDown() ? SelectionUpdate.TOGGLE : SelectionUpdate.SET_IF_NOT_SELECTED;
				selectOnClick.updateSelection(event, srcState, update);
				currentSource = selectableFinder.findControllerAt(x, y, event);
				eventType = EDragDropEventType.startDrag;
			}
			else if (srcState == dragSelection && targetState == dragSelection && isDragEvent) {
				eventType = EDragDropEventType.continueDrag;
			}
			else if (srcState == dragSelection && targetState == init && isMouseUp) {
				eventType = EDragDropEventType.drop;
			}
			
			if(eventType != null) {
				notifyListeners(eventType,x,y);
				return true;
			} else {
				return false;
			}
		}

		@Override
		public Class<IMouseEvent> getExpectedEventType() {
			return IMouseEvent.class;
		}
	};


	private void notifyListeners(final EDragDropEventType type, final double x, final double y ) {
		for(IDragDropListener l: this.listeners) {
			l.dragEvent(type, x, y, currentSource);
		}
	}
	
	public interface IDragDropListener {
		
		void dragEvent(EDragDropEventType type, double x, double y, ISelectableController dragSource);
		
	}
	
	public static enum EDragDropEventType {
		startDrag,
		continueDrag,
		drop,
		abortDrag
	}
}
