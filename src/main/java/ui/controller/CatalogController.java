package ui.controller;

import application.CatalogService;
import domain.Catalog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
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
    @FXML
    private Button createCatalogButton;
    @FXML
    private Label errorLabel;

    private TextFormatter<String> textFormatter = new TextFormatter<>(change -> {
        if (change.getControlNewText().length() > 255) {
            return null;
        }

        return change;
    });
    public void setRootPanel(BorderPane rootPanel){
        this.rootPanel = rootPanel;
    }









    
    @FXML
    private void handleCreateCatalog(){
        System.out.println("Tap in create catalog");
        String name = catalogNameField.getText();
        Catalog catalog = catalogService.createCatalog(name);
        storage.addCatalog(catalog);
        catalogList.getItems().add(catalog);
    }
    @FXML
    private void initialize() {
        createCatalogButton.setDisable(true);
        catalogNameField.textProperty().addListener((obs,old,neww) -> {
            if (neww.isBlank()) {
                createCatalogButton.setDisable(true);
                errorLabel.setText("Название каталога не может быть пустым");

            } else if (neww.length() > 255) {
                createCatalogButton.setDisable(true);
                errorLabel.setText("Название каталога не может содержать более 255 символов");

            } else {
                createCatalogButton.setDisable(false);
                errorLabel.setText("");
            }
        });
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
