@echo off
setlocal

:: Configure paths (modify these if needed)
set JAVAFX_PATH="E:\Hari\javafx-sdk-24.0.1\lib"
set SQLITE_JAR="lib\sqlite-jdbc-3.49.1.0.jar"
set MAIN_CLASS=player.Main

:: Compile
echo Compiling...
javac --module-path %JAVAFX_PATH% --add-modules javafx.controls,javafx.media,javafx.fxml -cp ".;%SQLITE_JAR%" -d out src/player/Main.java src/player/controllers/*.java src/player/db/*.java src/player/ui/*.java

if errorlevel 1 (
    echo Compilation failed!
    pause
    exit /b
)

:: Run
echo Running...
java --module-path %JAVAFX_PATH% --add-modules javafx.controls,javafx.media,javafx.fxml -cp "out;%SQLITE_JAR%" %MAIN_CLASS%

pause