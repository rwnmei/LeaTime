package ui.controller;

import domain.Direction;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import persistence.JsonDataStorage;

import java.util.List;

public class TimerController {
    private final JsonDataStorage storage = new JsonDataStorage();
    @FXML
    private ComboBox<Direction> directionComboBox;
    @FXML
    private void initialize(){
        List<Direction> directions = storage.loadAllDirections();
        directionComboBox.getItems().addAll(directions);
        directionComboBox.setCellFactory(comboBox -> new ListCell<>() {
            @Override
            protected void updateItem(Direction direction, boolean empty) {
                super.updateItem(direction, empty);

                if (empty || direction == null) {
                    setText(null);
                } else {
                    setText(direction.getName());
                }
            }
        });

    }
}
