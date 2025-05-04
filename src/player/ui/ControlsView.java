package player.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.media.MediaPlayer;

public class ControlsView {

    @FXML
    private Button playButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button nextButton;

    @FXML
    private Button previousButton;

    private MediaPlayer mediaPlayer;

    // Set MediaPlayer from outside (e.g., main player or controller)
    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    @FXML
    private void handlePlay() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    @FXML
    private void handlePause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @FXML
    private void handleNext() {
        System.out.println("Next song..."); // Hook into queue logic
        // You can implement your own queue/playlist management here
    }

    @FXML
    private void handlePrevious() {
        System.out.println("Previous song..."); // Hook into queue logic
    }
}

// <HBox spacing="10" alignment="CENTER">
//     <Button fx:id="previousButton" text="⏮" onAction="#handlePrevious"/>
//     <Button fx:id="playButton" text="▶" onAction="#handlePlay"/>
//     <Button fx:id="pauseButton" text="⏸" onAction="#handlePause"/>
//     <Button fx:id="nextButton" text="⏭" onAction="#handleNext"/>
// </HBox>