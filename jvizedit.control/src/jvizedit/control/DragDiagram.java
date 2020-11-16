package jvizedit.control;

import jvizedit.control.core.ControlState;
import jvizedit.control.core.ControlStateMachine;
import jvizedit.control.core.IControlStateEventHandler;
import jvizedit.control.core.events.IMouseEvent;
import jvizedit.control.core.events.IMouseEvent.MouseButton;
import jvizedit.control.core.events.Point2D;

public class DragDiagram implements IControlStateEventHandler<IMouseEvent> {

	public static final String STATE_DRAG_DIAGRAM = "DragDiagram";

	private final ControlState init;
	private final ControlState dragDiagram;
	private final ControlState rightMouseDown;

	private final INavigableArea modelLayer;
	private Point2D mouseStartPos;
	private Point2D rootStartPos;

	public DragDiagram(final ControlStateMachine cstm, final OpenContextMenu ocm, final INavigableArea modelLayer) {
		this.rightMouseDown = ocm.getRightMouseDownState();
		this.dragDiagram = cstm.getOrCreateState(STATE_DRAG_DIAGRAM);
		this.init = cstm.getInitState();

		this.rightMouseDown.addStateTransition(this.dragDiagram, this);
		this.dragDiagram.addStateTransition(this.dragDiagram, this);
		this.dragDiagram.addStateTransition(this.init, this);

		this.modelLayer = modelLayer;
	}

	public ControlState getDragDiagramState() {
		return this.dragDiagram;
	}

	@Override
	public Class<IMouseEvent> getExpectedEventType() {
		return IMouseEvent.class;
	}

	@Override
	public boolean handleInputEvent(final ControlState srcState, final ControlState targetState,
			final IMouseEvent event) {

		final boolean isDrag = event.isDrag() && (event.getButton() == MouseButton.RIGHT);
		if ((srcState == this.rightMouseDown) && (targetState == this.dragDiagram) && isDrag) {
			startDrag(event);
			return true;
		}

		if ((srcState == this.dragDiagram) && (targetState == this.dragDiagram) && isDrag) {
			updateDrag(event);
			return true;
		}

		final boolean isMouseUp = event.isButtonUp() && (event.getButton() == MouseButton.RIGHT);
		if ((srcState == this.dragDiagram) && (targetState == this.init) && isMouseUp) {
			finishDrag(event);
			return true;
		}

		return false;
	}

	private void startDrag(final IMouseEvent dragEvent) {
		this.mouseStartPos = new Point2D(dragEvent.getX(), dragEvent.getY());
		final double offX = this.modelLayer.getOffsetX();
		final double offY = this.modelLayer.getOffsetY();
		this.rootStartPos = new Point2D(offX, offY);
	}

	private void updateDrag(final IMouseEvent dragEvent) {
		performMove(dragEvent);
	}

	private void finishDrag(final IMouseEvent mouseUpEvent) {
		performMove(mouseUpEvent);
	}

	private void performMove(final IMouseEvent globalMouseEvent) {
		final double xDiff = globalMouseEvent.getX() - this.mouseStartPos.getX();
		final double yDiff = globalMouseEvent.getY() - this.mouseStartPos.getY();
		final double x = this.rootStartPos.getX() + xDiff;
		final double y = this.rootStartPos.getY() + yDiff;
		this.modelLayer.setOffset(x, y);
	}

}
