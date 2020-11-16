package jvizedit.swtfx.sample.mvc;

import java.util.Collection;
import java.util.List;

import javafx.scene.Group;
import javafx.scene.Node;
import jvizedit.mvc.IController;
import jvizedit.mvc.content.core.IContentHandler;
import jvizedit.mvc.content.core.IContentManager;
import jvizedit.mvc.content.core.UnorderedContentHandler;
import jvizedit.swtfx.sample.model.ShapesRoot;

public class ShapesRootController extends AbstractObservingController<ShapesRoot> {

	private final UnorderedContentHandler<IController> contentHandler = new UnorderedContentHandler<>(this);
	private boolean isDisposed = false;

	private final Group group;

	public ShapesRootController(final IContentManager contentManager, final IController parent,
			final ShapesRoot shapesRoot) {
		super(contentManager, parent, shapesRoot);

		this.group = new Group();
		this.group.setUserData(shapesRoot);

	}

	@Override
	public Collection<?> getModelChildren() {
		return getModel().getShapeObjects();
	}

	@Override
	public void updateView() {
		final List<Node> nodes = this.contentHandler.getContentControllsAs(Node.class);
		this.group.getChildren().setAll(nodes);

		/*
		 * Alternative: final UnorderedContentUpdate<IController> update =
		 * contentHandler.getLastUpdate(); if(update != null) { final Collection<Node>
		 * addedElements = update.getAddedUiElementsAsType(Node.class);
		 * group.getChildren().addAll(addedElements);
		 *
		 * final Collection<Node> removedElements =
		 * update.getRemovedUiElementsAsType(Node.class);
		 * group.getChildren().removeAll(removedElements); }
		 */
	}

	@Override
	public IContentHandler<IController> getContentHandler() {
		return this.contentHandler;
	}

	@Override
	public boolean isDisposed() {
		return this.isDisposed;
	}

	@Override
	public void dispose() {
		this.isDisposed = true;
	}

	@Override
	public Object getView() {
		return this.group;
	}

}
