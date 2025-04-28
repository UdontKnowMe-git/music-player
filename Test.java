import javafx.application.Application;
import javafx.stage.Stage;
//To compile:
// javac --module-path "E:\Hari\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.media -d out Test.java

//To run:
// java --module-path "E:\Hari\javafx-sdk-24.0.1\lib" --add-modules javafx.controls,javafx.media -cp out Test

public class Test extends Application {
    public void start(Stage stage) {
        stage.setTitle("JavaFX Works!");
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
