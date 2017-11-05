package com.example.shapeeditor;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class EllipseTool extends AbstractTool {
    private Ellipse shape;
    private Point2D anchor;

    public EllipseTool(ShapeEditor controller) {
        super(controller);
    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        double minX, maxX;
        if (anchor.getX() < event.getX()) {
            minX = anchor.getX();
            maxX = event.getX();
        } else {
            minX = event.getX();
            maxX = anchor.getX();
        }
        double minY, maxY;
        if (anchor.getY() < event.getY()) {
            minY = anchor.getY();
            maxY = event.getY();
        } else {
            minY = event.getY();
            maxY = anchor.getY();
        }
        double cx = (minX + maxX) / 2.0;
        double cy = (minY + maxY) / 2.0;
        double rx = cx - minX;
        double ry = cy - minY;
        shape.setCenterX(cx);
        shape.setCenterY(cy);
        shape.setRadiusX(rx);
        shape.setRadiusY(ry);
    }

    @Override
    public void onMousePressed(MouseEvent event) {
        anchor = new Point2D(event.getX(), event.getY());
        shape = new Ellipse(
                event.getX(), event.getY(),
                0, 0
                );
        shape.setStrokeWidth(3.0);
        shape.setStroke(Color.BLACK);
        shape.setFill(Color.TRANSPARENT);
        controller.userInputCanvas.getChildren().clear();
        controller.userInputCanvas.getChildren().add(shape);
    }

    @Override
    public void onMouseReleased(MouseEvent event) {
        controller.activeLayer.getChildren().add(shape);
        shape = null;
    }

}
