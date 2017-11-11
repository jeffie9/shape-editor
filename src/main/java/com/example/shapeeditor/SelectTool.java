package com.example.shapeeditor;


import java.util.HashSet;
import java.util.Set;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Transform;

public class SelectTool extends AbstractTool {
    private Set<Node> selectedShapes = new HashSet<>();

    enum Action {
        NONE,
        SELECT,
        BOX_SELECT,
        TRANSLATE,
        ROTATE,
        SCALE
    }
    private Action action = Action.NONE;
    private Point2D anchor;

    public SelectTool(ShapeEditor controller) {
        super(controller);
    }

    @Override
    public void onMouseClicked(MouseEvent event) {
    }

    @Override
    public void onMousePressed(MouseEvent event) {
        Node pickedNode = controller.activeLayer.getChildren().stream()
                .filter(n -> contains(n, event.getX(), event.getY()))
                .findFirst()
                .orElse(null);
        boolean pickedNodeIsSelected = pickedNode != null
                && !selectedShapes.isEmpty()
                && selectedShapes.contains(pickedNode);
        if (pickedNodeIsSelected) {
            // fixin to drag shape or shapes
            anchor = new Point2D(event.getX(), event.getY());
            action = Action.TRANSLATE;
        } else if (pickedNode != null) {
            System.out.println(pickedNode);
            Bounds b = pickedNode.getBoundsInLocal();
            Rectangle r = new Rectangle(b.getMinX(), b.getMinY(), b.getWidth(), b.getHeight());
            r.setFill(Color.TRANSPARENT);
            r.setStroke(Color.ORANGE);
            r.setStrokeWidth(1.0);
            r.getStrokeDashArray().add(5.0);
            r.getTransforms().addAll(pickedNode.getTransforms());
            if (!event.isShiftDown()) {
                controller.userInputCanvas.getChildren().clear();
                selectedShapes.clear();
            }
            selectedShapes.add(pickedNode);
            controller.userInputCanvas.getChildren().add(r);
            action = Action.SELECT;
        } else {
            if (!event.isShiftDown()) {
                controller.userInputCanvas.getChildren().clear();
                selectedShapes.clear();
            }
            anchor = new Point2D(event.getX(), event.getY());
            action = Action.BOX_SELECT;
        }
    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        if (action == Action.TRANSLATE) {
            double dx = event.getX() - anchor.getX();
            double dy = event.getY() - anchor.getY();
            selectedShapes.forEach(n -> {
                n.setTranslateX(dx);
                n.setTranslateY(dy);
            });
            controller.userInputCanvas.getChildren().forEach(n -> {
                n.setTranslateX(dx);
                n.setTranslateY(dy);
            });
        }
    }

    @Override
    public void onMouseReleased(MouseEvent event) {
        if (action == Action.TRANSLATE) {
            double dx = event.getX() - anchor.getX();
            double dy = event.getY() - anchor.getY();
            Transform transform = Transform.translate(dx, dy);
            selectedShapes.forEach(n -> {
                n.setTranslateX(0);
                n.setTranslateY(0);
                n.getTransforms().add(transform);
            });
        }
    }

    private boolean contains(Node node, double x, double y) {
        Point2D pt = new Point2D(x, y);
        for (Transform t : node.getTransforms()) {
            try {
                pt = t.inverseTransform(pt);
            } catch (NonInvertibleTransformException ignore) {}
        }
        return node.contains(pt.getX(), pt.getY());
    }
}
