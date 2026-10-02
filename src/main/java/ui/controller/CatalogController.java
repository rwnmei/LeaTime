package ui.controller;

import application.CatalogService;
import domain.Catalog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import persistence.JsonDataStorage;
import ui.MainView;

import java.io.IOException;
import java.util.List;

public class CatalogController {
    private final JsonDataStorage storage = new JsonDataStorage();
    private final CatalogService catalogService = new CatalogService();
    @FXML
    private ListView<Catalog> catalogList;
    @FXML
    private TextField catalogNameField;
    private BorderPane rootPanel;
    public void setRootPanel(BorderPane rootPanel){
        this.rootPanel = rootPanel;
    }









    
    @FXML
    private void handleCreateCatalog(){
        System.out.println("Нажимаю на добавить обьект");
        String name = catalogNameField.getText();
        Catalog catalog = catalogService.createCatalog(name);
        storage.addCatalog(catalog);
        catalogList.getItems().add(catalog);
    }
    @FXML
    private void initialize() {
        List<Catalog> catalogs = storage.loadCatalog();
        catalogList.getItems().addAll(catalogs);
        catalogList.setCellFactory(listView -> new ListCell<>(){
            @Override
            protected void updateItem(Catalog catalog, boolean empty) {
                super.updateItem(catalog, empty);
                if (empty || catalog == null){
                    setText(null);
                }else {
                    setText(catalog.getName());
                    setOnMouseClicked(event -> {
                        try {
                            System.out.println("Mouse event");
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/CatalogDetails.fxml"));
                            Parent view = loader.load();
                            CatalogDetailsController controller = loader.getController();
                            controller.setCatalog(catalog);
                            controller.setRootPanel(rootPanel);
                            rootPanel.setCenter(view);

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }
            }
        });
    }
 }
