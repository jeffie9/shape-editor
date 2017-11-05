package com.example.shapeeditor;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.transform.Transform;

public class ArcTool extends AbstractTool {
    private Arc shape = null;
    private Point2D center = null;
    private Line guide1 = null;
    private double guideAngle;

    enum ToolState {
        NO_SHAPE,
        ONE_ANCHOR,
        TWO_ANCHORS
    }

    private ToolState toolState = ToolState.NO_SHAPE;

    public ArcTool(ShapeEditor controller) {
        super(controller);
    }

    @Override
    public void onMouseDragged(MouseEvent event) {
        if (toolState == ToolState.ONE_ANCHOR) {
            guide1.setEndX(event.getX());
            guide1.setEndY(event.getY());
        }
    }

    @Override
    public void onMousePressed(MouseEvent event) {
        System.out.println("arc mouse pressed");
        if (toolState == ToolState.NO_SHAPE) {
            guide1 = new Line(event.getX(), event.getY(),
                        event.getX(), event.getY());
            guide1.setStrokeWidth(1.0);
            guide1.getStrokeDashArray().add(5.0);
            guide1.setStroke(Color.BLACK);
            shape = null;
            center = null;
            controller.userInputCanvas.getChildren().clear();
            controller.userInputCanvas.getChildren().add(guide1);
            toolState = ToolState.ONE_ANCHOR;
        }
    }

    @Override
    public void onMouseMoved(MouseEvent event) {
        if (toolState == ToolState.TWO_ANCHORS) {
            if (shape != null) {
                shape.setRadiusY(distanceToLine(event.getX(), event.getY(), guide1));
            }
            if (center != null) {
                double dx = event.getX() - center.getX(), dy = event.getY() - center.getY();
                double angle = Math.toDegrees(Math.atan2(dy, dx));
                controller.leftStatusLabel.setText(String.format("%.2f", angle - guideAngle));
                if (angle - guideAngle < 0) {
                    shape.setLength(180.0);
                } else {
                    shape.setLength(-180.0);
                }
            }
        }
    }

    @Override
    public void onMouseReleased(MouseEvent event) {
        System.out.println("arc mouse released");
        if (toolState == ToolState.ONE_ANCHOR) {
            // put guide line into preferred configuration
            if (guide1.getStartX() > guide1.getEndX()
                    || (guide1.getStartX() > guide1.getEndX() 
                            && guide1.getStartY() > guide1.getEndY())) {
                Point2D p = new Point2D(guide1.getEndX(), guide1.getEndY());
                guide1.setEndX(guide1.getStartX());
                guide1.setEndY(guide1.getStartY());
                guide1.setStartX(p.getX());
                guide1.setStartY(p.getY());
            }
            double dx = guide1.getEndX() - guide1.getStartX(), dy = guide1.getEndY() - guide1.getStartY();
            guideAngle = Math.toDegrees(Math.atan2(dy, dx));
            controller.rightStatusLabel.setText(String.format("%.2f", guideAngle));

            center = new Point2D(guide1.getStartX(), guide1.getStartY()).midpoint(guide1.getEndX(), guide1.getEndY());
            Circle circle = new Circle(center.getX(), center.getY(), 2.0);
            controller.userInputCanvas.getChildren().add(circle);
            shape = new Arc();
            shape.setCenterX(center.getX());
            shape.setCenterY(center.getY());
            shape.setRadiusX(center.distance(guide1.getStartX(), guide1.getStartY()));
            shape.setRadiusY(0);
            shape.setType(ArcType.OPEN);
            shape.setStrokeWidth(2.0);
            shape.setStroke(Color.BLACK);
            shape.setFill(Color.TRANSPARENT);
            shape.setStartAngle(0);
            shape.setLength(180.0);
            shape.getTransforms().add(Transform.rotate(guideAngle, center.getX(), center.getY()));
            controller.userInputCanvas.getChildren().add(shape);
            // TODO set guide2 as the line from the mouse to top of arc
            toolState = ToolState.TWO_ANCHORS;
        } else if (toolState == ToolState.TWO_ANCHORS) {
            controller.userInputCanvas.getChildren().clear();
            controller.activeLayer.getChildren().add(shape);
            shape = null;
            guide1 = null;
            toolState = ToolState.NO_SHAPE;
        }
    }

    public double distanceToLine(double x, double y, Line line) {
        // https://en.wikipedia.org/wiki/Distance_from_a_point_to_a_line
        // distance(x = a + tn, p) = ||(a - p) - ((a - p) dot n)n||
        Point2D n = new Point2D(line.getEndX() - line.getStartX(),
                line.getEndY() - line.getStartY()).normalize();
        Point2D ap = new Point2D(line.getStartX(), line.getStartY()).subtract(x, y);
        return ap.subtract(n.multiply(ap.dotProduct(n))).magnitude();
    }
}
