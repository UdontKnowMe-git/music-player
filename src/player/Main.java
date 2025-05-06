// Incomplete main.java, edit and add codes as project moves forward.
package player;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import player.controllers.PlayerController;
import player.controllers.PlaylistController;
import player.controllers.ProgressController;
import player.controllers.QueueController;
import player.db.DatabaseManager;
import player.ui.PlayerView;
import player.ui.PlaylistView;
import player.ui.QueueView;
import player.ui.ControlsView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.control.Label;

public class Main extends Application {

    private PlayerController playerController;
    private PlaylistController playlistController;
    private ProgressController progressController;
    private QueueController queueController;
    private ControlsView controlsView;

    @Override
    public void start(Stage primaryStage) {
        // Connect to the database
        DatabaseManager.connect();

        // Initialize controllers
        playerController = new PlayerController();
        playlistController = new PlaylistController();
        progressController = new ProgressController();
        queueController = new QueueController();

        // Initialize UI components
        PlayerView playerView = new PlayerView();
        PlaylistView playlistView = new PlaylistView();
        QueueView queueView = new QueueView();
        controlsView = new ControlsView();

        // Root layout
        BorderPane root = new BorderPane();

        // Title
        Label titleLabel = new Label("EchoFy - Music Player");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        root.setTop(titleLabel);
        BorderPane.setAlignment(titleLabel, Pos.CENTER);

        // Center - Player View
        root.setCenter(playerView.getView());

        // Bottom - Controls
        VBox controlsBox = new VBox(controlsView);
        controlsBox.setSpacing(15);
        controlsBox.setAlignment(Pos.CENTER);
        controlsBox.setPadding(new Insets(15));

        root.setBottom(controlsBox);

        // Left - Playlist
        VBox playlistBox = new VBox(new Label("Playlist"), playlistView);


        playlistBox.setSpacing(10);
        playlistBox.setPadding(new Insets(10));
        root.setLeft(playlistBox);

        // Right - Queue
        VBox queueBox = new VBox(new Label("Queue"), queueView);


        queueBox.setSpacing(10);
        queueBox.setPadding(new Insets(10));
        root.setRight(queueBox);

        // Scene setup
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("EchoFy - Music Player");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Handle application exit
        primaryStage.setOnCloseRequest(event -> DatabaseManager.disconnect());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
