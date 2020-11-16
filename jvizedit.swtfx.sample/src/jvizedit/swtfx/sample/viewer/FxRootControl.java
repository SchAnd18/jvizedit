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
		this.root.getTransforms().addAll(this.translate, this.scale);
		this.scene = new Scene(this.root);
		this.root.getChildren().add(this.feedbackLayer);
	}

	public Scene getScene() {
		return this.scene;
	}

	public Group getFeedbackLayer() {
		return this.feedbackLayer;
	}

	@Override
	public void setOffset(final double x, final double y) {
		this.translate.setX(x);
		this.translate.setY(y);
	}

	@Override
	public double getOffsetX() {
		return this.translate.getX();
	}

	@Override
	public double getOffsetY() {
		return this.translate.getY();
	}

	@Override
	public void setScale(final double scale) {
		this.scale.setX(scale);
		this.scale.setY(scale);
	}

	@Override
	public double getScale() {
		return this.scale.getX();
	}

	@Override
	public void onRootChange(final IController oldRootController, final IController newRootController) {
		if (this.currentRootControllerNode != null) {
			this.root.getChildren().remove(this.currentRootControllerNode);
		}
		this.currentRootControllerNode = newRootController.getViewAsType(Node.class);
		if (this.currentRootControllerNode != null) {
			this.root.getChildren().add(0, this.currentRootControllerNode);
		}
	}

}
