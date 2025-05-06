//TO COMPILETE: This code is a JavaFX controller for a playlist view in a media player application. It manages the display of songs in a ListView and handles user interactions such as selecting and playing songs.
package player.ui;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import player.db.Song; // Assuming you have a Song model

public class PlaylistView {

    @FXML
    private ListView<String> playlistListView;  // ListView to display songs

    private ObservableList<String> songTitles = FXCollections.observableArrayList();  // List of song titles

    private List<Song> songs;  // Assuming you have a Song model

    // Initialize the PlaylistView and set the songs list
    public void initialize() {
        playlistListView.setItems(songTitles);  // Set ListView's data source

        // Optional: double-click to simulate playing the song
        playlistListView.setOnMouseClicked(this::handleSongClick);
    }

    // Method to set the playlist (song list)
    public void setPlaylist(List<Song> songs) {
        this.songs = songs;
        songTitles.clear();
        for (Song song : songs) {
            songTitles.add(song.getTitle());  // Assuming Song has a getTitle() method
        }
    }

    // Get the selected song title from ListView
    public String getSelectedSongTitle() {
        return playlistListView.getSelectionModel().getSelectedItem();
    }

    // Get the selected song's index in the ListView
    public int getSelectedIndex() {
        return playlistListView.getSelectionModel().getSelectedIndex();
    }

    // Clear the ListView selection
    public void clearSelection() {
        playlistListView.getSelectionModel().clearSelection();
    }

    // Method to handle song click (double-click to play)
    private void handleSongClick(MouseEvent event) {
        if (event.getClickCount() == 2) {  // Double-click action
            String selected = playlistListView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                System.out.println("Playing: " + selected);
                // Trigger your media player or play functionality here
            }
        }
    }
}
// <?xml version="1.0" encoding="UTF-8"?>

// <?import javafx.scene.control.ListView?>
// <?import javafx.scene.layout.AnchorPane?>

// <AnchorPane xmlns:fx="http://javafx.com/fxml" fx:controller="player.ui.PlaylistView">
//     <ListView fx:id="playlistListView" layoutX="10" layoutY="10" prefHeight="200" prefWidth="300"/>
// </AnchorPane>
// // 