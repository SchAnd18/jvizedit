package jvizedit.swtfx.sample.graph.mvc;

import java.util.Collection;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import jvizedit.mvc.IController;
import jvizedit.mvc.content.core.IContentManager;
import jvizedit.swtfx.sample.graph.gml.GraphNode;

public class GraphNodeController extends AbstractEMFController implements IGraphNodeController {

	//private final Translate position = new Translate();
	private final Pane group = new Pane();
	private final Rectangle rectangle = new Rectangle();
	private Pane children = new Pane();
	private Group connectionContainer = new Group();
	
	private final DoubleProperty width = new SimpleDoubleProperty();
	private final DoubleProperty height = new SimpleDoubleProperty();
	
	public GraphNodeController(IContentManager contentManager, IController parent, GraphNode model) {
		super(contentManager, parent, model);
		
		group.getChildren().addAll(rectangle,children, connectionContainer);
		
		final DoubleBinding childAreaWidth = new DoubleBinding() {
			
			{
				bind(children.boundsInLocalProperty());
			}
			
			@Override
			protected double computeValue() {
				return children.getBoundsInLocal().getWidth();
			}
		};
		
		final DoubleBinding childAreaHeight = new DoubleBinding() {
			
			{
				bind(children.boundsInLocalProperty());
			}
			
			@Override
			protected double computeValue() {
				return children.getBoundsInLocal().getHeight();
			}
		};
		
		rectangle.widthProperty().bind(Bindings.max(width, childAreaWidth));
		rectangle.heightProperty().bind(Bindings.max(height, childAreaHeight));
		
		rectangle.setFill(Color.rgb(random255(), random255(), random255()));
	}
	
	@Override
	public Group getConnectionContainer() {
		return connectionContainer;
	}
	
	private int random255() {
		return (int)( Math.random() * 255);
	}

	@Override
	public GraphNode getModel() {
		return (GraphNode)super.getModel();
	}
	
	@Override
	public Collection<?> getModelChildren() {
		return getModel().getChildren();
	}

	@Override
	public void updateView() {
		//position.setX(getModel().getX());
		//position.setY(getModel().getY());
		group.setLayoutX(getModel().getX());
		group.setLayoutY(getModel().getY());
		
		final Collection<Node> removed = getConentHandler().getLastUpdate().getRemovedUiElementsAsType(Node.class);
		final Collection<Node> added = getConentHandler().getLastUpdate().getAddedUiElementsAsType(Node.class);
		children.getChildren().removeAll(removed);
		children.getChildren().addAll(added);
		
		this.width.set(getModel().getWidth());
		this.height.set(getModel().getHeight());
	}

	@Override
	public Pane getView() {
		return group;
	}


}
