package core;

import members.Member;
import gui.Window;
import commands.Command;
import tasks.Task;
import utils.DukeException;
import utils.Parser;
import utils.Storage;
import utils.Reminder;
import core.Ui;

import java.util.Scanner;
import java.util.ArrayList;

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
        members = storage.loadMemberList();
        Duke.instance = this;
    }

    /**
     * main running structure of Duke.
     */
    public void run() {
        new Window();
        Ui.welcome();
        Reminder.checkReminders(tasks);
        boolean isExit = false;
        Scanner in = new Scanner(System.in);
        while (!isExit) {
            try {
                String fullCommand = Ui.readLine(in);
                Command c = Parser.commandLine(fullCommand);
                c.execute(tasks, members, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                Ui.print(e.getMessage());
            }
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
    public static void main(String[] args) {
        new Duke("data/tasks.txt", "data/members.txt").run();
    }
}
