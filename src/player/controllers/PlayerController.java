package player.controllers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import player.db.Song;
import java.io.File; // Add this import

public class PlayerController {
    private MediaPlayer mediaPlayer;
    private Runnable onEndOfMedia;

    public void playSong(Song song, Runnable onEnd) {
        this.onEndOfMedia = onEnd;
        
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        try {
            Media media = new Media(new File(song.getFilepath()).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            
            if (onEndOfMedia != null) {
                mediaPlayer.setOnEndOfMedia(onEndOfMedia);
            }
            
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Error playing song: " + e.getMessage());
        }
    }

    public void togglePlayPause() {
        if (mediaPlayer != null) {
            if (isPlaying()) {
                mediaPlayer.pause();
            } else {
                mediaPlayer.play();
            }
        }
    }

    public boolean isPlaying() {
        return mediaPlayer != null && 
               mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}