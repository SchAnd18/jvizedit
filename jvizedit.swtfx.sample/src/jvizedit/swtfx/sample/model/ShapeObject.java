package jvizedit.swtfx.sample.model;

import java.util.Objects;

public class ShapeObject extends AbstractObervableObject {

	private ShapeType shapeType = ShapeType.Rectangle;
	private double x;
	private double y;
	private double width;
	private double height;
	private String text;
	private String color;

	public double getHeight() {
		return this.height;
	}

	public void setHeight(final double height) {
		this.height = height;
		super.notifyChange();
	}

	public double getWidth() {
		return this.width;
	}

	public void setWidth(final double width) {
		this.width = width;
		super.notifyChange();
	}

	public double getX() {
		return this.x;
	}

	public void setX(final double x) {
		this.x = x;
		super.notifyChange();
	}

	public double getY() {
		return this.y;
	}

	public void setY(final double y) {
		this.y = y;
		super.notifyChange();
	}

	public ShapeType getShapeType() {
		return this.shapeType;
	}

	public void setShapeType(final ShapeType shapeType) {
		this.shapeType = Objects.requireNonNull(shapeType);
		super.notifyChange();
	}

	public String getText() {
		return this.text;
	}

	public void setText(final String text) {
		this.text = text;
		super.notifyChange();
	}

	public String getColor() {
		return this.color;
	}

	public void setColor(final String color) {
		this.color = color;
		super.notifyChange();
	}
}
