package player.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import player.controllers.PlayerController;
import player.db.Song;

public class PlayerView {
    private VBox view;
    private PlayerController controller;

    public PlayerView() {
        controller = new PlayerController();
        view = new VBox(15); // increased vertical spacing

        // Title
        Text title = new Text("EchoFy");
        title.setFont(Font.font("System", 24)); // Safe font

        // Song info
        Text songInfo = new Text();
        songInfo.setFont(Font.font("System", 16));

        // Buttons
        Button playButton = new Button("\u25B6");   // ▶ Play
        Button pauseButton = new Button("\u23F8");  // ⏸ Pause
        Button resumeButton = new Button("\u23F5"); // ⏵ Resume
        Button stopButton = new Button("\u23F9");   // ⏹ Stop

        playButton.setFont(new Font(20));
        pauseButton.setFont(new Font(20));
        resumeButton.setFont(new Font(20));
        stopButton.setFont(new Font(20));

        // Real song
        Song testSong = new Song(1, "Blinding Lights", "The Weeknd", "C:/MusicPlayer/MusicPlayer_M/songs/BlindingLights.mp3");

        // Show song name + artist
        songInfo.setText("Now Playing:      " + testSong.getTitle() + " - " + testSong.getArtist());

        // Actions
        playButton.setOnAction(e -> controller.playSong(testSong));
        pauseButton.setOnAction(e -> controller.pause());
        resumeButton.setOnAction(e -> controller.resume());
        stopButton.setOnAction(e -> controller.stop());

        // Layout
        HBox controls = new HBox(15, playButton, pauseButton, resumeButton, stopButton);
        controls.setAlignment(Pos.CENTER);

        view.setAlignment(Pos.CENTER);
        view.setPadding(new Insets(20));
        view.getChildren().addAll(title, songInfo, controls);
    }

    public VBox getView() {
        return view;
    }
}
