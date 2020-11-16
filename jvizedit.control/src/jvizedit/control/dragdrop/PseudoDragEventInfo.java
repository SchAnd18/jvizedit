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

	public PseudoDragEventInfo(final DragSelection dragSelection, final IWrappedEvent sourceEvent,
			final EDiagramDragEventType type, final double x, final double y, final ISelectableController dragSource) {
		this.sourceEvent = sourceEvent;
		this.dragSelection = dragSelection;
		this.type = type;
		this.x = x;
		this.y = y;
		this.dragSource = dragSource;
	}

	@Override
	public EDiagramDragEventType getType() {
		return this.type;
	}

	@Override
	public double getX() {
		return this.x;
	}

	@Override
	public double getY() {
		return this.y;
	}

	@Override
	public IWrappedEvent getSourceEvent() {
		return this.sourceEvent;
	}

	@Override
	public EDragDropTransfer getTransfer() {
		return EDragDropTransfer.pseudo;
	}

	@Override
	public boolean isAccepted() {
		return this.dragSelection.isPseudoTransferAccepted();
	}

	public ISelectableController getDragSource() {
		return this.dragSource;
	}

	@Override
	public void setAcceptedTransfers(final EDragDropTransfer... transfers) {
		for (final EDragDropTransfer t : transfers) {
			if (t == EDragDropTransfer.pseudo) {
				this.dragSelection.acceptPseudoTransfer();
			}
		}
	}

}
