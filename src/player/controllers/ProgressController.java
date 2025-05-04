import javafx.scene.control.Slider;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class ProgressController {

    @FXML
    private Slider progressSlider;
    @FXML
    private Label elapsedTimeLabel;
    @FXML
    private Label totalTimeLabel;

    private MediaPlayer mediaPlayer;

    // Call this method to initialize your mediaPlayer object
    public void initialize() {
        mediaPlayer = new MediaPlayer(new Media("file:/path/to/your/music/file.mp3"));
        
        mediaPlayer.currentTimeProperty().addListener((obs, oldTime, newTime) -> {
            updateProgress(newTime);
        });

        progressSlider.setOnMousePressed(event -> mediaPlayer.pause());
        progressSlider.setOnMouseReleased(event -> {
            mediaPlayer.seek(Duration.seconds(progressSlider.getValue()));
            mediaPlayer.play();
        });
    }

    private void updateProgress(Duration currentTime) {
        progressSlider.setValue(currentTime.toSeconds());
        elapsedTimeLabel.setText(formatTime(currentTime));
        totalTimeLabel.setText(formatTime(mediaPlayer.getTotalDuration()));
    }

    private String formatTime(Duration time) {
        int minutes = (int) time.toMinutes();
        int seconds = (int) (time.toSeconds() % 60);
        return String.format("%02d:%02d", minutes, seconds);
    }
}
