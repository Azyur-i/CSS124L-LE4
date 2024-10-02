package com.groupfour;

import javafx.application.Application;
import javafx.geometry.Insets;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.control.ScrollPane;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    private GridPane selectedPane; // To keep track of the selected pane
    Button returnBtn = new Button("Return");

    @Override
    public void start(Stage stage) throws IOException {
        //Main
        stage.setTitle("G4 App Store");
        HBox root = new HBox();
        scene = new Scene(root, 1280, 720);
        stage.setScene(scene);
        stage.show();

        //UI Elements (Left side)
        VBox sideBar = new VBox();
        ScrollPane scrollPaneSideBar = new ScrollPane();
        scrollPaneSideBar.setContent(sideBar);
        sideBar.setSpacing(15);
        

        //UI Elements (Right section)
        VBox mainVBox = new VBox();


        ScrollPane scrollPaneMainVBox = new ScrollPane();
        scrollPaneMainVBox.setContent(mainVBox);
        scrollPaneMainVBox.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPaneMainVBox.setFitToWidth(true);
        scrollPaneMainVBox.setPrefHeight(720);







        VBox gamesVBox = new VBox();
        gamesVBox.setSpacing(15);

        VBox appsVBox = new VBox();
        appsVBox.setSpacing(15);

        VBox moviesVBox = new VBox();
        moviesVBox.setSpacing(15);

        returnBtn.setOnMouseClicked(e -> {
            mainVBox.getChildren().clear();
            mainVBox.getChildren().add(gamesVBox);
            stage.setScene(scene); // Return to the previous scene
            stage.show();
        });

        //Buttons
        Button gameBtn = new Button("Games");
        Button appBtn = new Button("Apps");
        Button movieBtn = new Button("Movies");
        styleButton(gameBtn);
        styleButton(appBtn);
        styleButton(movieBtn);

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

        //Add JSON components
        JsonNode appNode = new ObjectMapper().readTree(getClass().getResourceAsStream("appData.json"));
        JsonNode gameNode = new ObjectMapper().readTree(getClass().getResourceAsStream("gameData.json"));
        JsonNode movieNode = new ObjectMapper().readTree(getClass().getResourceAsStream("movieData.json"));

        //Populate VBox containers
        populateVbox(appNode, appsVBox, stage);
        populateVbox(gameNode, gamesVBox, stage);
        populateVbox(movieNode, moviesVBox, stage);

        //Add UI elements
        sideBar.getChildren().addAll(gameBtn, appBtn, movieBtn);
        root.getChildren().addAll(sideBar, scrollPaneMainVBox);


        mainVBox.getChildren().add(gamesVBox);

        //Styling
        sideBar.setStyle("-fx-background-color: #F0F0F0; -fx-padding: 15; -fx-pref-width: 200px;");
        scrollPaneMainVBox.setStyle("-fx-background-color: transparent;" );
        mainVBox.setStyle("-fx-padding: 15; -fx-background-color: #FFFFFF; -fx-pref-width: 1000px;");

        //Drop Shadow
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(Color.rgb(0, 0, 0, 0.5));
        dropShadow.setRadius(15);
        mainVBox.setEffect(dropShadow);



    }

    public void populateVbox(JsonNode node, VBox vbox, Stage stage) {
        if (node.isArray()) {
            int count = 0;
            for (JsonNode element : node) {
                String title = element.get("title").asText();
                String dev = element.get("dev").asText();
                double starRating = element.get("star_rating").asDouble();
                String downloads = element.get("downloads").asText();
                String description = element.get("description").asText();

                GridPane infoPane = new GridPane();
                stylePane(infoPane);

                //Main Page Info
                Label titleLabel = new Label(title);
                titleLabel.setFont(new Font("Arial", 24));

                Label devLabel = new Label(dev);
                devLabel.setFont(new Font("Arial", 14));
                devLabel.setTextFill(Color.GRAY);

                Label combinedLabel = new Label("⭐ " + String.valueOf(starRating) + " | " + downloads);
                combinedLabel.setFont(new Font("Arial", 12));
                combinedLabel.setTextFill(Color.GRAY);

                //Copy for Extended Details Page (.add consumes the variable, need two of these- one for sidebar one for details)
                Label titleCopy = new Label(title);
                titleCopy.setFont(new Font("Arial", 24));

                Label devCopy = new Label(dev);
                devCopy.setFont(new Font("Arial", 14));
                devCopy.setTextFill(Color.GRAY);

                Label combinedLabelCopy = new Label("⭐ " + String.valueOf(starRating) + " | " + downloads);
                combinedLabelCopy.setFont(new Font("Arial", 12));
                combinedLabelCopy.setTextFill(Color.GRAY);

                Label descLabel = new Label(description);
                descLabel.setWrapText(true);

                //Contents
                infoPane.add(titleLabel, 0, 1);
                infoPane.add(devLabel, 0, 2);
                infoPane.add(combinedLabel, 0, 3);

                //Click event on info,  opens extended details page
                infoPane.setOnMouseClicked(event -> {
                    if (selectedPane != null) {
                        selectedPane.setStyle("-fx-background-color: #FFFFFF; " +
                                "-fx-border-color: #CCCCCC; " +
                                "-fx-border-width: 1; " +
                                "-fx-text-fill: #333333; " +
                                "-fx-padding: 10 10; " +
                                "-fx-font-size: 12px; " +
                                "-fx-margin-bottom: 15px;" +
                                "-fx-text-overflow: clip; " +
                                "-fx-border-radius: 20px; " +
                                "-fx-background-radius: 20px; " +
                                "-fx-ellipsis-string: '...'; " +
                                "-fx-pref-width: 340px; " +
                                "-fx-wrap-text: false;");
                    }

                    selectedPane = infoPane;
                        selectedPane.setStyle("-fx-border-color: #CCCCCC; " +
                                "-fx-border-width: 1; " +
                                "-fx-text-fill: #333333; " +
                                "-fx-padding: 10 10; " +
                                "-fx-font-size: 12px; " +
                                "-fx-margin-bottom: 15px;" +
                                "-fx-text-overflow: clip; " +
                                "-fx-border-radius: 20px; " +
                                "-fx-background-radius: 20px; " +
                                "-fx-ellipsis-string: '...'; " +
                                "-fx-pref-width: 340px; " +
                                "-fx-wrap-text: false;"
                                );

                    AnchorPane container = new AnchorPane();
                    HBox parentHBox = new HBox();
                    VBox infoVBox = new VBox();



                    

                    //Drop Shadow
                    DropShadow dropShadow = new DropShadow();
                    dropShadow.setColor(Color.rgb(0, 0, 0, 0.2)); // Shadow color
                    dropShadow.setRadius(15);
                    infoVBox.setEffect(dropShadow);
                    infoVBox.setSpacing(7);

                    //Add vbox to scrollpane
                    ScrollPane scrollPane = new ScrollPane();
                    scrollPane.setContent(vbox);
                    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
                    scrollPane.setFitToWidth(true);
                    scrollPane.setPrefHeight(720);

                    
                    returnBtn.setStyle(
                            "-fx-background-color: #FFFFFF; " +
                                    "-fx-border-color: #CCCCCC; " +
                                    "-fx-border-width: 1; " +
                                    "-fx-text-fill: #333333; " +
                                    "-fx-font-size: 16px; " +
                                    "-fx-padding: 10 10; " +
                                    "-fx-border-radius: 10px; " +
                                    "-fx-background-radius: 10px; " +
                                    "-fx-cursor: hand;"
                    );
                    

                    infoVBox.getChildren().addAll(titleCopy, devCopy, combinedLabelCopy, descLabel,returnBtn);


                    parentHBox.getChildren().addAll(scrollPane, infoVBox);
                    parentHBox.setMargin(infoVBox, new Insets(0, 0, 0, 15));
                    parentHBox.setMargin(scrollPane, new Insets(15, 0, 0, 15));

                    container.getChildren().add(parentHBox);
                    Scene infoScene = new Scene(container, 1280, 720);
                    stage.setScene(infoScene);
                    stage.show();

                    returnBtn.setStyle(
                            "-fx-background-color: #FFFFFF; " +
                                    "-fx-border-color: #CCCCCC; " +
                                    "-fx-border-width: 1; " +
                                    "-fx-text-fill: #333333; " +
                                    "-fx-font-size: 16px; " +
                                    "-fx-padding: 10 10; " +
                                    "-fx-border-radius: 10px; " +
                                    "-fx-background-radius: 10px; " +
                                    "-fx-cursor: hand;"
                    );

                    infoVBox.setStyle(
                            "-fx-padding: 15; " +
                            "-fx-background-color: #FFFFFF; " +
                            "-fx-border-color: #CCCCCC; " +
                            "-fx-border-width: 1; " +
                            "-fx-pref-width: 1000;");
                    scrollPane.setStyle("-fx-background-color: transparent;" );
                });

                if (count < 4) {
                    vbox.getChildren().add(infoPane);
                    count = -1;
                }
                count++;
            }
        } else {
            System.out.println("Error: JSON data is not an array");
        }
    }

    //GridPane styling
    private void stylePane(GridPane gridPane) {
        gridPane.setStyle(
                "-fx-padding: 10 ; " +
                "-fx-border-color: #CCCCCC; " +
                "-fx-border-width: 1; " +
                "-fx-background-color: #FAFAFA; " +
                "-fx-border-radius: 20px; " +
                "-fx-background-radius: 20px; " +
                "-fx-pref-width: 340px; " +
                "-fx-margin-bottom: 15px;"
        );
        gridPane.setOnMouseEntered(e -> {
            gridPane.setStyle(
                    "-fx-padding: 10 ; " +
                            "-fx-border-color: #CCCCCC; " +
                            "-fx-border-width: 1; " +
                            "-fx-background-color: #E0E0E0; " +
                            "-fx-border-radius: 20px; " +
                            "-fx-pref-width: 340px; " +
                            "-fx-background-radius: 20px; " +
                            "-fx-margin-bottom: 15px;" +
                            "-fx-cursor: hand;"
            );
        });
        gridPane.setOnMouseExited(e -> {
            gridPane.setStyle(
                    "-fx-padding: 10 ; " +
                            "-fx-border-color: #CCCCCC; " +
                            "-fx-border-width: 1; " +
                            "-fx-background-color: #FAFAFA; " +
                            "-fx-border-radius: 20px; " +
                            "-fx-background-radius: 20px; " +
                            "-fx-pref-width: 340px; " +
                            "-fx-margin-bottom: 15px;"
            );
        });
    }

    //Button styling
    private void styleButton(Button button) {
        button.setStyle(
                "-fx-background-color: #FFFFFF; " +
                        "-fx-border-color: #CCCCCC; " +
                        "-fx-border-width: 1; " +
                        "-fx-text-fill: #333333; " +
                        "-fx-font-size: 24px; " +
                        "-fx-padding: 10 10; " +
                        "-fx-border-radius: 20px; " +
                        "-fx-background-radius: 20px; " +
                        "-fx-cursor: hand; " +
                        "-fx-alignment: center; "
        );

        button.setMaxWidth(Double.MAX_VALUE);

        button.setOnMouseEntered(e -> button.setStyle(
                "-fx-background-color: #E0E0E0; " +
                "-fx-border-color: #CCCCCC; " +
                "-fx-border-width: 1; " +
                "-fx-text-fill: #333333; " +
                "-fx-font-size: 24px; " +
                "-fx-padding: 10 10; " +
                "-fx-border-radius: 20px; " +
                "-fx-background-radius: 20px; " +
                "-fx-cursor: hand;"
        ));

        button.setOnMouseExited(e -> button.setStyle(
                "-fx-background-color: #FFFFFF; " +
                "-fx-border-color: #CCCCCC; " +
                "-fx-border-width: 1; " +
                "-fx-text-fill: #333333; " +
                "-fx-font-size: 24px; " +
                "-fx-padding: 10 10; " +
                "-fx-border-radius: 20px; " +
                "-fx-background-radius: 20px; "
        ));
    }

    public static void main(String[] args) {
        launch();
    }
}
