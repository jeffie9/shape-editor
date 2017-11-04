package com.example.shapeeditor;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class RectangleTool extends AbstractTool {
    private Rectangle shape;
    private Point2D anchor;

    public RectangleTool(ShapeEditor controller) {
        super(controller);
    }

    @Override
    public void onMouseClicked(MouseEvent event) {
        
    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        shape.setWidth(Math.abs(event.getX() - anchor.getX()));

        if (anchor.getX() < event.getX()) {
            shape.setX(anchor.getX());
        } else {
            shape.setX(event.getX());
        }

        shape.setHeight(Math.abs(event.getY() - anchor.getY()));
        if (anchor.getY() < event.getY()) {
            shape.setY(anchor.getY());
        } else {
            shape.setY(event.getY());
        }
    }

    @Override
    public void onMousePressed(MouseEvent event) {
        anchor = new Point2D(event.getX(), event.getY());
        shape = new Rectangle(
                event.getX(), event.getY(),
                0, 0
                );
        shape.setStrokeWidth(3.0);
        shape.setStroke(Color.BLACK);
        controller.userInputCanvas.getChildren().clear();
        controller.userInputCanvas.getChildren().add(shape);
    }

    @Override
    public void onMouseMoved(MouseEvent event) {
        
    }

    @Override
    public void onMouseReleased(MouseEvent event) {
        controller.activeLayer.getChildren().add(shape);
        shape = null;
    }

}
