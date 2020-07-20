package jvizedit.control;


import jvizedit.control.core.ControlState;
import jvizedit.control.core.ControlStateMachine;
import jvizedit.control.core.IControlStateTransition;
import jvizedit.control.core.events.IMouseEvent;
import jvizedit.control.core.events.IMouseEvent.MouseButton;
import jvizedit.control.core.events.Point2D;

public class DragDiagram implements IControlStateTransition<IMouseEvent> {

	public static final String STATE_DRAG_DIAGRAM = "DragDiagram";
	
	private final ControlState init;
	private final ControlState dragDiagram;
	private final ControlState rightMouseDown;
	
	private final IModelLayer modelLayer;
	private Point2D mouseStartPos;
	private Point2D rootStartPos;
	
	public DragDiagram(final ControlStateMachine cstm, final OpenContextMenu ocm, final IModelLayer modelLayer) {
		this.rightMouseDown = ocm.getRightMouseDownState();
		this.dragDiagram = cstm.getOrCreateState(STATE_DRAG_DIAGRAM);
		this.init = cstm.getInitState();
		
		this.rightMouseDown.addStateTransition(dragDiagram, this);
		this.dragDiagram.addStateTransition(dragDiagram, this);
		this.dragDiagram.addStateTransition(init, this);
		
		this.modelLayer = modelLayer;
	}
	
	public ControlState getDragDiagramState() {
		return dragDiagram;
	}
	
	@Override
	public Class<IMouseEvent> getExpectedEventType() {
		return IMouseEvent.class;
	}
	
	@Override
	public boolean handleInputEvent(ControlState srcState, ControlState targetState, IMouseEvent event) {
		
		final boolean isDrag = event.isDrag() && event.getButton() == MouseButton.RIGHT;
		if(srcState == rightMouseDown && targetState == dragDiagram && isDrag) {
			startDrag(event);
			return true;
		}
		
		if(srcState == dragDiagram && targetState == dragDiagram && isDrag) {
			updateDrag(event);
			return true; 
		}
		
		final boolean isMouseUp = event.isButtonUp() && event.getButton() == MouseButton.RIGHT;
		if(srcState == dragDiagram && targetState == init && isMouseUp) {
			finishDrag(event);
			return true;
		}
		
		return false;
	}
	
	private void startDrag(IMouseEvent dragEvent) {
		mouseStartPos = new Point2D(dragEvent.getX(), dragEvent.getY());
		final double offX = modelLayer.getOffsetX();
		final double offY = modelLayer.getOffsetY();
		rootStartPos = new Point2D(offX, offY);
	}
	
	private void updateDrag(IMouseEvent dragEvent) {
		performMove(dragEvent);
	}
	
	private void finishDrag(IMouseEvent mouseUpEvent) {
		performMove(mouseUpEvent);
	}
	
	private void performMove(final IMouseEvent globalMouseEvent) {
		final double xDiff = globalMouseEvent.getX() - mouseStartPos.getX();
		final double yDiff = globalMouseEvent.getY() - mouseStartPos.getY();
		final double x = rootStartPos.getX() + xDiff;
		final double y = rootStartPos.getY() + yDiff;
		modelLayer.setOffset(x, y);
	}
	
}
