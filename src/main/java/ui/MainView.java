package ui;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.util.logging.Logger;


public class MainView extends Application{
    public static void main(String[] args) {
        System.out.println("Starting LeaTime");

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Button startButton = new Button("Начать");
        startButton.setPrefWidth(370);
        startButton.setPrefHeight(190);
        VBox content = new VBox();
        content.setSpacing(30);
        content.setAlignment(Pos.CENTER);
        content.getChildren().add(startButton);
      BorderPane root = new BorderPane();
      root.getChildren().add(content);
      Scene scene = new Scene(root,1000,600);
      stage.setScene(scene);
      stage.setTitle("LeaTime");
      stage.show();


    }
}
