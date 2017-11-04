package com.example.shapeeditor;

import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class LineTool extends AbstractTool {
    private Line shape;

    public LineTool(ShapeEditor controller) {
        super(controller);
    }

    @Override
    public void onMouseClicked(MouseEvent event) {
        
    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        shape.setEndX(event.getX());
        shape.setEndY(event.getY());
    }

    @Override
    public void onMousePressed(MouseEvent event) {
        shape = new Line(
                event.getX(), event.getY(),
                event.getX(), event.getY()
                );
        shape.setStrokeWidth(5.0);
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
