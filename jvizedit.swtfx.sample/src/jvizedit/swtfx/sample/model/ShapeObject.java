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
		return height;
	}
	
	public void setHeight(double height) {
		this.height = height;
		super.notifyChange();
	}
	
	public double getWidth() {
		return width;
	}
	
	public void setWidth(double width) {
		this.width = width;
		super.notifyChange();
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
		super.notifyChange();
	}
	
	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		super.notifyChange();
	}
	
	public ShapeType getShapeType() {
		return shapeType;
	}
	
	public void setShapeType(ShapeType shapeType) {
		this.shapeType = Objects.requireNonNull(shapeType);
		super.notifyChange();
	}
	
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
		super.notifyChange();
	}
	
	public String getColor() {
		return color;
	}
	
	public void setColor(String color) {
		this.color = color;
		super.notifyChange();
	}
}
