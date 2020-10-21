package jvizedit.swtfx.fxuitl;

import java.util.function.Consumer;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.layout.Region;

public class FXUtil {
	
	public static void createBoundsToLocalBinding(final Node local, final Region regionBounds, Consumer<ObjectBinding<Bounds>> c) {
		final ObjectBinding<Bounds> boundsBinding = createBoundsToLocalBinding(local, regionBounds);
		c.accept(boundsBinding);
	}
	
	public static ObjectBinding<Bounds> createBoundsToLocalBinding(final Node local, final Region regionBounds) {
		final ObjectBinding<Bounds> binding = Bindings.createObjectBinding(() -> {
	        Bounds nodeLocal = regionBounds.getBoundsInLocal();
	        Bounds nodeScene = regionBounds.getLocalToSceneTransform().transform(nodeLocal);
	        Bounds nodePane = local.sceneToLocal(nodeScene);
	        return nodePane ;
	    }, regionBounds.boundsInLocalProperty(), regionBounds.localToSceneTransformProperty(), 
				local.localToSceneTransformProperty());
		return binding;
	}
}
