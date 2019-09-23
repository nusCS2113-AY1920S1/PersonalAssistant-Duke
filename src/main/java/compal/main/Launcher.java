package compal.main;

import compal.inputs.Main;

import javafx.application.Application;

/**
 * Holds launch code to start the GUI and pass CLI args.
 */
public class Launcher {

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

}
