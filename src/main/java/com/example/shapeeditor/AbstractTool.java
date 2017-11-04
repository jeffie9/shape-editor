package com.example.shapeeditor;

import javafx.scene.input.MouseEvent;

public abstract class AbstractTool {
    protected final ShapeEditor controller;

    protected AbstractTool(ShapeEditor controller) {
        this.controller = controller;
    }

    public abstract void onMouseClicked(MouseEvent event);
    public abstract void onMouseDragged(MouseEvent event);
    public abstract void onMousePressed(MouseEvent event);
    public abstract void onMouseMoved(MouseEvent event);
    public abstract void onMouseReleased(MouseEvent event);
}
