package ui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class MainView extends Application{
    public static void main(String[] args) {

        System.out.println("Starting LeaTime");

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.getIcons().add(
                new Image(getClass().getResourceAsStream("/images/leatime.jpg"))
        );
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/MainView.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root,1000,700);
        scene.getStylesheets().add(getClass().getResource("/ui/style.css").toExternalForm());
        stage.setMinWidth(800);
        stage.setMinHeight(500);
        stage.setScene(scene);
        stage.setTitle("LeaTime");
        stage.show();
    }
}
