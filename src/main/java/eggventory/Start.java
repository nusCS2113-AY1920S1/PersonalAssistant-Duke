package eggventory;

import eggventory.ui.Gui;
import javafx.application.Application;


public class Start {
    /**
     * Main function that sets the save path and runs Eggventory.
     * Starts the CLI and GUI in separate threads so they can run simultaneously.
     */
    public static void main(String[] args) {
        String currentDir = System.getProperty("user.dir");
        String filePath = currentDir + "/data/saved_tasks.txt";

        Thread cliThread = new Thread(new Eggventory(filePath));
        cliThread.start();
        Application.launch(Gui.class, args);
    }
}
