// To run: java --module-path "E:\Hari\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.media,javafx.fxml -cp "out;lib/sqlite-jdbc-3.49.1.0.jar" player.Main
package player;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import player.controllers.*;
import player.db.*;
import player.ui.*;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.*;

public class Main extends Application {
    private static final String[] SUPPORTED_FORMATS = {".mp3", ".wav", ".aac", ".m4a"};

    private final DatabaseManager dbManager = DatabaseManager.getInstance();
    private PlayerController playerController;
    private PlaylistController playlistController;
    private QueueController queueController;
    private ControlsView controlsView;
    private PlayerView playerView;
    private PlaylistView playlistView;

    @Override
public void init() {
    playerController = new PlayerController();
    playlistController = new PlaylistController();
    queueController = new QueueController();

    controlsView = new ControlsView();
    playerView = new PlayerView();
    playlistView = new PlaylistView();

    // Set up playlist click handler
    playlistView.setPlayHandler(song -> {
        queueController.addSong(song);
        playSong(song);
    });

    linkComponents();
}

    private void linkComponents() {
        queueController.setPlayHandler(() -> {
            Song nextSong = queueController.getNextSong();
            if (nextSong != null) {
                playSong(nextSong);
            }
        });

        controlsView.getPlayButton().setOnAction(e -> togglePlayPause());
        controlsView.getNextButton().setOnAction(e -> {
            Song nextSong = queueController.getNextSong();
            if (nextSong != null) {
                playSong(nextSong);
            }
        });
        controlsView.getPrevButton().setOnAction(e -> {
            Song prevSong = queueController.getPreviousSong();
            if (prevSong != null) {
                playSong(prevSong);
            }
        });

        controlsView.getProgressSlider().valueProperty().addListener((obs, oldVal, newVal) -> {
            if (playerController.getMediaPlayer() != null && controlsView.getProgressSlider().isValueChanging()) {
                double total = playerController.getMediaPlayer().getTotalDuration().toSeconds();
                playerController.getMediaPlayer().seek(javafx.util.Duration.seconds(total * newVal.doubleValue() / 100));
            }
        });
    }

    @Override
    public void start(Stage primaryStage) {
        showFolderDialog(primaryStage);
    }

    private void showFolderDialog(Stage primaryStage) {
        TextInputDialog dialog = new TextInputDialog("./music");
        dialog.setTitle("Music Folder Location");
        dialog.setHeaderText("Enter path to your music folder");
        dialog.setContentText("Folder path:");

        dialog.showAndWait().ifPresent(folderPath -> {
            try {
                scanMusicFolder(folderPath);
                setupMainUI(primaryStage);
            } catch (Exception e) {
                showAlert("Error", "Failed to scan folder: " + e.getMessage());
                Platform.exit();
            }
        });
    }

    private void scanMusicFolder(String folderPath) throws Exception {
        Path path = Paths.get(folderPath);
        if (Files.isDirectory(path)) {
            clearSongsDatabase();

            File[] files = path.toFile().listFiles(file -> {
                if (file.isFile()) {
                    for (String format : SUPPORTED_FORMATS) {
                        if (file.getName().toLowerCase().endsWith(format)) {
                            return true;
                        }
                    }
                }
                return false;
            });

            if (files != null && files.length > 0) {
                List<Song> songs = new ArrayList<>();
                for (File file : files) {
                    Song song = addSongToDatabase(
                            file.getName().replaceFirst("[.][^.]+$", ""),
                            "Unknown",
                            file.getAbsolutePath()
                    );
                    if (song != null) {
                        songs.add(song);
                        queueController.addSong(song);
                    }
                }
                playlistView.setPlaylist(songs);
            }
        }
    }

    private void setupMainUI(Stage primaryStage) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #2b2b2b;");
        
        // Make controls view stretch horizontally
        controlsView.getView().setMaxWidth(Double.MAX_VALUE);
        BorderPane.setMargin(controlsView.getView(), new Insets(10)); // Now properly imported
        
        // Set up the layout
        root.setLeft(playlistView.getView());
        root.setCenter(playerView.getRoot());
        root.setBottom(controlsView.getView());
        
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void clearSongsDatabase() throws SQLException {
        try (Connection conn = dbManager.getConnection();
             Statement stmt = conn.createStatement()) {
            conn.setAutoCommit(false);
            stmt.executeUpdate("DELETE FROM playlist_songs");
            stmt.executeUpdate("DELETE FROM songs");
            conn.commit();
            System.out.println("Cleared existing songs from database");
        } catch (SQLException e) {
            System.err.println("Error clearing database: " + e.getMessage());
            throw e;
        }
    }

    private Song addSongToDatabase(String title, String artist, String filepath) {
        String sql = "INSERT INTO songs(title, artist, filepath) VALUES(?, ?, ?)";

        try (Connection conn = dbManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, title);
            pstmt.setString(2, artist);
            pstmt.setString(3, filepath);
            pstmt.executeUpdate();

            try (ResultSet rs = pstmt.getGeneratedKeys()) {
                if (rs.next()) {
                    return new Song(rs.getInt(1), title, artist, filepath);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error adding song to database: " + e.getMessage());
        }
        return null;
    }

    private void playSong(Song song) {
        playerController.playSong(song, () -> {
            Song nextSong = queueController.getNextSong();
            if (nextSong != null) {
                playSong(nextSong);
            }
        });
        playerView.updateNowPlaying(song);
        queueController.songStarted(song);
        
        // Initialize progress bar and time display
        controlsView.getProgressSlider().setValue(0);
        if (playerController.getMediaPlayer() != null) {
            double total = playerController.getMediaPlayer().getTotalDuration().toSeconds();
            controlsView.updateTime(0, total);
        }
        updateProgressBar();
    }

    private void updateProgressBar() {
        if (playerController.getMediaPlayer() != null) {
            // Update total duration first
            double total = playerController.getMediaPlayer().getTotalDuration().toSeconds();
            controlsView.updateTime(0, total); // Initialize with 0:00 / total time
            
            playerController.getMediaPlayer().currentTimeProperty().addListener((obs, oldTime, newTime) -> {
                if (!controlsView.getProgressSlider().isValueChanging()) {
                    double current = newTime.toSeconds();
                    double totalDuration = playerController.getMediaPlayer().getTotalDuration().toSeconds();
                    controlsView.getProgressSlider().setValue(current / totalDuration * 100);
                    controlsView.updateTime(current, totalDuration); // Update both current and total time
                }
            });
        }
    }

    private void togglePlayPause() {
        if (playerController.getMediaPlayer() != null) {
            if (playerController.getMediaPlayer().getStatus() == javafx.scene.media.MediaPlayer.Status.PLAYING) {
                playerController.getMediaPlayer().pause();
            } else {
                playerController.getMediaPlayer().play();
            }
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void stop() {
        if (playerController != null && playerController.getMediaPlayer() != null) {
            playerController.getMediaPlayer().dispose();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}