package jvizedit.control;

/**
 * A graphical area where the user can scroll and zoom.
 */
public interface INavigableArea {

	void setOffset(double x, double y);

	/**
	 * X offset of the content. This should not be affected by scale.
	 */
	double getOffsetX();

	/**
	 * y offset of the content. This should not be affected by scale.
	 */
	double getOffsetY();

	void setScale(double scale);

	/**
	 * scale factor of the areas content. Value of 1.0 means no scale
	 */
	double getScale();

}
