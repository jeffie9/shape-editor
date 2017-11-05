package com.example.shapeeditor;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class PolygonTool extends AbstractTool {
    private Polygon shape = null;

    public PolygonTool(ShapeEditor controller) {
        super(controller);
    }

    @Override
    public void onMouseClicked(MouseEvent event) {
        if (event.getClickCount() > 1) {
            if (shape != null) {
                controller.activeLayer.getChildren().add(shape);
                controller.userInputCanvas.getChildren().clear();
                shape = null;
            }
        } else {
            if (shape == null) {
                shape = new Polygon(event.getX(), event.getY());
                shape.setStrokeWidth(5.0);
                shape.setStroke(Color.BLACK);
                controller.userInputCanvas.getChildren().clear();
                controller.userInputCanvas.getChildren().add(shape);
            }
            shape.getPoints().addAll(event.getX(), event.getY());
        }
    }

    @Override
    public void onMouseMoved(MouseEvent event) {
        // update last two points
        if (shape != null && shape.getPoints().size() > 3) {
            int last = shape.getPoints().size() - 1;
            shape.getPoints().set(last - 1, event.getX());
            shape.getPoints().set(last, event.getY());
        }
    }

}
