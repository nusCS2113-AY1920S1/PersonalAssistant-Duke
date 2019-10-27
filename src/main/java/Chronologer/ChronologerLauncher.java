package chronologer;
import javafx.application.Application;

/**
 * A launcher class to workaround classpath issues.
 */
public class ChronologerLauncher {
    public static void main(String[] args) { Application.launch(chronologer.ChronologerMain.class, args);
    }
}