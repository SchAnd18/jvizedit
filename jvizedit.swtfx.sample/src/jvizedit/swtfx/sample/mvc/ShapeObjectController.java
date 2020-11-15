package jvizedit.swtfx.sample.mvc;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Translate;
import jvizedit.control.selection.ISelectableController;
import jvizedit.mvc.IController;
import jvizedit.mvc.content.core.IContentHandler;
import jvizedit.mvc.content.core.IContentManager;
import jvizedit.swtfx.sample.model.ShapeObject;
import jvizedit.swtfx.sample.model.ShapeType;

public class ShapeObjectController extends AbstractObservingController<ShapeObject> implements ISelectableController {

	private boolean isDisposed = false;

	private final Translate dragPreviewTranslate = new Translate();

	private final StackPane view;
	private final Label textLabel;
	private Shape currentShape;

	public ShapeObjectController(IContentManager contentManager, IController parent, ShapeObject shapeObject) {
		super(contentManager, parent, shapeObject);

		// setup ui
		this.view = new StackPane();
		this.view.getTransforms().add(dragPreviewTranslate);

		this.textLabel = new Label();
		this.textLabel.setAlignment(Pos.CENTER);
		this.view.getChildren().add(textLabel);

		/*
		 * Set controller always as user data for root element. This is used to find the
		 * controller from ui side.
		 */
		this.view.setUserData(this);
	}

	public Translate getDragPreviewTranslate() {
		return dragPreviewTranslate;
	}

	@Override
	public Collection<?> getModelChildren() {
		return Collections.emptyList();
	}

	@Override
	public void updateView() {
		if (getModel().getShapeType() == ShapeType.Oval) {
			final Ellipse ellipse;
			if (currentShape instanceof Ellipse) {
				ellipse = (Ellipse) currentShape;
			} else {
				ellipse = new Ellipse();
				this.view.getChildren().remove(currentShape);
				currentShape = ellipse;
				this.view.getChildren().add(0, currentShape);
			}
			updateEllipse(ellipse);
		} else if (getModel().getShapeType() == ShapeType.Rectangle) {
			final Rectangle rect;
			if (currentShape instanceof Rectangle) {
				rect = (Rectangle) currentShape;
			} else {
				rect = new Rectangle();
				this.view.getChildren().remove(currentShape);
				currentShape = rect;
				this.view.getChildren().add(0, currentShape);
			}
			updateRectangle(rect);
		} else {
			throw new RuntimeException("Unexpected shape type: " + getModel().getShapeType());
		}

		this.view.setLayoutX(getModel().getX());
		this.view.setLayoutY(getModel().getY());

		final String text = Optional.ofNullable(getModel().getText()).orElse("");
		this.textLabel.setText(text);

	}

	private Color getColor() {
		return Optional.ofNullable(getModel().getColor()).map(Color::valueOf).orElse(Color.AQUA);
	}

	private void updateEllipse(Ellipse shape) {
		shape.setRadiusX(getModel().getWidth() / 2);
		shape.setRadiusY(getModel().getHeight() / 2);
		shape.setFill(getColor());
	}

	private void updateRectangle(Rectangle rectangle) {
		rectangle.setWidth(getModel().getWidth());
		rectangle.setHeight(getModel().getHeight());
		rectangle.setFill(getColor());
	}

	@Override
	public IContentHandler<IController> getConentHandler() {
		return null;
	}

	@Override
	public boolean isDisposed() {
		return isDisposed;
	}

	@Override
	public void dispose() {
		this.isDisposed = true;
	}

	@Override
	public StackPane getView() {
		return view;
	}

	@Override
	public void setSelectionStatus(boolean isSelected) {
		// TODO: Replace this by a selection border effect in feedback layer
		if (isSelected) {
			final Border selBorder = new Border(
					new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
			this.view.setBorder(selBorder);
		} else {
			this.view.setBorder(null);
		}

	}
}
