package core;


import common.DukeException;
import model.Model;


import java.util.Scanner;

import java.util.ArrayList;
//=======New Imports for new structure
import gui.UiController;
import logic.LogicController;
import logic.ReminderController;
import model.ModelController;
import storage.Storage;

/**
 * This is the main class to be executed for DUKE PRO application
 *
 * @author T14-4 team
 */
public class Duke {

    //=============New instantiation of new structure objects===================
    protected Model modelController;
    protected LogicController logicController;
    protected UiController uiController;
    protected ReminderController reminderController;
    protected Storage storage;

    public static Duke instance;


    /**
     * A constructor which applies the file path to load previous data
     */
    public Duke() {
        //========= instantiation for controllers ==============
        modelController = new ModelController();
        logicController = new LogicController(modelController);
        uiController = new UiController(logicController, storage);
        reminderController = new ReminderController(modelController);
        Duke.instance = this;
    }

    /**
     * main running structure of Duke.
     */

    public void run() throws DukeException {
        UiController.welcome();
        uiController.start();
        boolean isExit = false;
        Scanner in = new Scanner(System.in);
        while (isExit) {
            uiController.readCommand(in);
            isExit = uiController.isExit();
        }
    }

    /**
     * Main method of the entire project.
     *
     * @param args command line arguments, not used here
     */
    public static void main(String[] args) throws DukeException {
        new Duke().run();
    }
}
