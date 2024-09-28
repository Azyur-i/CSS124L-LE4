package com.groupfour;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

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
        VBox gamesVBox = new VBox();
        VBox appsVBox = new VBox();
        VBox moviesVBox = new VBox();
        Label label = new Label("PLACEHOLDER");

        label.getStyleClass().add("placeholder-label");
        mainVBox.getStyleClass().add("mainVBox");

        Button gameBtn = new Button("Games");
        Button appBtn = new Button("Apps");
        Button movieBtn = new Button("Movies");

        //Button triggers
        gameBtn.setOnAction(e -> {
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(gamesVBox);
        });

        appBtn.setOnAction(e -> {
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(appsVBox);
        });

        movieBtn.setOnAction(e -> {
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(moviesVBox);
        });

        //Add Json components
        JsonNode node = new ObjectMapper().readTree(getClass().getResourceAsStream("appData.json"));
        if (node.isArray()) {
            int count = 0;
            for (JsonNode element : node) {
                String title = element.get("title").asText();
                String dev = element.get("dev").asText();
                double starRating = element.get("star_rating").asDouble();
                String downloads = element.get("downloads").asText();

                Label titleLabel = new Label(title);
                Label devLabel = new Label(dev);
                Label rateLabel = new Label(String.valueOf(starRating));
                Label dlLabel = new Label(downloads);

                if (count < 4) {
                    gamesVBox.getChildren().addAll(titleLabel, devLabel, rateLabel, dlLabel);
                } else if (count < 8) {
                    appsVBox.getChildren().addAll(titleLabel, devLabel, rateLabel, dlLabel);
                } else {
                    moviesVBox.getChildren().addAll(titleLabel, devLabel, rateLabel, dlLabel);
                }
                count++;
            }
        } else {
            System.out.println("Error: JSON data is not an array");
        }
        
        //Add UI elements
        sideBar.getChildren().addAll(label, gameBtn, appBtn, movieBtn);
        root.getChildren().addAll(sideBar, mainVBox);

        mainVBox.getChildren().add(gamesVBox);
        
        
    }

    public static void main(String[] args) {
        launch();
    }

}