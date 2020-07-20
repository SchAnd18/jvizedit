package jvizedit.control.selection;

import jvizedit.mvc.IControllerBase;

public interface ISelectableController extends IControllerBase {
	
	default boolean isSelectable() {
		return true;
	}
	
	default void setSelectionStatus(boolean isSelected) {
	}
}
