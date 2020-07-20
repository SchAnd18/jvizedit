package jvizedit.mvc.content.core;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import jvizedit.mvc.IController;
import jvizedit.mvc.IControllerBase;
import jvizedit.mvc.content.IContentUpdateContext;

public class UnorderedContentHandler<T extends IControllerBase> implements IContentHandler<T> {

	private final Map<Object,T> contentMap = new HashMap<>();
	private final IController controller;
	private UnorderedContentUpdate<T> lastUpdate;
	
	public UnorderedContentHandler(final IController controller) {
		this.controller = controller;
	}
	
	@Override
	public void updateContent(IContentUpdateContext<T> updateContext) {
		final Collection<?> modelChildren = updateContext.getModelChildren(controller);
		
		final Map<Object,T> added = new HashMap<>();
		final Map<Object,T> removed = new HashMap<>(contentMap);
		
		for(Object modelChild: modelChildren) {
			T controller = removed.remove(modelChild);
			if(controller == null) {
				controller = updateContext.getController(modelChild, this.controller);
				added.put(modelChild, controller);
			}
		}
		
		contentMap.keySet().removeAll(removed.keySet());
		contentMap.putAll(added);
		
		lastUpdate = new UnorderedContentUpdate<T>(added.values(), removed.values());
	}

	@Override
	public Collection<T> getContent() {
		return Collections.unmodifiableCollection(contentMap.values());
	}

	@Override
	public T getContent(Object model) {
		return contentMap.get(model);
	}

	@Override
	public UnorderedContentUpdate<T> getLastUpdate() {
		return lastUpdate;
	}
	
}
