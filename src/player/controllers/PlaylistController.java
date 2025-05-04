package player.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import player.db.Song;

public class PlaylistController {

    @FXML
    private ListView<String> playlistView;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    private ObservableList<String> songs;

    public void initialize() {
        songs = FXCollections.observableArrayList();
        playlistView.setItems(songs);

        // Sample songs (in a real app, load from DB or file)
        songs.addAll("Song A", "Song B", "Song C");

        playlistView.setOnMouseClicked(this::handleSongClick);
    }

    @FXML
    private void handleAddSong() {
        // You would normally open a file chooser or prompt
        songs.add("New Song");
    }

    @FXML
    private void handleRemoveSong() {
        String selected = playlistView.getSelectionModel().getSelectedItem();
        if (selected != null) {
            songs.remove(selected);
        }
    }

    private void handleSongClick(MouseEvent event) {
        if (event.getClickCount() == 2) {
            String selected = playlistView.getSelectionModel().getSelectedItem();
            if (selected != null) {
                // Play selected song
                System.out.println("Playing: " + selected);
                // You can link this to a MediaPlayer or another controller
            }
        }
    }
}

// <ListView fx:id="playlistView" layoutX="20" layoutY="20" prefHeight="200" prefWidth="300"/>
// <Button fx:id="addButton" layoutX="20" layoutY="230" text="Add Song" onAction="#handleAddSong"/>
// <Button fx:id="removeButton" layoutX="120" layoutY="230" text="Remove Song" onAction="#handleRemoveSong"/>
