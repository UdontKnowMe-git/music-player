# 🎵 JavaFX Music Player

A modern desktop music player with playlist management, built with JavaFX and SQLite.

## ✨ Features
- 🎶 Play/pause with time display (00:00 / 03:45)
- ⏭️ Next/previous track navigation
- 📁 Load music from any folder
- 🗃️ Persistent database (SQLite) for track storage
- 🎚️ Interactive progress bar with seeking
- 🖥️ Clean, dark-themed UI

## 🛠️ Tech Stack
- **Java 17+**
- **JavaFX** (GUI)
- **SQLite** (Database)

## 🚀 How to Run
### Prerequisites
- JDK 17+ (with JavaFX modules)
- [SQLite JDBC driver](https://github.com/xerial/sqlite-jdbc) (included in `lib/`)

### Using the Batch File (Windows)
1. Double-click `run.bat`
2. When prompted, enter your music folder path (e.g. `./music`)

### Manual Commands
```bash
# Compile
javac --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.media,javafx.fxml -cp ".;lib/sqlite-jdbc-3.49.1.0.jar" -d out src/player/Main.java src/player/controllers/*.java src/player/db/*.java src/player/ui/*.java

# Run
java --module-path "path/to/javafx-sdk/lib" --add-modules javafx.controls,javafx.media,javafx.fxml -cp "out;lib/sqlite-jdbc-3.49.1.0.jar" player.Main
```

## 📂 Project Structure
```
music-player/
├── db/               # Database files
│   └── music.db     
├── lib/              # Dependencies
│   └── sqlite-jdbc-3.49.1.0.jar
├── src/              # Source code
│   └── player/       # Main package
│       ├── controllers/  # Player logic
│       ├── db/       # Database classes
│       ├── ui/       # JavaFX views
│       └── Main.java # Entry point
├── run.bat           # One-click launcher
└── README.md         # This file
```

## 📝 License
MIT License - Feel free to modify and use!
