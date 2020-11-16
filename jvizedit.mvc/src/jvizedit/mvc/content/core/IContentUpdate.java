package jvizedit.mvc.content.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jvizedit.mvc.IControllerBase;

public interface IContentUpdate<U extends IControllerBase> {

	Collection<? extends U> getAdded();

	Collection<? extends U> getRemoved();

	default <T> Collection<T> getAddedUiElementsAsType(final Class<T> expectedType) {
		final Collection<? extends U> added = getAdded();
		final List<T> result = castViewTo(expectedType, added);
		return result;
	}

	default <T> Collection<T> getRemovedUiElementsAsType(final Class<T> expectedType) {
		final Collection<? extends U> removed = getRemoved();
		final List<T> result = castViewTo(expectedType, removed);
		return result;
	}

	public static <T> List<T> castViewTo(final Class<T> expectedType, final Collection<? extends IControllerBase> controllers) {
		final List<T> result = new ArrayList<>(controllers.size());
		for (final IControllerBase o : controllers) {
			final Object view = o.getView();
			if (expectedType.isInstance(view) == false) {
				throw new RuntimeException("Unexpected UI object type " + o);
			}
			final T t = expectedType.cast(view);
			result.add(t);
		}
		return result;
	}
}
