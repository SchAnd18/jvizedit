package jvizedit.swtfx.events;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import javafx.event.Event;
import javafx.event.EventHandler;

public class FilteredEventHandler<T extends Event> implements EventHandler<T> {

	private final EventHandler<T> wrapped;
	private final List<Function<T, Boolean>> filters = new ArrayList<>();

	public FilteredEventHandler(final EventHandler<T> wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public void handle(T event) {
		for (Function<T, Boolean> filter : this.filters) {
			if (!filter.apply(event)) {
				return;
			}
		}
		wrapped.handle(event);
	}

	public void addFilter(Function<T, Boolean> filter) {
		this.filters.add(filter);
	}

	public void removeFilter(Function<T, Boolean> filter) {
		this.filters.remove(filter);
	}
}
