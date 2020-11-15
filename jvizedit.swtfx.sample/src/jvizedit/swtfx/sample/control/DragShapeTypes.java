package jvizedit.swtfx.sample.control;

import java.util.Random;

import org.eclipse.jface.util.LocalSelectionTransfer;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import jvizedit.control.dragdrop.EDragDropTransfer;
import jvizedit.control.dragdrop.IDragDropListener;
import jvizedit.control.dragdrop.IDragEventInfo;
import jvizedit.mvc.IController;
import jvizedit.mvc.content.core.IContentManager;
import jvizedit.swtfx.sample.model.ShapeObject;
import jvizedit.swtfx.sample.model.ShapeType;
import jvizedit.swtfx.sample.model.ShapesRoot;

public class DragShapeTypes implements IDragDropListener {

	private final IContentManager modelContent;

	public DragShapeTypes(final IContentManager modelContent) {
		this.modelContent = modelContent;
	}

	@Override
	public void dragEvent(IDragEventInfo eventInfo) {
		switch (eventInfo.getType()) {
		case continueDrag:
			eventInfo.setAcceptedTransfers(EDragDropTransfer.move);
			break;
		case drop:
			final ISelection selection = LocalSelectionTransfer.getTransfer().getSelection();
			if (selection.isEmpty() || !(selection instanceof IStructuredSelection)) {
				return;
			}
			final Object first = ((IStructuredSelection) selection).getFirstElement();
			if (!(first instanceof ShapeType)) {
				return;
			}
			final ShapeType shapeType = (ShapeType) first;

			final IController rootController = this.modelContent.getRootController();
			final Node rootNode = rootController.getViewAsType(Node.class);
			final Point2D pos = rootNode.sceneToLocal(eventInfo.getX(), eventInfo.getY());

			final Random random = new Random();
			final ShapeObject shapeObject = new ShapeObject();
			shapeObject.setX(pos.getX());
			shapeObject.setY(pos.getY());
			shapeObject.setWidth(20 + random.nextDouble() * 100);
			shapeObject.setHeight(20 + random.nextDouble() * 100);

			shapeObject.setShapeType(shapeType);

			final String color = "rgb(" + random.nextInt(255) + "," + random.nextInt(255) + "," + random.nextInt(255)
					+ ")";
			shapeObject.setColor(color);

			final ShapesRoot root = (ShapesRoot) rootController.getModel();
			root.addShape(shapeObject);
			this.modelContent.performRefresh();
			break;
		default:
		}
	}

}
