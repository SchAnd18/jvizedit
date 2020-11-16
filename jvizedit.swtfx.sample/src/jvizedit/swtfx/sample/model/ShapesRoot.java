package jvizedit.swtfx.sample.model;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

public class ShapesRoot extends AbstractObervableObject {

	private final Set<ShapeObject> shapeObjects = new LinkedHashSet<>();

	public void addShape(final ShapeObject shapeObject) {
		if (this.shapeObjects.add(shapeObject)) {
			super.notifyChange();
		}
	}

	public void removeShape(final ShapeObject shapeObject) {
		if (this.shapeObjects.remove(shapeObject)) {
			super.notifyChange();
		}
	}

	public Set<ShapeObject> getShapeObjects() {
		return Collections.unmodifiableSet(this.shapeObjects);
	}

}
