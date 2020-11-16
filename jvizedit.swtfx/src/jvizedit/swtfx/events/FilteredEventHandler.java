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
	public void handle(final T event) {
		for (final Function<T, Boolean> filter : this.filters) {
			if (!filter.apply(event)) {
				return;
			}
		}
		this.wrapped.handle(event);
	}

	public void addFilter(final Function<T, Boolean> filter) {
		this.filters.add(filter);
	}

	public void removeFilter(final Function<T, Boolean> filter) {
		this.filters.remove(filter);
	}
}
