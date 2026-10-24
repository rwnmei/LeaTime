package ui.controller;

import application.CatalogService;
import domain.Catalog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.text.Text;
import persistence.JsonDataStorage;
import java.io.IOException;
import java.util.List;

public class CatalogController {


    //Инициализация переменных
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


    //Основной метод
    @FXML
    private void initialize() {
        createCatalogButton.setDisable(true);
        catalogNameField.textProperty().addListener((obs,old,now) -> {
            if (now.isBlank()) {
                createCatalogButton.setDisable(true);
                errorLabel.setText("Название каталога не может быть пустым");

            } else if (now.length() > 255) {
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
            private final HBox hbox = new HBox();
            private final Label label = new Label();
            private final Button button = new Button("Удалить");
            {
                button.setStyle(
                        "-fx-background-color: #39353E;\n" +
                                "    -fx-text-fill: #F2B8CA;\n" +
                                "    -fx-font-family: \"Century Gothic\";\n" +
                                "    -fx-font-size: 14px;\n" +
                                "    -fx-font-weight: bold;\n" +
                                "    -fx-padding: 10px 18px;\n" +
                                "    -fx-background-radius: 12px;\n" +
                                "    -fx-border-color: #514753;\n" +
                                "    -fx-border-radius: 12px;\n" +
                                "    -fx-cursor: hand;"
                );
                hbox.setAlignment(Pos.CENTER_LEFT);
                HBox.setHgrow(label, Priority.ALWAYS);
                label.setMaxWidth(Double.MAX_VALUE);
                hbox.getChildren().addAll(label,button);
            }
            @Override
            protected void updateItem(Catalog catalog, boolean empty) {
                super.updateItem(catalog, empty);
                if (empty || catalog == null){
                    setText(null);
                    setGraphic(null);
                }else {
                    label.setText(catalog.getName());
                    setGraphic(hbox);
                    button.setOnAction(event -> {
                        System.out.println("Удаление каталога");
                        handleDeleteCatalog(catalog);

                    });
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


    //Не помню
    public void setRootPanel(BorderPane rootPanel){
        this.rootPanel = rootPanel;
    }


    //Удаления каталога
    @FXML
    private void handleDeleteCatalog(Catalog catalog){
        System.out.println("Tap in delete catalog");
        boolean deleted = storage.deleteCatalog(catalog);
        if (deleted){
            catalogList.getItems().remove(catalog);
        }else {
            System.out.println("Exception delete catalog");
        }


    }


    //Создание каталога
    @FXML
    private void handleCreateCatalog() {
        String name = catalogNameField.getText();
        Catalog catalog = catalogService.createCatalog(name);
        storage.addCatalog(catalog);
        catalogList.getItems().add(catalog);

    }


 }
