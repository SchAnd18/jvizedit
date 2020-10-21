package jvizedit.swtfx.sample.graph.mvc;

import java.util.Objects;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.Region;
import javafx.scene.shape.Line;
import jvizedit.mvc.IController;
import jvizedit.mvc.IEdgeController;
import jvizedit.mvc.MVCUtil;
import jvizedit.mvc.content.core.IContentManager;
import jvizedit.swtfx.fxuitl.FXUtil;
import jvizedit.swtfx.sample.graph.gml.Connection;

public class ConnectionController extends AbstractEMFControllerBase implements IEdgeController {

	private final Line line = new Line();
	private IController sourceController;
	private IController targetController;
	private IGraphNodeController parent;
	
	public ConnectionController(IContentManager contentManager, Connection model) {
		super(contentManager, model);
	}
	
	@Override
	public void dispose() {
		removeLine();
		
		sourceController = null;
		targetController = null;
		parent = null;
		
		super.dispose();
	}

	private void removeLine() {
		line.startXProperty().unbind();
		line.startYProperty().unbind();
		line.endXProperty().unbind();
		line.endYProperty().unbind();
		if(parent != null) {
			parent.getConnectionContainer().getChildren().remove(line);
		}
	}

	@Override
	public Object getView() {
		return line;
	}

	@Override
	public IController getConnectedSourceController() {
		return sourceController;
	}

	@Override
	public IController getConnectedTargetController() {
		return targetController;
	}

	@Override
	public void updateView(IController sourceController, IController targetController) {
		final IGraphNodeController parent = MVCUtil.findCommonParentOfType(sourceController, targetController, IGraphNodeController.class).orElseThrow(() -> new IllegalStateException("No common parent found!"));
		boolean parentUpdate = false;
		if(!Objects.equals(this.parent, parent)) {
			removeLine();
			this.parent = parent;
			this.parent.getConnectionContainer().getChildren().add(line);
			parentUpdate = true;
		}
		
		if(!Objects.equals(this.sourceController, sourceController) || parentUpdate) {
			this.sourceController = sourceController;
			final Region srcRegion = sourceController.getViewAsType(Region.class);
			
			FXUtil.createBoundsToLocalBinding(line, srcRegion, b -> {
				final DoubleBinding centerX = Bindings.createDoubleBinding(()-> {return b.get().getMinX() + b.get().getWidth() / 2;}, b);
				final DoubleBinding centerY = Bindings.createDoubleBinding(()-> {return b.get().getMinY() + b.get().getHeight() / 2;}, b);
				line.startXProperty().bind(centerX);
				line.startYProperty().bind(centerY);
			});
			
		}
		if(!Objects.equals(this.targetController,  targetController) || parentUpdate) {
			this.targetController = targetController;
			final Region targetRegion = targetController.getViewAsType(Region.class);
			
			FXUtil.createBoundsToLocalBinding(line, targetRegion, b -> {
				final DoubleBinding centerX = Bindings.createDoubleBinding(()-> {return b.get().getMinX() + b.get().getWidth() / 2;}, b);
				final DoubleBinding centerY = Bindings.createDoubleBinding(()-> {return b.get().getMinY() + b.get().getHeight() / 2;}, b);
				line.endXProperty().bind(centerX);
				line.endYProperty().bind(centerY);
			});
		}
	}
	
	@Override
	public Connection getModel() {
		return (Connection)super.getModel();
	}

	@Override
	public Object getSourceNode() {
		return getModel().getSrc();
	}

	@Override
	public Object getTargetNode() {
		return getModel().getTarget();
	}



}
