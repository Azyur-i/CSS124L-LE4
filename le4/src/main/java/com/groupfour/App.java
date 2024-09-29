package com.groupfour;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    private GridPane selectedPane;

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
        JsonNode appNode = new ObjectMapper().readTree(getClass().getResourceAsStream("appData.json"));
        JsonNode gameNode = new ObjectMapper().readTree(getClass().getResourceAsStream("gameData.json"));
        JsonNode movieNode = new ObjectMapper().readTree(getClass().getResourceAsStream("movieData.json"));
        
        //Populate VBox containers
        populateVbox(appNode,appsVBox, stage);
        populateVbox(gameNode,gamesVBox, stage);
        populateVbox(movieNode, moviesVBox, stage);
        
        //Add UI elements
        sideBar.getChildren().addAll(label, gameBtn, appBtn, movieBtn);
        root.getChildren().addAll(sideBar, mainVBox);

        mainVBox.getChildren().add(gamesVBox);
    }

    public void populateVbox(JsonNode node, VBox vbox,Stage stage){
        if (node.isArray()) {
            int count = 0;
            for (JsonNode element : node) {
                String title = element.get("title").asText();
                String dev = element.get("dev").asText();
                double starRating = element.get("star_rating").asDouble();
                String downloads = element.get("downloads").asText();
                String description = element.get("description").asText();

                GridPane infoPane = new GridPane();

                //Main Page Info
                Label titleLabel = new Label(title);
                Label devLabel = new Label(dev);
                Label rateLabel = new Label(String.valueOf(starRating));
                Label dlLabel = new Label(downloads);

                //Copy for Extended Details Page (.add consumes the variable, need two of these- one for sidebar one for details)
                Label titleCopy = new Label(title);
                Label devCopy = new Label(dev);
                Label rateCopy = new Label(String.valueOf(starRating));
                Label dlCopy = new Label(downloads);
                Label descLabel = new Label(description);
                
                //Spacer
                infoPane.add(new Label(" "), 0, 0);
                infoPane.add(new Label(" "), 1, 0);

                //Contents
                infoPane.add(titleLabel, 0, 1);           
                infoPane.add(devLabel, 0, 2);
                infoPane.add(rateLabel, 0, 3);
                infoPane.add(dlLabel, 0, 4);
                
                //Click event on info,  opens extended details page
                infoPane.setOnMouseClicked(event -> {

                    //First instance of this will error
                    try{
                    selectedPane.setStyle("-fx-background-color:#808080;"); //CSS Styling, 
                    } catch (Exception e){
                        System.out.println("First CLick");
                    }


                    selectedPane = infoPane;
                    AnchorPane container = new AnchorPane();
                    HBox parentHBox = new HBox();
                    VBox infoVBox = new VBox();
                    
                    vbox.setStyle("-fx-background-color:#808080;"); //CSS Styling
                    selectedPane.setStyle("-fx-background-color: #C0C0C0;"); //CSS Styling

                    //**CSS Styling - Remove these at the end, just showed the variables you need to use

                    infoVBox.getChildren().addAll(titleCopy, devCopy, rateCopy, dlCopy,descLabel);
                    parentHBox.getChildren().addAll(vbox,infoVBox);
                    container.getChildren().add(parentHBox);
                    Scene infoScene = new Scene(container, 1280, 720);
                    stage.setScene(infoScene);
                    stage.show();



                });

                if (count < 4) {

                    vbox.getChildren().addAll(infoPane);
                    count=-1;
                } 
                count++;
            }
        } else {
            System.out.println("Error: JSON data is not an array");
        }
    }

    public static void main(String[] args) {
        launch();
    }

}