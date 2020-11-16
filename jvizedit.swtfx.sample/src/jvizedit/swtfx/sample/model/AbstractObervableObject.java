package jvizedit.swtfx.sample.model;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractObervableObject {

	private final List<IObjectChangeListener> changeListeners = new ArrayList<>();

	public void addListener(final IObjectChangeListener listener) {
		this.changeListeners.add(listener);
	}

	public void removeListener(final IObjectChangeListener listener) {
		this.changeListeners.remove(listener);
	}

	public void notifyChange() {
		this.changeListeners.forEach(c -> c.objectChanged(this));
	}

}
