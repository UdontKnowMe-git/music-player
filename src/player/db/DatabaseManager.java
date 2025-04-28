package player.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:db/music_db.sql";
    private static Connection connection = null;

    public static void connect() {
        try {
            connection = DriverManager.getConnection(DB_URL);
            System.out.println("Connected to database.");

            initializeDatabase();
        } catch (SQLException e) {
            System.out.println("Database connection failed: " + e.getMessage());
        }
    }

    private static void initializeDatabase() throws SQLException {
        String createSongsTable = """
            CREATE TABLE IF NOT EXISTS songs (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                title TEXT NOT NULL,
                artist TEXT,
                filepath TEXT NOT NULL
            );
            """;

        String createPlaylistsTable = """
            CREATE TABLE IF NOT EXISTS playlists (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL
            );
            """;

        String createPlaylistSongsTable = """
            CREATE TABLE IF NOT EXISTS playlist_songs (
                playlist_id INTEGER,
                song_id INTEGER,
                FOREIGN KEY (playlist_id) REFERENCES playlists(id),
                FOREIGN KEY (song_id) REFERENCES songs(id)
            );
            """;

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createSongsTable);
            stmt.execute(createPlaylistsTable);
            stmt.execute(createPlaylistSongsTable);
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void disconnect() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Disconnected from database.");
            }
        } catch (SQLException e) {
            System.out.println("Database disconnection failed: " + e.getMessage());
        }
    }
}