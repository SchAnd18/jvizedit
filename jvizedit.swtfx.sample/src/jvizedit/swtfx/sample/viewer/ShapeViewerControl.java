package jvizedit.swtfx.sample.viewer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;

import javafx.embed.swt.FXCanvas;
import javafx.scene.Scene;
import jvizedit.control.DragDiagram;
import jvizedit.control.OpenContextMenu;
import jvizedit.control.Zoom;
import jvizedit.control.core.ControlStateMachine;
import jvizedit.control.dragdrop.DragExternal;
import jvizedit.control.dragdrop.DragSelection;
import jvizedit.control.selection.ISelectableFinder;
import jvizedit.control.selection.SelectOnClick;
import jvizedit.control.selection.SelectionAreaOnDrag;
import jvizedit.control.selection.ViewerSelection;
import jvizedit.control.selection.ViewerSelectionUpdater;
import jvizedit.mvc.content.ContentManager;
import jvizedit.mvc.content.core.IContentManager;
import jvizedit.swtfx.SelectableControllerFinderImpl;
import jvizedit.swtfx.SelectionAreaEffect;
import jvizedit.swtfx.events.FxDragEvent;
import jvizedit.swtfx.events.FxKeyEvent;
import jvizedit.swtfx.events.FxMouseEvent;
import jvizedit.swtfx.events.SwtMouseWheelEvent;
import jvizedit.swtfx.sample.control.DragShapeObjects;
import jvizedit.swtfx.sample.control.DragShapeTypes;
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

		{ // model content
			this.modelContent = new ContentManager();
			this.modelContent.addContentChangeListener(fxRoot);

			this.modelContent.addControllerFactory(new ShapeModelControllerFactory());
		}

		{ // control state machine
			final ControlStateMachine cstm = new ControlStateMachine();

			FxDragEvent.addDragEventFilter(scene, cstm);
			FxMouseEvent.addMouseEventFilter(scene, cstm);
			FxKeyEvent.addKeyEventFilter(scene, cstm);

			parent.getDisplay().addFilter(SWT.MouseWheel, new Listener() {

				@Override
				public void handleEvent(final Event event) {
					final SwtMouseWheelEvent wheel = new SwtMouseWheelEvent(event);
					cstm.handleEvent(wheel);
				}
			});

			final OpenContextMenu openContextMenu = new OpenContextMenu(cstm);
			openContextMenu.addOpenContextMenuListener(new ContextMenu(this.fxCanvas));

			new DragDiagram(cstm, openContextMenu, fxRoot);

			new Zoom(cstm, fxRoot);

			final ISelectableFinder selectableFinder = new SelectableControllerFinderImpl(this.modelContent);
			final SelectOnClick selectOnClick = new SelectOnClick(cstm, selectableFinder);

			this.viewerSelection = new ViewerSelection(this.modelContent);
			final ViewerSelectionUpdater selectionUpdater = new ViewerSelectionUpdater(this.viewerSelection,
					selectableFinder);
			selectOnClick.addSelectOnClickListener(selectionUpdater);

			// allow pseudo drag and drop of diagram elements
			final DragSelection dragSelection = new DragSelection(cstm, selectOnClick, selectableFinder);

			// handle real drag elements form outside of diagram
			final DragExternal dragExternal = new DragExternal(cstm);

			final SelectionAreaOnDrag selectionAreaOnDrag = new SelectionAreaOnDrag(cstm, selectOnClick);
			final SelectionAreaEffect selecationAreaEffect = new SelectionAreaEffect(fxRoot.getFeedbackLayer(),
					parent.getDisplay());
			selectionAreaOnDrag.addSelectionAreaListener(selecationAreaEffect);
			selectionAreaOnDrag.addSelectionAreaListener(selectionUpdater);

			// apply drag behavior to shape objects
			final DragShapeObjects dragShapeObjects = new DragShapeObjects(this.viewerSelection, this.modelContent);
			dragSelection.addDragDropListener(dragShapeObjects);

			// apply creation of shapes by dragged types
			final DragShapeTypes dragShapeTypes = new DragShapeTypes(this.modelContent);
			dragExternal.addDragDropListener(dragShapeTypes);

		}
	}

	public void refresh() {
		this.modelContent.performRefresh();
	}

	public void setInput(final ShapesRoot shapeModel) {
		this.modelContent.setRoot(shapeModel);
		this.modelContent.performRefresh();
	}

}
