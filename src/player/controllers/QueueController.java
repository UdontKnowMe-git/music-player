package player.controllers;

import player.db.Song;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.LinkedList;
import java.util.Queue;

public class QueueController {
    private Queue<Song> songQueue = new LinkedList<>();
    private ObservableList<String> queueDisplay = FXCollections.observableArrayList();

    public void addSong(Song song) {
        songQueue.add(song);
        updateQueueDisplay();
    }

    public void removeSong(Song song) {
        songQueue.remove(song);
        updateQueueDisplay();
    }

    public Song getNextSong() {
        return songQueue.poll();
    }

    public ObservableList<String> getQueueDisplay() {
        return queueDisplay;
    }

    private void updateQueueDisplay() {
        queueDisplay.clear();
        for (Song song : songQueue) {
            queueDisplay.add(song.toString());
        }
    }
}
