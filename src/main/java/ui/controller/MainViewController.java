package ui.controller;

import domain.Catalog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainViewController {
    @FXML
    private BorderPane rootPanel;
    @FXML
    private void initialize() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Welcome.fxml"));
        Parent welcomeView = loader.load();
        rootPanel.setCenter(welcomeView);
    }

    @FXML
    private void handleCatalogs() throws IOException {
        System.out.println("Нажали Каталоги");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Catalog.fxml"));
        Parent catalogView = loader.load();
        CatalogController controller = loader.getController();
        controller.setRootPanel(rootPanel);
        rootPanel.setCenter(catalogView);
    }
    @FXML
    private void handleTimer() throws IOException{
        System.out.println("Clicked timer");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Timer.fxml"));
        Parent timerView = loader.load();
        rootPanel.setCenter(timerView);
    }

}
