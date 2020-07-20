package jvizedit.mvc.content.core;

import java.util.Collection;

import jvizedit.mvc.IControllerBase;
import jvizedit.mvc.content.IContentUpdateContext;

public interface IContentHandler<U extends IControllerBase> {
	
	IContentUpdate<U> getLastUpdate();
	
	void updateContent(IContentUpdateContext<U> updateContext);
	
	Collection<U> getContent();
	
	U getContent(Object model);
}
