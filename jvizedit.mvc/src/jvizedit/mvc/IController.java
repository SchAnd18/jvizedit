package jvizedit.mvc;

import java.util.Collection;
import java.util.Collections;

import jvizedit.mvc.content.core.IContentHandler;

public interface IController extends IControllerBase {

	Collection<?> getModelChildren();

	void updateView();

	IContentHandler<IController> getContentHandler();

	IController getParent();

	void relocateToNewParent(IController newParent);

	default Collection<IController> getControllerChildren() {
		final IContentHandler<IController> ch = getContentHandler();
		if (ch == null) {
			return Collections.emptyList();
		}
		return ch.getContent();
	}

	default boolean hasControllerChildren() {
		final IContentHandler<IController> ch = getContentHandler();
		return ch != null && ch.getContent().isEmpty() == false;
	}

}
