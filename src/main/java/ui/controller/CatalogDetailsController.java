package ui.controller;

import application.DirectionService;
import domain.Catalog;
import domain.Direction;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import persistence.JsonDataStorage;

import java.io.IOException;
import java.util.Objects;

public class CatalogDetailsController {
    private final JsonDataStorage storage = new JsonDataStorage();
    private final DirectionService directionService = new DirectionService();
    @FXML
    public Button createDirectionButton;
    @FXML
    public Label errorDirection;
    @FXML
    private ListView<Direction> directionList;
    @FXML
    private TextField directionNameField;
    private Catalog catalog;
    private BorderPane rootPanel;
    DirectionDetailsController controller = new DirectionDetailsController();
    public void setRootPanel(BorderPane rootPanel){
        this.rootPanel = rootPanel;
    }

    public void setCatalog(Catalog catalog) {
        this.catalog = catalog;
        directionList.getItems().addAll(catalog.getDirections());
    }
    @FXML
    private void handleCreateDirection(){
        System.out.println("Нажимаю на добавить объект");
        String name = directionNameField.getText();
        Direction direction = directionService.createDirection(name,catalog);
        directionList.getItems().add(direction);
        storage.updateCatalog(catalog);

    }

    @FXML
    private void initialize() {
        createDirectionButton.setDisable(true);
        directionNameField.textProperty().addListener((obs,old,neww) -> {
            if (neww.isBlank()) {
                createDirectionButton.setDisable(true);
                errorDirection.setText("Название направления не может быть пустым");

            } else if (neww.length() > 255) {
                createDirectionButton.setDisable(true);
                errorDirection.setText("Название направления не может содержать более 255 символов");

            } else {
                createDirectionButton.setDisable(false);
                errorDirection.setText("");
            }
        });

        directionList.setCellFactory(listView -> new ListCell<>(){
            private final HBox hbox = new HBox();
            private final Label label = new Label();
            private final Button button = new Button("Удалить направление");
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
            protected void updateItem(Direction direction, boolean empty) {
                super.updateItem(direction, empty);
                if (empty || direction == null){
                    setText(null);
                    setGraphic(null);
                }else {
                    label.setText(direction.getName());
                    setGraphic(hbox);
                    button.setOnAction(event -> {
                        System.out.println("Удаление направления");
                        handleDeleteDirection(direction);
                    });
                    setOnMouseClicked(event -> {
                        try {
                            System.out.println("Mouse event");
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/DirectionDetails.fxml"));
                            Parent view = loader.load();
                            rootPanel.setCenter(view);
                            DirectionDetailsController controller = loader.getController();
                            controller.setDirection(direction);
                            controller.setCatalog(catalog);
                            controller.setRootPanel(rootPanel);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
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

    public void handleDeleteDirection(Direction direction) {
        boolean deleted = storage.deleteDirection(direction);

        if (deleted) {
            catalog.getDirections().removeIf(
                    d -> Objects.equals(d.getId(), direction.getId())
            );
            directionList.getItems().removeIf(
                    d -> Objects.equals(d.getId(), direction.getId())
            );
        } else {
            System.out.println("Не удалось удалить направление");
        }
    }
}
