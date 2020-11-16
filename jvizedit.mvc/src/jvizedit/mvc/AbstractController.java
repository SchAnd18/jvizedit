package jvizedit.mvc;

public abstract class AbstractController implements IController {

	private IController parent;

	public AbstractController(final IController parent) {
		this.parent = parent;
	}

	@Override
	public IController getParent() {
		return this.parent;
	}

	@Override
	public void relocateToNewParent(final IController newParent) {
		this.parent = newParent;
	}

}
