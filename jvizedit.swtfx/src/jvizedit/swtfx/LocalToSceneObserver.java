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

	public LocalToSceneObserver(final Node observedNode) {
		this.il1 = (a, b, c) -> {
			update();
		};
		this.il2 = (observable, oldValue, newvalue) -> {
			update();
		};
		this.observedNode = observedNode;
		enable();
	}

	public void enable() {
		if (this.enabled) {
			return;
		}
		this.enabled = true;
		this.observedNode.localToSceneTransformProperty().addListener(this.il2);
		this.observedNode.getLocalToSceneTransform().onTransformChangedProperty().addListener(this.il1);
	}

	public void disable() {
		if (!this.enabled) {
			return;
		}
		this.enabled = false;
		this.observedNode.localToSceneTransformProperty().removeListener(this.il2);
		this.observedNode.getLocalToSceneTransform().onTransformChangedProperty().removeListener(this.il1);
	}

	public boolean isEnabled() {
		return this.enabled;
	}

	public Node getObservedNode() {
		return this.observedNode;
	}

	public void update() {
	}
}
