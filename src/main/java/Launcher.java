import javafx.application.Application;

/**
 * Launches the application.
 */
public class Launcher {
    /**
     * Runs the program either in GUI or CLI mode.
     * @param args If appropriate argument is given, GUI will be launched.
     */
    public static void main(String[] args) {
        if (args.length > 0) {
            if (args[0].equals("GUI")) {
                Application.launch(Main.class, args);
            }
        } else {
            Duke.main(args);
        }
    }
}
