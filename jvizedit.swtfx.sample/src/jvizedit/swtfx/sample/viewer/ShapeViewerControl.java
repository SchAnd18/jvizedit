package jvizedit.swtfx.sample.viewer;

import org.eclipse.swt.SWT;import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseWheelListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import jvizedit.control.DragDiagram;
import jvizedit.control.DragSelection;
import jvizedit.control.OpenContextMenu;
import jvizedit.control.Zoom;
import jvizedit.control.core.ControlStateMachine;
import jvizedit.control.selection.ISelectableFinder;
import jvizedit.control.selection.SelectOnClick;
import jvizedit.control.selection.SelectionAreaOnDrag;
import jvizedit.control.selection.ViewerSelection;
import jvizedit.control.selection.ViewerSelectionUpdater;
import jvizedit.mvc.content.ContentManager;
import jvizedit.mvc.content.core.IContentManager;
import jvizedit.swtfx.SelectableControllerFinderImpl;
import jvizedit.swtfx.SelectionAreaEffect;
import jvizedit.swtfx.events.FxKeyEvent;
import jvizedit.swtfx.events.FxMouseEvent;
import jvizedit.swtfx.events.SwtMouseWheelEvent;
import jvizedit.swtfx.sample.control.DragShapeObjects;
import jvizedit.swtfx.sample.model.ShapesRoot;
import jvizedit.swtfx.sample.mvc.ShapeModelControllerFactory;

public class ShapeViewerControl {

	private final ViewerSelection viewerSelection;
	private final IContentManager modelContent;
	private final FXCanvas fxCanvas;
	
	
	public ShapeViewerControl(final Composite parent) {
		
		this.fxCanvas = new FXCanvas(parent, SWT.NONE);
		
		
		
		final FxRootControl fxRoot = new FxRootControl();
		final Scene scene = fxRoot.getScene();
		
		this.fxCanvas.setScene(scene);
		
		{ //model content
			this.modelContent = new ContentManager();
			this.modelContent.addContentChangeListener(fxRoot);
			
			this.modelContent.addControllerFactory(new ShapeModelControllerFactory());
		}
		
		{ //control state machine
			final ControlStateMachine cstm = new ControlStateMachine();
			
			FxMouseEvent.addMouseEventFilter(scene, cstm);
			FxKeyEvent.addKeyEventFilter(scene, cstm);
			
			parent.getDisplay().addFilter(SWT.MouseWheel, new Listener() {
				
				@Override
				public void handleEvent(Event event) {
					final SwtMouseWheelEvent wheel = new SwtMouseWheelEvent(event);
					cstm.handleEvent(wheel);
				}
			});
			
			
			final OpenContextMenu openContextMenu = new OpenContextMenu(cstm);
			openContextMenu.addOpenContextMenuListener(new ContextMenu(fxCanvas));
			
			new DragDiagram(cstm, openContextMenu, fxRoot);
			
			new Zoom(cstm, fxRoot);
			
			final ISelectableFinder selectableFinder = new SelectableControllerFinderImpl(modelContent);
			final SelectOnClick selectOnClick = new SelectOnClick(cstm, selectableFinder);
			
			this.viewerSelection = new ViewerSelection(modelContent);
			final ViewerSelectionUpdater selectionUpdater = new ViewerSelectionUpdater(viewerSelection, selectableFinder);
			selectOnClick.addSelectOnClickListener(selectionUpdater);
			
			final DragSelection dragSelection = new DragSelection(cstm, selectOnClick, selectableFinder);
			
			final SelectionAreaOnDrag selectionAreaOnDrag = new SelectionAreaOnDrag(cstm, selectOnClick);
			final SelectionAreaEffect selecationAreaEffect = new SelectionAreaEffect(fxRoot.getFeedbackLayer(), parent.getDisplay());
			selectionAreaOnDrag.addSelectionAreaListener(selecationAreaEffect);
			selectionAreaOnDrag.addSelectionAreaListener(selectionUpdater);
			
			//apply drag behavior to shape objects
			final DragShapeObjects dragShapeObjects = new DragShapeObjects(viewerSelection, modelContent);
			dragSelection.addDragDropListener(dragShapeObjects);
		}
	}

	public void refresh() {
		this.modelContent.performRefresh();
	}
	
	public void setInput(ShapesRoot shapeModel) {
		modelContent.setRoot(shapeModel);
		modelContent.performRefresh();
	}
	
}
