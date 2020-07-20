package jvizedit.mvc;

public interface IEdgeController extends IControllerBase {
	
	IEdgeContainer getParent();
	
	void updateView(IEdgeContainer parent);
	
}
