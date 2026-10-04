package ui.controller;

import application.StatisticService;
import domain.Catalog;
import domain.Direction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Arrays;

public class DirectionDetailsController {
    private BorderPane rootPanel;
    private Direction direction;
    private Catalog catalog;
    @FXML
    private StackPane chartContainer;
    private final StatisticService statisticService = new StatisticService();
    private BarChart<String, Number> timeChart;
    public void initialize(){
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        xAxis.setLabel("Час");
        yAxis.setLabel("Минуты");
        timeChart = new BarChart<>(xAxis,yAxis);
        timeChart.getStylesheets().add(
                getClass().getResource("/ui/style.css").toExternalForm()
        );
        timeChart.setTitle("Время");
        timeChart.setLegendVisible(false);
        chartContainer.getChildren().add(timeChart);
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
        int[] result = statisticService.getTimeByHoursForDay(direction, LocalDate.of(2026, 10, 4));
        System.out.println(Arrays.toString(result));
        XYChart.Series<String, Number> series = new XYChart.Series<>();

        for (int i = 0; i < result.length; i++) {
            series.getData().add(
                    new XYChart.Data<>(String.valueOf(i), result[i]));
        }
        timeChart.getData().clear();
        timeChart.getData().add(series);
    }

    public void handleWeek(ActionEvent event) {
    }

    public void handleMonth(ActionEvent event) {
    }

    public void handleYear(ActionEvent event) {
    }
}
