package jvizedit.swtfx.sample.model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractObervableObject {

	private final List<IObjectChangeListener> changeListeners = new ArrayList<>();
	
	public void addListener(IObjectChangeListener listener) {
		changeListeners.add(listener);
	}
	
	public void removeListener(IObjectChangeListener listener) {
		changeListeners.remove(listener);
	}
	
	public void notifyChange() {
		changeListeners.forEach(c->c.objectChanged(this));
	}
	
}
