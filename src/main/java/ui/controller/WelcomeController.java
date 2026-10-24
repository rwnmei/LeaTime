package ui.controller;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WelcomeController {
    @FXML
    private ImageView characterView;
    private double dragOffsetX;
    private double dragOffsetY;
    private boolean isDragging = false;
    private final List<Image> idleFrames = new ArrayList<>();
    private final List<Image> followFrames = new ArrayList<>();
    private final List<Image> clickJumpFrames = new ArrayList<>();
    private final List<Image> blinkFrames = new ArrayList<>();
    private final List<Image> draggedFrames = new ArrayList<>();
    private Timeline idleAnimation;
    private int currentFrame = 0;
    @FXML
    private StackPane welcomePane;
    boolean isJumping;
    private final PauseTransition idleDelay =
            new PauseTransition(Duration.seconds(3));

    @FXML
    private void initialize() {
        loadIdleFrames();
        loadFollowFrames();
        loadJumpFrames();
        loadBlinkFrames();
        loadDraggedFrames();
        characterView.setOnMouseClicked(event -> {
            playClickJumpAnimation();
            event.consume();
        });
        idleDelay.setOnFinished(event -> startIdleAnimation());
        startIdleAnimation();
        welcomePane.setOnMouseMoved(this::handleMouseMove);
        ;
    }
    private void draggedPerson(MouseEvent event){

    }
    private void loadDraggedFrames(){
        for (int i = 1; i <= 12; i++) {
            String path = String.format(
                    "/character/dragged/frame_%03d.png", i
            );

            Image frame = new Image(
                    Objects.requireNonNull(
                            getClass().getResourceAsStream(path),
                            "Не найден кадр: " + path
                    )
            );

            draggedFrames.add(frame);
        }

        characterView.setImage(draggedFrames.get(0));
    }
    private void playClickJumpAnimation() {
        if (isJumping) {
            return;
        }

        isJumping = true;
        idleDelay.stop();

        if (idleAnimation != null) {
            idleAnimation.stop();
        }

        Timeline jumpAnimation = new Timeline();

        for (int i = 0; i < clickJumpFrames.size(); i++) {
            final int frameIndex = i;

            jumpAnimation.getKeyFrames().add(
                    new KeyFrame(
                            Duration.millis(80L * (i + 1)),
                            event -> characterView.setImage(
                                    clickJumpFrames.get(frameIndex)
                            )
                    )
            );
        }

        jumpAnimation.setOnFinished(event -> {
            Timeline blinkAnimation = new Timeline();

            for (int i = 0; i < blinkFrames.size(); i++) {
                final int frameIndex = i;

                blinkAnimation.getKeyFrames().add(
                        new KeyFrame(
                                Duration.millis(100L * (i + 1)),
                                e -> characterView.setImage(
                                        blinkFrames.get(frameIndex)
                                )
                        )
                );
            }

            blinkAnimation.setOnFinished(e -> {
                isJumping = false;
                startIdleAnimation();
                idleDelay.playFromStart();
            });

            blinkAnimation.play();
        });

        jumpAnimation.play();
    }
    private void loadBlinkFrames(){
        for (int i = 1; i <= 10; i++) {
            String path = String.format(
                    "/character/blink/frame_%03d.png", i
            );

            Image frame = new Image(
                    Objects.requireNonNull(
                            getClass().getResourceAsStream(path),
                            "Не найден кадр: " + path
                    )
            );

            blinkFrames.add(frame);
        }

        characterView.setImage(blinkFrames.get(0));
    }
    private void loadJumpFrames(){
        for (int i = 1; i <= 20; i++) {
            String path = String.format(
                    "/character/click_jump/frame_%03d.png", i
            );
            Image frame = new Image(
                    Objects.requireNonNull(
                            getClass().getResourceAsStream(path),
                            "Не найден кадр: " + path
                    )
            );
            clickJumpFrames.add(frame);
        }
        characterView.setImage(clickJumpFrames.get(0));
    }
    private void loadIdleFrames() {
        for (int i = 1; i <= 12; i++) {
            String path = String.format(
                    "/character/idle/frame_%03d.png", i
            );
            Image frame = new Image(
                    Objects.requireNonNull(
                            getClass().getResourceAsStream(path),
                            "Не найден кадр: " + path
                    )
            );

            idleFrames.add(frame);
        }

        characterView.setImage(idleFrames.get(0));
    }

    private void startIdleAnimation() {
        if (idleAnimation != null) {
            idleAnimation.stop();
        }
        idleAnimation = new Timeline(
                new KeyFrame(Duration.millis(100), event -> {
                    currentFrame = (currentFrame + 1) % idleFrames.size();
                    characterView.setImage(idleFrames.get(currentFrame));
                })
        );

        idleAnimation.setCycleCount(Timeline.INDEFINITE);
        idleAnimation.play();
    }
    private void handleMouseMove(MouseEvent event) {
        if (isJumping) {
            return;
        }
        double mouseX = event.getX();
        double mouseY = event.getY();
        double petX = characterView.getBoundsInParent().getCenterX();
        double petY = characterView.getBoundsInParent().getCenterY();
        double dx = mouseX - petX;
        double dy = mouseY - petY;
        double distance = Math.sqrt(dx * dx + dy * dy);
        String direction;
        int depth;
        if (distance < 50) {
            depth = 1;
        } else if (distance < 120) {
            depth = 2;
        } else if (distance < 200) {
            depth = 3;
        } else {
            depth = 4;
        }
        if (dx < 0 && dy < 0) {
            direction = "UP_LEFT";
        } else if (dx > 0 && dy < 0) {
            direction = "UP_RIGHT";
        } else if (dx < 0 && dy > 0) {
            direction = "DOWN_LEFT";
        } else if (dx > 0 && dy > 0) {
            direction = "DOWN_RIGHT";
        } else if (dx < 0) {
            direction = "LEFT";
        } else if (dx > 0) {
            direction = "RIGHT";
        } else if (dy < 0) {
            direction = "UP";
        } else {
            direction = "DOWN";
        }
        int directionIndex = switch (direction) {
            case "LEFT" -> 0;
            case "RIGHT" -> 1;
            case "UP" -> 2;
            case "DOWN" -> 3;
            case "UP_LEFT" -> 4;
            case "UP_RIGHT" -> 5;
            case "DOWN_LEFT" -> 6;
            case "DOWN_RIGHT" -> 7;
            default -> 0;
        };
        int frameIndex = directionIndex * 4 + depth - 1;
        if (idleAnimation != null) {
            idleAnimation.stop();
        }
        characterView.setImage(followFrames.get(frameIndex));
        idleDelay.playFromStart();
    }
    private void loadFollowFrames() {
        for (int i = 1; i <= 32; i++) {
            String path = String.format(
                    "/character/cursor_follow/frame_%03d.png", i
            );

            Image frame = new Image(
                    Objects.requireNonNull(
                            getClass().getResourceAsStream(path),
                            "Не найден кадр: " + path
                    )
            );

            followFrames.add(frame);
        }
    }
}
