package com.groupfour;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.groupfour.JsonReader;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.File;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        


        //Main 
        HBox root = new HBox();
        scene = new Scene(root, 1280, 720);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

        //UI Elements
        VBox sideBar = new VBox();
        VBox mainVBox = new VBox();
        VBox firstCtnBox = new VBox();
        Label label = new Label("PLACEHOLDER");

        label.getStyleClass().add("placeholder-label");
        mainVBox.getStyleClass().add("mainVBox");

        Button gameBtn = new Button("Games");
        Button appBtn = new Button("Apps");
        Button movieBtn = new Button("Movies");

        JsonNode node = new ObjectMapper().readTree(getClass().getResourceAsStream("appData.json"));
        if (node.isArray()) {
            for (JsonNode element : node) {
                String title = element.get("title").asText();
                String dev = element.get("dev").asText();
                double starRating = element.get("star_rating").asDouble();
                String downloads = element.get("downloads").asText();

                Label gameLabel = new Label(title);
                Label devLabel = new Label(dev);
                Label rateLabel = new Label(String.valueOf(starRating));
                Label dlLabel = new Label(downloads);
                firstCtnBox.getChildren().addAll(gameLabel, devLabel, rateLabel, dlLabel);

            }
        } else {
            System.out.println("Error: JSON data is not an array");
        }
        
        gameBtn.getStyleClass().add("button");
        appBtn.getStyleClass().add("button");
        movieBtn.getStyleClass().add("button");
        
        //Add UI elements
        sideBar.getChildren().addAll(label, gameBtn, appBtn, movieBtn);
        root.getChildren().addAll(sideBar, mainVBox);

        mainVBox.getChildren().addAll(firstCtnBox);
        
        
    }

    public static void main(String[] args) {
        launch();
    }

}