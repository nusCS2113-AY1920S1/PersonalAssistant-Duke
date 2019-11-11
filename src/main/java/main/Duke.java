package main;

import storage.task.TaskList;
import ui.gui.MainGui;

public class Duke {
    protected static MainGui gui;
    protected static TaskList taskList;

    /**
     * The Main method by which main.Duke will be launched.
     */
    public static void main(String[] args) {
        initializeGui(args);
    }

    private static void initializeGui(String[] args) {
        gui = new MainGui();
        gui.initialize(args);
    }

    private static void initializeUi() {

    }
}
