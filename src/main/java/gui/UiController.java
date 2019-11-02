package gui;

import logic.LogicController;

import java.util.ArrayList;
import java.util.Scanner;

import logic.command.CommandOutput;
import model.Task;
import common.DukeException;
import storage.Storage;

public class UiController {
    private static final String horizontalLine = "\t____________________________________________________________";
    private LogicController logicController;
    private boolean exit;
    //TODO TEMP array list of tasks, should change to a model object
    private ArrayList<Task> tasks;
    protected Storage storage;

    public UiController(LogicController logicController, Storage storage) {
        this.logicController = logicController;
        this.storage = storage;
    }

    //@@author JustinChia1997

    /**
     * Initializes and starts the UI
     */
    public void start() {
        //TODO remove temp tasks list
        TasksCounter tc = new TasksCounter(logicController.model.getTaskList());
        new Window(tc, logicController);
    }


    /**
     * This method is used to read a line from Scanner in, before sending command to relevant controller
     * This is the current method for a terminal only application, however as we progress to build a gui
     * it should be handled with a text change event listener or something similar
     * Can consider changing readCommand method name
     *
     * @param in the instantiated Scanner object
     */

    public void readCommand(Scanner in) throws DukeException {
        String fullCommand = in.nextLine();
        CommandOutput commandOutput = logicController.execute(fullCommand);
        print(commandOutput.getOutputToUser());
        this.exit = commandOutput.isExit();
    }


    /**
     * print out Duke logo and welcome message. This is for the old method
     */
    public static void welcome() {
        String logo = " _____                         ___  ___                                  \n"
                + "|_   _|                        |  \\/  |                                  \n"
                + "  | | ___  __ _ _ __ ___       | .  . | __ _ _ __   __ _  __ _  ___ _ __ \n"
                + "  | |/ _ \\/ _` | '_ ` _ \\      | |\\/| |/ _` | '_ \\ / _` |/ _` |/ _ \\ '__|\n"
                + "  | |  __/ (_| | | | | | |     | |  | | (_| | | | | (_| | (_| |  __/ |   \n"
                + "  \\_/\\___|\\__,_|_| |_| |_|     \\_|  |_/\\__,_|_| |_|\\__,_|\\__, |\\___|_|   \n"
                + "                                                          __/ |          \n"
                + "                                                         |___/   ";
        System.out.println(logo);
        System.out.println("Team Manager software is an application that helps you to better collaborate, \n"
                + "communicate, and, ultimately, manage a group of people. Team Manager is created \n"
                + "to increase productivity and improve the quality of work a team produces.");



    }

    /**
     * Print message onto temrinal ui
     *
     * @param toPrint string message that needs to be printed
     */
    public static void print(String toPrint) {
        System.out.println(horizontalLine);
        toPrint = "\t" + toPrint;
        for (int i = 0; i < toPrint.length(); i++) {
            if (toPrint.charAt(i) == '\n') {
                toPrint = toPrint.substring(0, i + 1) + "\t" + toPrint.substring(i + 1);
            }
        }
        System.out.println(toPrint);
        System.out.println(horizontalLine);
    }

    /**
     * This method is used to read a line from Scanner in.
     *
     * @param in the instantiated Scanner object
     * @return the String read
     */
    private static String readLine(Scanner in) {
        return in.nextLine();
    }

    public boolean isExit() {
        return exit;
    }
}