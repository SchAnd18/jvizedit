package jvizedit.control.dragdrop;

import jvizedit.control.core.events.IDragEvent;

public class RealDragEventInfo implements IDragEventInfo {

	private final IDragEvent sourceEvent;
	private final EDiagramDragEventType type;

	public RealDragEventInfo(final IDragEvent sourceEvent, final EDiagramDragEventType type) {
		this.sourceEvent = sourceEvent;
		this.type = type;
	}

	@Override
	public EDiagramDragEventType getType() {
		return type;
	}

	@Override
	public EDragDropTransfer getTransfer() {
		return sourceEvent.getTransferMode();
	}

	@Override
	public IDragEvent getSourceEvent() {
		return sourceEvent;
	}

	@Override
	public void setAcceptedTransfers(EDragDropTransfer... transfers) {
		sourceEvent.acceptTransferModes(transfers);
	}

	@Override
	public boolean isAccepted() {
		return sourceEvent.isAccepted();
	}

	@Override
	public double getX() {
		return sourceEvent.getX();
	}

	@Override
	public double getY() {
		return sourceEvent.getY();
	}

}
