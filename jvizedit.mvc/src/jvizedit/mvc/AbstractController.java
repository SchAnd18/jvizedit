package jvizedit.mvc;

public abstract class AbstractController implements IController {

	private IController parent;
//	private final Set<IEdgeController> connectedEdges = new HashSet<>();

	public AbstractController(final IController parent) {
		this.parent = parent;
	}
	
//	@Override
//	public void addConnectedEdge(IEdgeController edge) {
//		connectedEdges.add(edge);
//	}
//
//	@Override
//	public void removeConnectedEdge(IEdgeController edge) {
//		connectedEdges.remove(edge);
//	}
//
//	@Override
//	public Collection<IEdgeController> getConnectedEdges() {
//		return Collections.unmodifiableSet(connectedEdges);
//	}

	@Override
	public IController getParent() {
		return parent;
	}
	
	@Override
	public void relocateToNewParent(IController newParent) {
		this.parent = newParent;
	}


}
