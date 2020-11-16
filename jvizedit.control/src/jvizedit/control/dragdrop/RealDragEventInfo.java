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
		return this.type;
	}

	@Override
	public EDragDropTransfer getTransfer() {
		return this.sourceEvent.getTransferMode();
	}

	@Override
	public IDragEvent getSourceEvent() {
		return this.sourceEvent;
	}

	@Override
	public void setAcceptedTransfers(final EDragDropTransfer... transfers) {
		this.sourceEvent.acceptTransferModes(transfers);
	}

	@Override
	public boolean isAccepted() {
		return this.sourceEvent.isAccepted();
	}

	@Override
	public double getX() {
		return this.sourceEvent.getX();
	}

	@Override
	public double getY() {
		return this.sourceEvent.getY();
	}

}
