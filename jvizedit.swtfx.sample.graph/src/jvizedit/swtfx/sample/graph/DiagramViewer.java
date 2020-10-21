package jvizedit.swtfx.sample.graph;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import jvizedit.control.DragDiagram;
import jvizedit.control.OpenContextMenu;
import jvizedit.control.core.ControlStateMachine;
import jvizedit.control.selection.ISelectableFinder;
import jvizedit.control.selection.SelectOnClick;
import jvizedit.control.selection.ViewerSelection;
import jvizedit.control.selection.ViewerSelectionUpdater;
import jvizedit.mvc.content.ContentManager;
import jvizedit.mvc.content.core.IContentManager;
import jvizedit.swtfx.SelectableControllerFinderImpl;
import jvizedit.swtfx.events.FxKeyEvent;
import jvizedit.swtfx.events.FxMouseEvent;
import jvizedit.swtfx.sample.graph.mvc.GraphControllerFactory;

public class DiagramViewer {

	private final ViewerSelection viewerSelection;
	private final IContentManager modelContent;
	private final FXCanvas fxCanvas;
	
	public DiagramViewer(Composite parent) {
		
		this.fxCanvas = new FXCanvas(parent, SWT.NONE);
		
		final RootControl modelLayer = new RootControl();
		final Scene scene = new Scene(modelLayer.getGroup());
		
		this.fxCanvas.setScene(scene);
		
		{ //model layer
			this.modelContent = new ContentManager();
			this.modelContent.addContentChangeListener(modelLayer);
			
			this.modelContent.addControllerFactory(new GraphControllerFactory());
		}
		
		{ //control state machine
			final ControlStateMachine cstm = new ControlStateMachine();
			
			FxMouseEvent.addMouseEventFilter(scene, cstm);
			FxKeyEvent.addKeyEventFilter(scene, cstm);
			
			final OpenContextMenu openContextMenu = new OpenContextMenu(cstm);
			new DragDiagram(cstm, openContextMenu, modelLayer);
			
			
			final ISelectableFinder selectableFinder = new SelectableControllerFinderImpl(modelContent);
			final SelectOnClick selectOnClick = new SelectOnClick(cstm, selectableFinder);
			
			this.viewerSelection = new ViewerSelection(modelContent);
			final ViewerSelectionUpdater selectionUpdater = new ViewerSelectionUpdater(viewerSelection, selectableFinder);
			selectOnClick.addSelectOnClickListener(selectionUpdater);
			
			//final DragSelection dragSelection = new DragSelection(cstm, selectOnClick, selectableFinder);
			
		}
		
	}
	
	public IContentManager getModelContent() {
		return modelContent;
	}

}
