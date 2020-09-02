package jvizedit.control.dragdrop;

import jvizedit.control.core.events.IWrappedEvent;

public interface IDragEventInfo {

	EDiagramDragEventType getType();
	
	EDragDropTransfer getTransfer();
	
	IWrappedEvent getSourceEvent();
	
	void setAcceptedTransfers(EDragDropTransfer...transfers);
	
	boolean isAccepted();
	
	double getX();
	
	double getY();
}
