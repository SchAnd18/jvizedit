package jvizedit.control.dragdrop;

public enum EDragDropTransfer {
	pseudo,
	copy,
	move,
	link;
	
	public static final EDragDropTransfer [] NONE = new EDragDropTransfer [0]; 
}
