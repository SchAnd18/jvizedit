package jvizedit.swtfx.sample.control;

import java.util.List;

import javafx.geometry.Point2D;
import jvizedit.control.dragdrop.EDragDropTransfer;
import jvizedit.control.dragdrop.IDragDropListener;
import jvizedit.control.dragdrop.IDragEventInfo;
import jvizedit.control.selection.ViewerSelection;
import jvizedit.mvc.content.core.IContentManager;
import jvizedit.swtfx.sample.model.ShapeObject;
import jvizedit.swtfx.sample.mvc.ShapeObjectController;

public class DragShapeObjects implements IDragDropListener {

	private final IContentManager contentManager;
	private final ViewerSelection viewerSelection;

	private List<ShapeObjectController> draggedObjects;
	private double startX;
	private double startY;

	public DragShapeObjects(final ViewerSelection viewerSelection, final IContentManager contentManager) {
		this.viewerSelection = viewerSelection;
		this.contentManager = contentManager;
	}

	@Override
	public void dragEvent(final IDragEventInfo info) {
		final double x = info.getX();
		final double y = info.getY();
		switch (info.getType()) {
		case startDrag:
			/*
			 * Remember start position of drag and the elements that shall be dragged
			 */
			this.startX = x;
			this.startY = y;
			this.draggedObjects = this.viewerSelection.getSelectedControllers(ShapeObjectController.class);
			break;
		case continueDrag:
			/*
			 * Calculate drag-delta and apply set it as drag - review. This makes the
			 * figures move during drag operation.
			 */
			this.draggedObjects.forEach(o -> {
				final Point2D p = o.getView().sceneToLocal(x, y).subtract(o.getView().sceneToLocal(this.startX, this.startY));
				o.getDragPreviewTranslate().setX(p.getX());
				o.getDragPreviewTranslate().setY(p.getY());
			});

			// elements are expected to move with pseudo drag and drop. This is the only
			// kind of transfer we allow.
			info.setAcceptedTransfers(EDragDropTransfer.pseudo);
			break;
		case drop:
			/*
			 * Perform the drop. Set the new coordinates for all dragged elements. Then call
			 * performRefersh() In a real world example there would be some commands called
			 * here. The refresh should be just triggerd after a command call.
			 */
			this.draggedObjects.forEach(o -> {
				final Point2D p = o.getView().sceneToLocal(x, y).subtract(o.getView().sceneToLocal(this.startX, this.startY));
				final ShapeObject m = o.getModel();
				m.setX(m.getX() + p.getX());
				m.setY(m.getY() + p.getY());
			});

			this.contentManager.performRefresh();
			// there is no break ...
		case abortDrag:
			// ... because in abort and drop we need to clear the preview
			this.draggedObjects.stream().map(ShapeObjectController::getDragPreviewTranslate).forEach(t -> {
				t.setX(0);
				t.setY(0);
			});
			this.draggedObjects = null;
			break;
		}
	}

}
