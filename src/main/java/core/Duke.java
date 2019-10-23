package core;

import members.Member;
import gui.Window;
import commands.Command;
import model.Model;
import tasks.Task;
import utils.DukeException;
import utils.Parser;
import utils.Storage;
import utils.TasksCounter;
import utils.Reminder;

import java.util.Scanner;
import java.util.ArrayList;

//=======New Imports for new structure
import gui.UiController;
import logic.LogicController;
import model.ModelController;

/**
 * This is the main class to be executed for DUKE PRO application
 *
 * @author T14-4 team
 */
public class Duke {

    /**
     * deals with loading tasks from the file and saving tasks in the file
     */
    private Storage storage;

    /**
     * an array list contains all the tasks
     */
    private ArrayList<Task> tasks;

    private ArrayList<Member> members;

    //=============New instantiation of new structure objects===================
    protected Model modelController;
    protected LogicController logicController;
    protected UiController uiController;

    public static Duke instance;


    /**
     * A constructor which applies the file path to load previous data
     *
     * @param taskFilePath   the file path of task list
     * @param memberFilePath the file path of member list
     */
    public Duke(String taskFilePath, String memberFilePath) {
        storage = new Storage(taskFilePath, memberFilePath);
        tasks = storage.loadTaskList();
        members = storage.loadMemberList(tasks);
        //========= instantiation for controllers ==============
        modelController = new ModelController();
        logicController = new LogicController(modelController);
        uiController = new UiController(logicController, storage);
        Duke.instance = this;
    }

    /**
     * main running structure of Duke.
     */
    public void run() throws DukeException{
        uiController.welcome();
        uiController.start();
        //Reminder.checkReminders(tasks);
        boolean isExit = false;
        Scanner in = new Scanner(System.in);
        while (!isExit) {
                uiController.readCommand(in);
                isExit = uiController.isExit();
        }
    }

    /**
     * Attempts to parse and execute given input
     *
     * @param command input line from user
     */
    public void doCommand(String command) {
        Command c;
        try {
            c = Parser.commandLine(command);
            c.execute(tasks, members, storage);
            if (c.isExit()) {
                System.exit(0);
            }
        } catch (DukeException e) {
            Ui.print(e.getMessage());
        }
    }


    /**
     * Static version of doCommand. For window access
     *
     * @param command input line from user
     */
    public static void processCommand(String command) {
        instance.doCommand(command);
    }

    /**
     * Main method of the entire project.
     *
     * @param args command line arguments, not used here
     */
    public static void main(String[] args) throws DukeException {
        new Duke("data/tasks.txt", "data/members.txt").run();
    }
}
