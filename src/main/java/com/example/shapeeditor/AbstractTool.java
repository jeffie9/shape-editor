package com.example.shapeeditor;

import javafx.scene.input.MouseEvent;

public abstract class AbstractTool {
    protected final ShapeEditor controller;

    protected AbstractTool(ShapeEditor controller) {
        this.controller = controller;
    }

    public void onMouseClicked(MouseEvent event) {};
    public void onMouseDragged(MouseEvent event) {};
    public void onMousePressed(MouseEvent event) {};
    public void onMouseMoved(MouseEvent event) {};
    public void onMouseReleased(MouseEvent event) {};
}
