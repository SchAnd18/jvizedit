package jvizedit.swtfx.sample.graph;

import java.util.Collection;
import java.util.Collections;
import java.util.EventObject;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.common.command.CommandStackListener;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IPartListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import jvizedit.swtfx.sample.graph.gml.GraphModelRoot;
import jvizedit.swtfx.sample.graph.gml.presentation.GmlEditor;

public class GraphView extends ViewPart implements IPartListener, CommandStackListener {

	private DiagramViewer diagramViewer;
	private GmlEditor currentEditorContext;
	private EditingDomain currentEditingDomain;
	
	@Override
	public void createPartControl(Composite parent) {
		diagramViewer = new DiagramViewer(parent);
		
		getSite().getPage().addPartListener(this);
	}

	@Override
	public void setFocus() {
	}

	private void setEditorContext(GmlEditor editor) {
		
		if(currentEditingDomain != null) {
			currentEditingDomain.getCommandStack().removeCommandStackListener(this);
		}
		this.currentEditingDomain = null;
		this.currentEditorContext = editor;
		if(editor == null) {
			diagramViewer.getModelContent().setRoot(null);
			diagramViewer.getModelContent().setModelEdgeSupplier(null);
		} else {			
			final Optional<GraphModelRoot> root = editor.getEditingDomain().getResourceSet().getResources().stream() // 
					.map(Resource::getContents) //
					.flatMap(List::stream) //
					.filter(GraphModelRoot.class::isInstance) //
					.map(GraphModelRoot.class::cast) //
					.findFirst();
			
			diagramViewer.getModelContent().setRoot(root.orElse(null));
			
			diagramViewer.getModelContent().setModelEdgeSupplier(() -> {
				return root.<Collection<?>>map(r-> {
					if(r.getConnections() != null) {
						return r.getConnections().getConnections();
					} else {
						return Collections.emptyList();
					}
				}).orElse(Collections.emptyList());
			});
			
			currentEditingDomain = editor.getEditingDomain();
			currentEditingDomain.getCommandStack().addCommandStackListener(this);
		}
		this.diagramViewer.getModelContent().performRefresh();
	}
	
	//////////////////////////////////////////////////////
	///////////////// IPartListener //////////////////////
	
	@Override
	public void partActivated(IWorkbenchPart part) {
		if(part.equals(currentEditorContext)) {
			return;
		}
		if(part instanceof GmlEditor) {
			final GmlEditor editor = (GmlEditor)part;
			this.setEditorContext(editor);
		}
	}

	@Override
	public void partBroughtToTop(IWorkbenchPart part) {
	}

	@Override
	public void partClosed(IWorkbenchPart part) {
		if(part.equals(currentEditorContext)) {
			setEditorContext(null);
		}
	}

	@Override
	public void partDeactivated(IWorkbenchPart part) {
	}

	@Override
	public void partOpened(IWorkbenchPart part) {
	}

	///////////////////////////////////////////////////////
	//////////////////// CommandStackListener /////////////
	
	@Override
	public void commandStackChanged(EventObject event) {
		this.diagramViewer.getModelContent().performRefresh();
	}
}
