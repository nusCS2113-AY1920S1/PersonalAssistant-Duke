package launcher;

import javafx.application.Application;
import views.ui.Main;

/**
 * A launcher class to workaround classpath issues.
 */
public class UILauncher {
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}