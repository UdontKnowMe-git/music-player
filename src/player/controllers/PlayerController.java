package player.controllers;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import player.db.Song;

import java.io.File;

public class PlayerController {
    private MediaPlayer mediaPlayer;

    public void playSong(Song song) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }

        try {
            Media media = new Media(new File(song.getFilepath()).toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
            System.out.println("Playing: " + song.getTitle());
        } catch (Exception e) {
            System.out.println("Error playing song: " + e.getMessage());
        }
    }

    public void pause() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    public void resume() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
        }
    }
}
