package ui.controller;

import application.TimeEntryService;
import domain.Direction;
import domain.TimeEntry;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;

import java.time.Duration;
import persistence.JsonDataStorage;

import java.time.LocalDateTime;
import java.util.List;

public class TimerController {
    private final JsonDataStorage storage = new JsonDataStorage();
    @FXML
    public Label timerLabel;
    @FXML
    private ComboBox<Direction> directionComboBox;
    private int elapsedSeconds = 0;
    private Timeline timeline;
    private final TimeEntryService timeEntryService = new TimeEntryService();
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @FXML
    private Button startButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button stopButton;
    private boolean startFlag = false;
    private Direction activeDirection;

    @FXML
    private void initialize() {
        startButton.setDisable(true);
        pauseButton.setDisable(true);
        stopButton.setDisable(true);
        directionComboBox.valueProperty().addListener((obs,old,neww) -> {
            if (directionComboBox.getValue() != null){
                startButton.setDisable(false);
            }else {
               startButton.setDisable(true);
            }
        });
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

    public void handleStart(ActionEvent event) {
        if (timeline == null){
            directionComboBox.setDisable(true);
            startButton.setDisable(true);
            pauseButton.setDisable(false);
            stopButton.setDisable(false);
            startFlag = true;
            startTime = LocalDateTime.now();
            timeline = new Timeline(new KeyFrame(
                    javafx.util.Duration.seconds(1),
                    event1 -> {
                        elapsedSeconds++;
                        timerLabel.setText(
                                String.format(
                                        "%02d:%02d:%02d",
                                        elapsedSeconds / 3600,
                                        (elapsedSeconds % 3600) / 60,
                                        elapsedSeconds % 60)
                        );
                    }
            ));
            timeline.setCycleCount(Timeline.INDEFINITE);
            activeDirection = directionComboBox.getValue();
            timeline.play();
            directionComboBox.setDisable(true);
            return;
        }
        if (timeline.getStatus() == Animation.Status.PAUSED){
            startButton.setDisable(true);
            pauseButton.setDisable(false);
            stopButton.setDisable(false);
            timeline.play();
        }
    }

    public void handlePause(ActionEvent event) {
        timeline.pause();
        startButton.setDisable(false);
        pauseButton.setDisable(true);
    }

    public void handleStop(ActionEvent event) {
        directionComboBox.setDisable(false);
        pauseButton.setDisable(true);
        stopButton.setDisable(true);
        startButton.setDisable(directionComboBox.getValue() == null);
        timeline.stop();
        endTime = LocalDateTime.now();
        Duration duration = Duration.ofSeconds(elapsedSeconds);
        TimeEntry timeEntry = timeEntryService.createTimeEntry(
                duration,
                startTime,
                endTime
        );
            storage.addTimeEntry(activeDirection.getId(),timeEntry);
            elapsedSeconds = 0;
            timerLabel.setText("00:00:00");
            startFlag = false;
            timeline = null;
        }
    }

