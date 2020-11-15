package jvizedit.swtfx;

import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.transform.Transform;
import javafx.scene.transform.TransformChangedEvent;

public class LocalToSceneObserver {
	private final ChangeListener<? super EventHandler<? super TransformChangedEvent>> il1;
	private final ChangeListener<Transform> il2;
	private final Node observedNode;
	private boolean enabled = false;

	public LocalToSceneObserver(Node observedNode) {
		il1 = (a, b, c) -> {
			update();
		};
		il2 = (observable, oldValue, newvalue) -> {
			update();
		};
		this.observedNode = observedNode;
		enable();
	}

	public void enable() {
		if (enabled) {
			return;
		}
		enabled = true;
		observedNode.localToSceneTransformProperty().addListener(il2);
		observedNode.getLocalToSceneTransform().onTransformChangedProperty().addListener(il1);
	}

	public void disable() {
		if (!enabled) {
			return;
		}
		enabled = false;
		observedNode.localToSceneTransformProperty().removeListener(il2);
		observedNode.getLocalToSceneTransform().onTransformChangedProperty().removeListener(il1);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public Node getObservedNode() {
		return observedNode;
	}

	public void update() {
	}
}
