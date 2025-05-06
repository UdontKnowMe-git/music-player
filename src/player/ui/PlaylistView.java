package player.ui;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import player.db.Song;

import java.util.List;

public class PlaylistView {

    @FXML
    private ListView<String> playlistListView; // ListView to display songs

    private ObservableList<String> songTitles = FXCollections.observableArrayList();
    private List<Song> songs; // List of Song objects

    // Called during FXML initialization
    @FXML
    public void initialize() {
        playlistListView.setItems(songTitles); // Bind ListView to songTitles list
        playlistListView.setOnMouseClicked(this::handleSongClick);
    }

    // Set playlist and update UI
    public void setPlaylist(List<Song> songs) {
        this.songs = songs;
        songTitles.clear();
        for (Song song : songs) {
            songTitles.add(song.getTitle()); // Extract title
        }
    }

    // Get selected song title
    public String getSelectedSongTitle() {
        return playlistListView.getSelectionModel().getSelectedItem();
    }

    // Get selected song object
    public Song getSelectedSong() {
        int index = playlistListView.getSelectionModel().getSelectedIndex();
        if (index >= 0 && index < songs.size()) {
            return songs.get(index);
        }
        return null;
    }

    // Clear selection in the UI
    public void clearSelection() {
        playlistListView.getSelectionModel().clearSelection();
    }

    // Handle song double-click to trigger playback
    private void handleSongClick(MouseEvent event) {
        if (event.getClickCount() == 2) { // Double-click action
            Song selectedSong = getSelectedSong();
            if (selectedSong != null) {
                System.out.println("Playing: " + selectedSong.getTitle());
                // TODO: Integrate with media player playback system
            }
        }
    }
}
