package com.example.shapeeditor;

import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ShapeEditor extends Application {
    @FXML
    AnchorPane editorPanel;
    @FXML
    Pane userInputCanvas;
    @FXML
    Label leftStatusLabel;
    @FXML
    Label rightStatusLabel;

    Pane activeLayer;
    AbstractTool activeTool;
    Map<String, AbstractTool> tools;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ShapeEditor.fxml"));
        Pane pane = loader.load();
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Shape Editor");
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @FXML
    public void initialize() {
        System.out.println("initialize");
        activeLayer = new Pane();
        editorPanel.getChildren().add(activeLayer);
        userInputCanvas.toFront();
        tools = new HashMap<>();
        tools.put("Line", new LineTool(this));
        tools.put("Rectangle", new RectangleTool(this));
        tools.put("Ellipse", new EllipseTool(this));
        tools.put("Poly Line", new PolyLineTool(this));
        tools.put("Polygon", new PolygonTool(this));
        tools.put("Arc", new ArcTool(this));
        activeTool = tools.get("Line");
    }

    public void onPickTool(ActionEvent event) {
        Labeled source = Labeled.class.cast(event.getSource());
        activeTool = tools.get(source.getText());
    }

    public void onEditorMouseClicked(MouseEvent event) {
        activeTool.onMouseClicked(event);
    }
    public void onEditorMouseDragged(MouseEvent event) {
        activeTool.onMouseDragged(event);
    }
    public void onEditorMousePressed(MouseEvent event) {
        activeTool.onMousePressed(event);
    }
    public void onEditorMouseMoved(MouseEvent event) {
        activeTool.onMouseMoved(event);
    }
    public void onEditorMouseReleased(MouseEvent event) {
        activeTool.onMouseReleased(event);
    }
    public void onEditorDragDetected(MouseEvent event) {
        System.out.println(event);
        //userInputCanvas.startFullDrag();
    }
    public void onEditorMouseDragReleased(MouseDragEvent event) {
        System.out.println(event);
    }

}
