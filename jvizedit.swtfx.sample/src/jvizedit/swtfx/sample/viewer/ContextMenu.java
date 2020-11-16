package jvizedit.swtfx.sample.viewer;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;

import jvizedit.control.OpenContextMenu.IOpenContextMenuListener;

public class ContextMenu implements IOpenContextMenuListener {

	private final Menu swtMenu;

	public ContextMenu(final Composite parent) {
		this.swtMenu = new Menu(parent);
		parent.setMenu(this.swtMenu);
	}

	@Override
	public void showContextMenu(final double x, final double y) {
		this.swtMenu.setLocation((int) x, (int) y);
		this.swtMenu.setVisible(true);
	}

}
