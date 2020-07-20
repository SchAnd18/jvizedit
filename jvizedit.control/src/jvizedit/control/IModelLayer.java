package jvizedit.control;

// TODO: rename this not "INavigateable" or something
// offset is independent of scale!
public interface IModelLayer {

	void setOffset(double x, double y);
	
	double getOffsetX();
	
	double getOffsetY();
	
	void setScale(double scale);
	
	double getScale();
	
}
