package player.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import player.db.Song;

public class PlayerView {
    private BorderPane root;
    private Label nowPlayingLabel;

    public PlayerView() {
        root = new BorderPane();
        root.setPadding(new Insets(10));
        root.setStyle("-fx-background-color: #000000;");
        
        nowPlayingLabel = new Label("No song playing");
        nowPlayingLabel.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        nowPlayingLabel.setFont(Font.font("Arial"));
        nowPlayingLabel.setAlignment(Pos.CENTER);
        
        StackPane topPane = new StackPane(nowPlayingLabel);
        topPane.setPadding(new Insets(10));
        root.setTop(topPane);
    }

    public void updateNowPlaying(Song song) {
        nowPlayingLabel.setText("Now Playing: " + song.getTitle() + " - " + song.getArtist());
    }

    public BorderPane getRoot() {
        return root;
    }
}