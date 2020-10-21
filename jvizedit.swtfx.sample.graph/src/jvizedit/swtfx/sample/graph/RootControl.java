package jvizedit.swtfx.sample.graph;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import jvizedit.control.INavigableArea;
import jvizedit.mvc.IController;
import jvizedit.mvc.content.IContentChangeListener;

public class RootControl implements IContentChangeListener, INavigableArea {

	final Translate offset = new Translate();
	final Scale scale = new Scale();
	final Group group;
	
	public RootControl() {
		this.group = new Group();
		this.group.getTransforms().addAll(offset,scale);
	}
	
	public Group getGroup() {
		return group;
	}
	
	@Override
	public void setOffset(double x, double y) {
		offset.setX(x);
		offset.setY(y);
	}

	@Override
	public double getOffsetX() {
		return offset.getX();
	}

	@Override
	public double getOffsetY() {
		return offset.getY();
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
		group.getChildren().clear();
		if(newRootController != null) {			
			group.getChildren().add(newRootController.getViewAsType(Node.class));
		}
	}

}
