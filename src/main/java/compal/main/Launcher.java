package compal.main;

import compal.inputs.Main;
import javafx.application.Application;


/**
 * This class merely holds the launch code to start the GUI and passes CLI args
 */
public class Launcher {

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

}
