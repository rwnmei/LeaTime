package ui.controller;

import domain.Catalog;
import domain.Direction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class DirectionDetailsController {
    private BorderPane rootPanel;
    private Direction direction;
    private Catalog catalog;
    @FXML
    private StackPane chartContainer;
    @FXML
    private StackPane getChartContainer;
    public void initialize(){
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String,Number> chart = new BarChart<>(xAxis,yAxis);
        chart.setTitle("Время");
        chartContainer.getChildren().add(chart);
    }
    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
    }

    public void setRootPanel(BorderPane rootPanel){
        this.rootPanel = rootPanel;
    }
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void handleBack(ActionEvent event) {
        System.out.println("BackUp");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/CatalogDetails.fxml"));
            Parent catalogView = loader.load();
            CatalogDetailsController controller = loader.getController();
            controller.setCatalog(catalog);
            controller.setRootPanel(rootPanel);
            rootPanel.setCenter(catalogView);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void handleDay(ActionEvent event) {
    }

    public void handleWeek(ActionEvent event) {
    }

    public void handleMonth(ActionEvent event) {
    }

    public void handleYear(ActionEvent event) {
    }
}
