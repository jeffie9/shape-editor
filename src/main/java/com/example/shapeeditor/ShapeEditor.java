package com.example.shapeeditor;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class ShapeEditor extends Application {
    @FXML
    AnchorPane editorPanel;
    @FXML
    Pane userInputCanvas;
    Pane activeLayer;
    AbstractTool activeTool;

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
        activeTool = new RectangleTool(this);
    }

    public void onPickTool(ActionEvent event) {
        System.out.println(event);
        Button source = Button.class.cast(event.getSource());
        System.out.println(source.getText());
        switch (source.getText()) {
        default:
            break;
        }
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

}
