package gui;

import logic.LogicController;

import java.util.ArrayList;
import java.util.Scanner;

import logic.command.CommandOutput;
import tasks.Task;
import utils.DukeException;
import utils.TasksCounter;
import utils.Storage;

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
     * */
    public void start(){
        //TODO remove temp tasks list
        tasks = storage.loadTaskList();
        TasksCounter tc = new TasksCounter(tasks);
        new Window(tc, logicController);
    }


    /**
     * This method is used to read a line from Scanner in, before sending command to relevant controller
     * This is the current method for a terminal only application, however as we progress to build a gui
     * it should be handled with a text change event listener or something similar
     * Can consider changing readCommand method name
     *
     * @param in the instantiated Scanner object
     * @return the String read
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
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        System.out.println(logo);
        print("Hello! I'm Duke\nWhat can I do for you?");

    }

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