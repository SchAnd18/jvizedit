package jvizedit.swtfx.sample.viewer;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;

import jvizedit.control.OpenContextMenu.IOpenContextMenuListener;

public class ContextMenu implements IOpenContextMenuListener{

	private final Menu swtMenu;
	
	public ContextMenu (Composite parent) {
		swtMenu = new Menu(parent);
		parent.setMenu(swtMenu);
	}
	
	@Override
	public void showContextMenu(double x, double y) {
		swtMenu.setLocation((int)x,(int)y);
		swtMenu.setVisible(true);
	}
	
}
