package jvizedit.swtfx.sample.graph.mvc;

import java.util.Collection;
import java.util.Collections;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.util.EContentAdapter;

import javafx.scene.Group;
import javafx.scene.Node;
import jvizedit.mvc.IController;
import jvizedit.mvc.content.core.IContentManager;
import jvizedit.swtfx.sample.graph.gml.Connections;
import jvizedit.swtfx.sample.graph.gml.GraphModelRoot;
import jvizedit.swtfx.sample.graph.gml.Nodes;

public class GraphModelRootController extends AbstractEMFController implements IGraphNodeController {

	private final Group nodes = new Group();
	private final Group connections = new Group();
	private final Group group = new Group(nodes, connections);
	
	public GraphModelRootController(IContentManager contentManager, IController parent, GraphModelRoot model) {
		super(contentManager,parent,model);
	}

	@Override
	public Collection<?> getModelChildren() {
		if(getModel().getNodes() != null) {
			return getModel().getNodes().getNodes();
		}
		return Collections.emptyList();
	}

	@Override
	public void updateView() {
		final Collection<Node> removed = getConentHandler().getLastUpdate().getRemovedUiElementsAsType(Node.class);
		final Collection<Node> added = getConentHandler().getLastUpdate().getAddedUiElementsAsType(Node.class);
		nodes.getChildren().removeAll(removed);
		nodes.getChildren().addAll(added);
	}

	@Override
	public Group getView() {
		return group;
	}
	
	@Override
	public GraphModelRoot getModel() {
		return (GraphModelRoot)super.getModel();
	}

	@Override
	protected Adapter createEMFInvalidationAdapter() {
		return new EContentAdapter() {
			public void notifyChanged(org.eclipse.emf.common.notify.Notification msg) {
				invalidate();
				super.notifyChanged(msg);
			};
			
			@Override
			protected void addAdapter(Notifier notifier) {
				final boolean correctType = notifier instanceof GraphModelRoot || notifier instanceof Nodes || notifier instanceof Connections;
				if(correctType) {					
					super.addAdapter(notifier);
				}
			}
		};
	}

	@Override
	public Group getConnectionContainer() {
		return connections;
	}
}
