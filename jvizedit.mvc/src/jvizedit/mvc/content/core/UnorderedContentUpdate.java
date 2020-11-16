package jvizedit.mvc.content.core;

import java.util.Collection;

import jvizedit.mvc.IControllerBase;

public class UnorderedContentUpdate<U extends IControllerBase> implements IContentUpdate<U> {

	private final Collection<? extends U> added;
	private final Collection<? extends U> removed;

	public UnorderedContentUpdate(final Collection<? extends U> added, final Collection<? extends U> removed) {
		this.added = added;
		this.removed = removed;
	}

	@Override
	public Collection<? extends U> getAdded() {
		return this.added;
	}

	@Override
	public Collection<? extends U> getRemoved() {
		return this.removed;
	}

}
