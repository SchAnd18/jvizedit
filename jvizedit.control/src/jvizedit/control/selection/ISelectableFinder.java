package jvizedit.control.selection;

import java.util.Collection;

import jvizedit.control.core.events.IMouseEvent;


// TODO: check if an default implementation of this is possible when there is an interface that does the coordinate calculations
public interface ISelectableFinder {

	ISelectableController findControllerAt(double x, double y, IMouseEvent event);
	
	Collection<ISelectableController> findControllersIn(double x, double y, double width, double height);
	
	Collection<ISelectableController> findControllersIntersecting(int x, int y, int width, int height);
}
