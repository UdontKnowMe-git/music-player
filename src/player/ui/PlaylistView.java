package player.ui;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import player.db.Song;
import java.util.List;
import java.util.function.Consumer;

public class PlaylistView {
    private VBox view;
    private ListView<Song> playlistListView;

    public PlaylistView() {
        view = new VBox();
        view.setStyle("-fx-background-color: #2a2a2a; -fx-padding: 10;");
        
        Label title = new Label("Playlist");
        title.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");
        
        playlistListView = new ListView<>();
        playlistListView.setStyle("-fx-control-inner-background: #2a2a2a; " +
                               "-fx-text-fill: white; -fx-font-size: 14px;");
        playlistListView.setCellFactory(lv -> new SongListCell());
        
        view.getChildren().addAll(title, playlistListView);
    }

    public void setPlaylist(List<Song> songs) {
        playlistListView.getItems().setAll(songs);
    }

    public void setPlayHandler(Consumer<Song> handler) {
        playlistListView.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                Song selected = playlistListView.getSelectionModel().getSelectedItem();
                if (selected != null && handler != null) {
                    handler.accept(selected);
                }
            }
        });
    }

    public VBox getView() {
        return view;
    }
    
    // Custom cell for song display
    private static class SongListCell extends javafx.scene.control.ListCell<Song> {
        @Override
        protected void updateItem(Song song, boolean empty) {
            super.updateItem(song, empty);
            if (empty || song == null) {
                setText(null);
            } else {
                setText(song.getTitle() + " - " + song.getArtist());
                setTextFill(Color.WHITE);
                setStyle("-fx-background-color: #2a2a2a;");
            }
        }
    }
}