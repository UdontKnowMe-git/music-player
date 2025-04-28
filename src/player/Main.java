// Incomplete main.java, edit and add codes as project moves forward.

package player;

import player.db.DatabaseManager;

public class Main {
    public static void main(String[] args) {
        // Connect to the database
        DatabaseManager.connect();

        // Optionally, you can perform other operations here, like loading songs or playlists
        System.out.println("Welcome to the music player!");

        // Close the database connection when done
        DatabaseManager.disconnect();
    }
}
