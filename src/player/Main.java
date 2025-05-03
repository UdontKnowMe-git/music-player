// Incomplete main.java, edit and add codes as project moves forward.

package player;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import player.db.DatabaseManager;
import player.ui.PlayerView;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        DatabaseManager.connect();

        PlayerView view = new PlayerView();
        Scene scene = new Scene(view.getView(), 600, 400);

        primaryStage.setTitle("Music Player");
        primaryStage.setScene(scene);
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> DatabaseManager.disconnect());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
