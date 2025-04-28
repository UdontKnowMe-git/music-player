package player.db;

public class Song {
    private final int id;
    private final String title;
    private final String artist;
    private final String filepath;

    public Song(int id, String title, String artist, String filepath) {
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.filepath = filepath;
    }

    public int getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public String getArtist() {
        return artist;
    }
    public String getFilepath() {
        return filepath;
    }
}