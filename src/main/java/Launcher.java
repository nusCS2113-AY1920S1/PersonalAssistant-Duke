import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class Launcher {

    Launcher() {

    }

    public static void main(String[] args) {
        Application.launch(WordUp.class, args);
    }
}