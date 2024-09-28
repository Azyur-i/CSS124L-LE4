package com.groupfour;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;


public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        
        //Main 
        HBox root = new HBox();
        scene = new Scene(root, 680, 480);
        stage.setScene(scene);
        stage.show();

        //UI Elements

        Label label = new Label("Hello, World");

        //Add UI elements
        root.getChildren().add(label);
    }

    public static void main(String[] args) {
        launch();
    }

}