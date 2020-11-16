package jvizedit.swtfx;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;

import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.NonInvertibleTransformException;
import jvizedit.control.selection.SelectionAreaOnDrag.ESelectionAreaEvent;
import jvizedit.control.selection.SelectionAreaOnDrag.ISelectionAreaListener;

public class SelectionAreaEffect implements ISelectionAreaListener {

	private final Rectangle selectionRectangle;
	private final Group selectionLayerParent;
	private boolean translateBorderSize = true;

	public SelectionAreaEffect(final Group selectionLayerParent, final Display display) {
		final org.eclipse.swt.graphics.Color swtColor = display.getSystemColor(SWT.COLOR_LIST_SELECTION);
		final Color cFill = new Color((swtColor.getRed() / 255d), (swtColor.getGreen() / 255d), (swtColor.getBlue() / 255d), 0.3);
		final Color cStroke = new Color((swtColor.getRed() / 255d), (swtColor.getGreen() / 255d), (swtColor.getBlue() / 255d), 1);

		this.selectionLayerParent = selectionLayerParent;
		this.selectionRectangle = new Rectangle(1, 1);
		this.selectionRectangle.setFill(cFill);
		this.selectionRectangle.setStroke(cStroke);
	}

	@Override
	public void onSelectionArea(final ESelectionAreaEvent event, final double x, final double y, final double width, final double height,
			final boolean toggle) {
		switch (event) {
		case init:
			this.selectionLayerParent.getChildren().add(this.selectionRectangle);
		case update:
			final Bounds b = this.selectionLayerParent.sceneToLocal(new BoundingBox(x, y, width, height));
			this.selectionRectangle.setX(b.getMinX());
			this.selectionRectangle.setY(b.getMinY());
			this.selectionRectangle.setWidth(b.getWidth());
			this.selectionRectangle.setHeight(b.getHeight());

			if (this.translateBorderSize) {
				try {
					final double w = this.selectionLayerParent.getLocalToSceneTransform().inverseDeltaTransform(1, 1).getX();
					this.selectionRectangle.setStrokeWidth(w);
				} catch (final NonInvertibleTransformException e) {
					// this is a minor problem that just should be logged
					this.selectionRectangle.setStrokeWidth(1);
					this.translateBorderSize = false;
					e.printStackTrace();
				}
			}

			break;
		case apply:
		case cancel:
			this.selectionLayerParent.getChildren().remove(this.selectionRectangle);
		}
	}
}
