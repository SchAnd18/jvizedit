package jvizedit.swtfx;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jvizedit.control.selection.SelectionAreaOnDrag.ESelectionAreaEvent;
import jvizedit.control.selection.SelectionAreaOnDrag.ISelectionAreaListener;

public class SelectionAreaEffect implements ISelectionAreaListener {

	
	private final Rectangle selectionRectangle;
	private final Group selectionLayerParent;
	
	public SelectionAreaEffect(final Group selectionLayerParent, final Display display) {
		final org.eclipse.swt.graphics.Color swtColor = display.getSystemColor(SWT.COLOR_LIST_SELECTION);
		final Color cFill = new Color((swtColor.getRed()/255d), (swtColor.getGreen()/255d), (swtColor.getBlue()/255d), 0.3);
		final Color cStroke = new Color((swtColor.getRed()/255d), (swtColor.getGreen()/255d), (swtColor.getBlue()/255d), 1);
		
		this.selectionLayerParent = selectionLayerParent;
		this.selectionRectangle = new Rectangle(1, 1);
		this.selectionRectangle.setFill(cFill);
		this.selectionRectangle.setStroke(cStroke);
	}


	@Override
	public void onSelectionArea(ESelectionAreaEvent event, double x, double y, double width, double height, boolean toggle) {
		switch(event) {
		case init:
			selectionLayerParent.getChildren().add(selectionRectangle);
		case update:
			selectionRectangle.setX(x);
			selectionRectangle.setY(y);
			selectionRectangle.setWidth(width);
			selectionRectangle.setHeight(height);
			break;
		case apply:
		case cancel:
			selectionLayerParent.getChildren().remove(selectionRectangle);
		}
	}
}
