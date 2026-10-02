package ui.controller;

import application.DirectionService;
import domain.Catalog;
import domain.Direction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import persistence.JsonDataStorage;

import java.io.IOException;

public class CatalogDetailsController {
    private final JsonDataStorage storage = new JsonDataStorage();
    private final DirectionService directionService = new DirectionService();
    @FXML
    private ListView<Direction> directionList;
    @FXML
    private TextField directionNameField;
    private Catalog catalog;
    private BorderPane rootPanel;
    public void setRootPanel(BorderPane rootPanel){
        this.rootPanel = rootPanel;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
        directionList.getItems().addAll(catalog.getDirections());
    }
    @FXML
    private void handleCreateDirection(){
        System.out.println("Нажимаю на добавить обьект");
        String name = directionNameField.getText();
        Direction direction = directionService.createDirection(name,catalog);
        directionList.getItems().add(direction);
        storage.updateCatalog(catalog);

    }
    @FXML
    private void initialize() {
        directionList.setCellFactory(listView -> new ListCell<>(){
            @Override
            protected void updateItem(Direction direction, boolean empty) {
                super.updateItem(direction, empty);
                if (empty || direction == null){
                    setText(null);
                }else {
                    setText(direction.getName());
                }
            }
        });
    }

    public void handleBack(ActionEvent event) {
        System.out.println("BackUp");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/Catalog.fxml"));
            Parent catalogView = loader.load();
            CatalogController controller = loader.getController();
            controller.setRootPanel(rootPanel);
            rootPanel.setCenter(catalogView);
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
