package jvizedit.mvc.content;

import java.util.Collection;

import jvizedit.mvc.IController;

public interface IContentUpdateContext<T> {

	public T getController(final Object model, final IController parent);

	public Collection<?> getModelChildren(IController controller);
}
