package player.db;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.*;
import java.util.stream.Collectors;

public class DatabaseManager {
    private static final String DB_FILE = "db/music.db";
    private static final String SQL_FILE = "db/music_db.sql";
    private static DatabaseManager instance;
    private Connection connection;

    private DatabaseManager() {
        initializeDatabase();
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private void initializeDatabase() {
        try {
            // Ensure db directory exists
            new File("db").mkdirs();

            String url = "jdbc:sqlite:" + DB_FILE;
            connection = DriverManager.getConnection(url);
            connection.setAutoCommit(false);

            // Check if database is empty
            if (isDatabaseEmpty()) {
                System.out.println("Initializing new database from SQL file");
                executeSqlFile();
            } else {
                System.out.println("Using existing database");
            }
            
            // Enable foreign key constraints
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("PRAGMA foreign_keys = ON");
            }
        } catch (Exception e) {
            System.err.println("Database initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private boolean isDatabaseEmpty() throws SQLException {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(
                 "SELECT name FROM sqlite_master WHERE type='table' AND name='songs'")) {
            return !rs.next();
        }
    }

    private void executeSqlFile() throws Exception {
        try (BufferedReader reader = new BufferedReader(new FileReader(SQL_FILE));
             Statement stmt = connection.createStatement()) {
            
            // Read and clean the SQL file
            String sql = reader.lines()
                .filter(line -> !line.trim().startsWith("--"))  // Remove comments
                .filter(line -> !line.trim().isEmpty())        // Remove empty lines
                .collect(Collectors.joining("\n"));
            
            // Split into individual statements
            String[] statements = sql.split(";\\s*\n");
            
            for (String statement : statements) {
                if (!statement.trim().isEmpty()) {
                    stmt.executeUpdate(statement);
                }
            }
            connection.commit();
        }
    }

    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            initializeDatabase();
        }
        return connection;
    }

    public void disconnect() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Disconnected from database");
            }
        } catch (SQLException e) {
            System.err.println("Database disconnection failed: " + e.getMessage());
        }
    }
}