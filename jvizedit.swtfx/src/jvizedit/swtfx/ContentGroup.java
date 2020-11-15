package jvizedit.swtfx;

import javafx.scene.Group;
import javafx.scene.Node;
import jvizedit.mvc.IController;
import jvizedit.mvc.content.IContentChangeListener;

public class ContentGroup extends Group implements IContentChangeListener {

	@Override
	public void onRootChange(IController oldRootController, IController newRootController) {
		if (newRootController != null) {
			final Object view = newRootController.getView();
			if (!(view instanceof Node)) {
				throw new IllegalStateException("Expected an JavaFx node");
			}
			final Node node = (Node) view;
			this.getChildren().setAll(node);
		} else {
			this.getChildren().clear();
		}
	};
}
