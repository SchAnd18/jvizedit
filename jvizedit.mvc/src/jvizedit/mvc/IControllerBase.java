package jvizedit.mvc;

public interface IControllerBase {

	boolean isDisposed();
	
	void dispose();
	
	Object getModel();
	
	Object getView();
	
	default <T> T getViewAsType(Class<T> expectedType) {
		final Object view = getView();
		if(view == null) {
			throw new IllegalStateException("View is not allowed to be null.");
		}
		if(expectedType.isInstance(view)) {
			return expectedType.cast(view);
		} else {
			throw new IllegalStateException("Unexpected view type. Expected Type: " + expectedType.getName() + " Actual Type: " + view.getClass().getName()+ ".");
		}
		
	}
	
}
