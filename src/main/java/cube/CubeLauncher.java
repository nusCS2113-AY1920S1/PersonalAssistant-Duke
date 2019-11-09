package cube;

import javafx.application.Application;

/**
 * Launcher class to facilitate launching of JavaFX application directly from JAR.
 */
public class CubeLauncher {
    public static void main(String[] args) {

        if (args.length == 1 && args[0].equals("-cli")) {
            /*
                v2.0 Feature: To support launching CLI by specifying "-cli" args.
             */
            Application.launch(CubeApp.class, args);
        } else {
            Application.launch(CubeApp.class, args);
        }
    }
}
