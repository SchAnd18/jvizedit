package jvizedit.mvc.content.core;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import jvizedit.mvc.IControllerBase;
import jvizedit.mvc.content.IContentUpdateContext;

public interface IContentHandler<U extends IControllerBase> {

	IContentUpdate<U> getLastUpdate();

	void updateContent(IContentUpdateContext<U> updateContext);

	Collection<U> getContent();

	default <T> List<T> getContentControllsAs(final Class<T> expectedControlType) {
		return getContent().stream().map(IControllerBase::getView).map(v -> {
			if (!expectedControlType.isInstance(v)) {
				throw new RuntimeException("Unexpected content type " + v + ". Expected object of type "
						+ expectedControlType.getName() + ".");
			}
			return expectedControlType.cast(v);
		}).collect(Collectors.toList());
	}

	U getContent(Object model);
}
