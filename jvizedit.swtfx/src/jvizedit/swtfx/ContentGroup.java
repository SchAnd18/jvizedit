package jvizedit.swtfx;

import javafx.scene.Group;
import javafx.scene.Node;
import jvizedit.mvc.IController;
import jvizedit.mvc.content.IContentChangeListener;

public class ContentGroup extends Group implements IContentChangeListener {

	@Override
	public void onRootChange(final IController oldRootController, final IController newRootController) {
		if (newRootController != null) {
			final Object view = newRootController.getView();
			if (!(view instanceof Node)) {
				throw new IllegalStateException("Expected an JavaFx node");
			}
			final Node node = (Node) view;
			getChildren().setAll(node);
		} else {
			getChildren().clear();
		}
	}
}
