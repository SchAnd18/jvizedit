package jvizedit.control.core.events;

import jvizedit.control.dragdrop.EDragDropTransfer;

public interface IDragEvent extends IWrappedEvent {

	DragEventType getType();
	
	double getX();
	
	double getY();
	
	EDragDropTransfer getTransferMode();
	
	boolean isAccepted();
	
	void acceptTransferModes(EDragDropTransfer...trasferModes);
	
	enum DragEventType {
		dragEnter,
		dragExit,
		dragOver,
		dragDropped
	}
	
}
