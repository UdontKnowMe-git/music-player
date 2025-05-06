package player.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

public class ControlsView {
    private HBox controlBar;
    private Button playButton;
    private Button prevButton;
    private Button nextButton;
    private Slider progressSlider;
    private Label currentTimeLabel;
    private Label durationLabel;

    public ControlsView() {
        controlBar = new HBox(10);
        controlBar.setPadding(new Insets(10));
        controlBar.setAlignment(Pos.CENTER);
        controlBar.setStyle("-fx-background-color: #1f1f1f;");

        // Create buttons
        prevButton = new Button("⏮");
        playButton = new Button("⏯");
        nextButton = new Button("⏭");
        
        // Style buttons
        String buttonStyle = "-fx-font-size: 14px; -fx-min-width: 40px; -fx-min-height: 40px;";
        prevButton.setStyle(buttonStyle);
        playButton.setStyle(buttonStyle);
        nextButton.setStyle(buttonStyle);

        // Create time labels
        currentTimeLabel = new Label("00:00");
        currentTimeLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");
        durationLabel = new Label("00:00");
        durationLabel.setStyle("-fx-text-fill: white; -fx-font-size: 12px;");

        // Create progress slider
        progressSlider = new Slider();
        progressSlider.setMin(0);
        progressSlider.setMax(100);
        HBox.setHgrow(progressSlider, Priority.ALWAYS); // Make it stretch
        
        // Spacer to push time labels to sides
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Add all components to control bar
        controlBar.getChildren().addAll(
            prevButton,
            playButton,
            nextButton,
            currentTimeLabel,
            progressSlider,
            durationLabel
        );
    }

    public void updateTime(double currentSeconds, double totalSeconds) {
        currentTimeLabel.setText(formatTime(currentSeconds));
        durationLabel.setText(formatTime(totalSeconds));
    }

    private String formatTime(double seconds) {
        int minutes = (int) seconds / 60;
        int secs = (int) seconds % 60;
        return String.format("%02d:%02d", minutes, secs);
    }

    // Getters
    public Button getPlayButton() { return playButton; }
    public Button getPrevButton() { return prevButton; }
    public Button getNextButton() { return nextButton; }
    public Slider getProgressSlider() { return progressSlider; }
    public HBox getView() { return controlBar; }
}