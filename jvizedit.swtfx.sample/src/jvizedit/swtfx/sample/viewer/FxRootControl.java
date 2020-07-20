package jvizedit.swtfx.sample.viewer;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import jvizedit.control.INavigableArea;
import jvizedit.mvc.IController;
import jvizedit.mvc.content.IContentChangeListener;

public class FxRootControl implements IContentChangeListener, INavigableArea {

	private final Scale scale = new Scale();
	private final Translate translate = new Translate();
	private final Group root = new Group();
	private final Group feedbackLayer = new Group();
	private final Scene scene;
	
	private Node currentRootControllerNode;
	
	public FxRootControl() {
		this.root.getTransforms().addAll(translate,scale);
		this.scene = new Scene(this.root);
		this.root.getChildren().add(feedbackLayer);
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public Group getFeedbackLayer() {
		return feedbackLayer;
	}
	
	@Override
	public void setOffset(double x, double y) {
		translate.setX(x);
		translate.setY(y);
	}

	@Override
	public double getOffsetX() {
		return translate.getX();
	}

	@Override
	public double getOffsetY() {
		return translate.getY();
	}

	@Override
	public void setScale(double scale) {
		this.scale.setX(scale);
		this.scale.setY(scale);
	}

	@Override
	public double getScale() {
		return this.scale.getX();
	}
	
	@Override
	public void onRootChange(IController oldRootController, IController newRootController) {
		if(currentRootControllerNode != null) {
			this.root.getChildren().remove(currentRootControllerNode);
		}
		currentRootControllerNode = newRootController.getViewAsType(Node.class);
		if(currentRootControllerNode != null) {
			this.root.getChildren().add(0,currentRootControllerNode);
		}
	}

}
