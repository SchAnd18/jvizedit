package jvizedit.swtfx.sample.control;

import java.util.Set;

import javafx.geometry.Point2D;
import jvizedit.control.DragSelection.EDragDropEventType;
import jvizedit.control.DragSelection.IDragDropListener;
import jvizedit.control.selection.ISelectableController;
import jvizedit.control.selection.ViewerSelection;
import jvizedit.mvc.content.core.IContentManager;
import jvizedit.swtfx.sample.model.ShapeObject;
import jvizedit.swtfx.sample.mvc.ShapeObjectController;

public class DragShapeObjects implements IDragDropListener {

	private final IContentManager contentManager;
	private final ViewerSelection viewerSelection;
	
	private Set<ShapeObjectController> draggedObjects;
	private double startX;
	private double startY;
	
	public DragShapeObjects(final ViewerSelection viewerSelection, final IContentManager contentManager) {
		this.viewerSelection = viewerSelection;
		this.contentManager = contentManager;
	}
	
	@Override
	public void dragEvent(EDragDropEventType type, double x, double y, ISelectableController dragSource) {
		switch(type) {
		case startDrag:
			/*
			 * Remember start position of drag and the elements that shall be dragged
			 */
			startX = x;
			startY = y;
			draggedObjects = viewerSelection.getSelectedControllers(ShapeObjectController.class);
			break;
		case continueDrag:
			/*
			 * Calculate drag-delta and apply set it as drag - review. This makes the figures move during drag operation.  
			 */
			draggedObjects.forEach(o-> {
				final Point2D p = o.getView().sceneToLocal(x, y).subtract(o.getView().sceneToLocal(startX, startY));
				o.getDragPreviewTranslate().setX(p.getX());
				o.getDragPreviewTranslate().setY(p.getY());
			});
			break;
		case drop:
			/*
			 * Perform the drop. Set the new coordinates for all dragged elements.
			 * Then call performRefersh() 
			 * In a real world example there would be some commands called here. The refresh should be just triggerd after a command call. 
			 */
			draggedObjects.forEach(o-> {
				final Point2D p = o.getView().sceneToLocal(x, y).subtract(o.getView().sceneToLocal(startX, startY));
				final ShapeObject m = o.getModel();
				m.setX(m.getX() + p.getX());
				m.setY(m.getY() + p.getY());
			});

			contentManager.performRefresh();
			//there is no break ...
		case abortDrag:
			// ... because in abort and drop we need to clear the preview
			draggedObjects.stream().map(ShapeObjectController::getDragPreviewTranslate).forEach(t-> {
				t.setX(0);
				t.setY(0);
			});
			draggedObjects = null;
			break;
		}
	}

}
