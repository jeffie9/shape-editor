package com.example.shapeeditor;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;

public class PolyLineTool extends AbstractTool {
    private Polyline shape = null;
    private Line curLine = null;

    public PolyLineTool(ShapeEditor controller) {
        super(controller);
    }

    @Override
    public void onMouseClicked(MouseEvent event) {
        if (event.getClickCount() > 1) {
            if (shape != null) {
                controller.activeLayer.getChildren().add(shape);
                controller.userInputCanvas.getChildren().clear();
                shape = null;
                curLine = null;
            }
        } else {
            if (shape == null) {
                shape = new Polyline();
                shape.setStrokeWidth(5.0);
                shape.setStroke(Color.BLACK);
                curLine = new Line(event.getX(), event.getY(),
                        event.getX(), event.getY());
                curLine.setStrokeWidth(5.0);
                curLine.setStroke(Color.BLACK);
                controller.userInputCanvas.getChildren().clear();
                controller.userInputCanvas.getChildren().add(shape);
                controller.userInputCanvas.getChildren().add(curLine);
            }
            shape.getPoints().addAll(event.getX(), event.getY());
            curLine.setStartX(event.getX());
            curLine.setStartY(event.getY());
        }
    }

    @Override
    public void onMouseMoved(MouseEvent event) {
        if (curLine != null) {
            curLine.setEndX(event.getX());
            curLine.setEndY(event.getY());
        }
    }

}
