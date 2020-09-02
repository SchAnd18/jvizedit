package jvizedit.control.dragdrop;

import jvizedit.control.core.events.IWrappedEvent;
import jvizedit.control.selection.ISelectableController;

public class PseudoDragEventInfo implements IDragEventInfo {

	private final ISelectableController dragSource;
	private final IWrappedEvent sourceEvent;
	private final DragSelection dragSelection;
	private final EDiagramDragEventType type;
	private final double x;
	private final double y;
	
	public PseudoDragEventInfo(DragSelection dragSelection, IWrappedEvent sourceEvent, EDiagramDragEventType type, double x, double y, ISelectableController dragSource) {
		this.sourceEvent = sourceEvent;
		this.dragSelection = dragSelection;
		this.type = type;
		this.x = x;
		this.y = y;
		this.dragSource = dragSource;
	}
	
	public EDiagramDragEventType getType() {
		return this.type;
	}

	@Override
	public double getX() {
		return x;
	}
	
	@Override
	public double getY() {
		return y;
	}
	
	@Override
	public IWrappedEvent getSourceEvent() {
		return sourceEvent;
	}
	
	@Override
	public EDragDropTransfer getTransfer() {
		return EDragDropTransfer.pseudo;
	}
	
	@Override
	public boolean isAccepted() {
		return dragSelection.isPseudoTransferAccepted();
	}

	public ISelectableController getDragSource() {
		return dragSource;
	}
	
	@Override
	public void setAcceptedTransfers(EDragDropTransfer... transfers) {
		for(EDragDropTransfer t: transfers) {
			if(t == EDragDropTransfer.pseudo) {
				dragSelection.acceptPseudoTransfer();
			}
		}
	}
	
	
	
}
