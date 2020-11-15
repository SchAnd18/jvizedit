package jvizedit.mvc;

public interface IEdgeController extends IControllerBase {

	IController getConnectedSourceController();

	IController getConnectedTargetController();

	void updateView(IController sourceController, IController targetController);

	Object getSourceNode();

	Object getTargetNode();

}
