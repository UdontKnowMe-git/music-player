package player.controllers;

import java.util.LinkedList;
import player.db.Song;

public class QueueController {
    private LinkedList<Song> songQueue = new LinkedList<>(); // Changed from Queue to LinkedList
    private LinkedList<Song> playedSongs = new LinkedList<>();
    private Song currentSong;
    private Runnable playHandler;

    public void setPlayHandler(Runnable handler) {
        this.playHandler = handler;
    }

    public void addSong(Song song) {
        songQueue.add(song);
    }

    public Song getNextSong() {
        if (currentSong != null) {
            playedSongs.push(currentSong);
        }
        currentSong = songQueue.poll();
        return currentSong;
    }

    public Song getPreviousSong() {
        if (!playedSongs.isEmpty()) {
            if (currentSong != null) {
                songQueue.addFirst(currentSong);
            }
            currentSong = playedSongs.pop();
            return currentSong;
        }
        return null;
    }

    public void playNext() {
        if (playHandler != null) {
            playHandler.run();
        }
    }

    public void songStarted(Song song) {
        this.currentSong = song;
    }

    public Song getCurrentSong() {
        return currentSong;
    }
}