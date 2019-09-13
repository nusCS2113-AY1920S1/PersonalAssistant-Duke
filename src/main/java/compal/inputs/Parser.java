package compal.inputs;

import compal.main.Duke;

import java.util.Scanner;

public class Parser {
    Duke duke;
    Scanner sc;


    public Parser(Duke d) {
        this.duke = d;
        sc = new Scanner(System.in);
    }

    /**
     * This function handles the main CLI parsing. Just pass in the cmd string and it will work its magic.
     * It uses regex to understand the command entered.
     *
     * @param cmd No return value
     * @Function
     * @UsedIn: COMPal.Duke.handleUserInput()
     */
    public void processCommands(String cmd) {

        if (cmd.equals("bye")) {
            duke.exitDuke();
        } else if (cmd.equals("list")) {
            duke.ui.listTasks();
        } else if (cmd.matches("done ([0-9]+)")) {
            duke.tasklist.taskDone(cmd);

        } else if (cmd.matches("delete ([0-9]+)")) {
            duke.tasklist.deleteTask(cmd);
        } else if (cmd.matches("(todo|event|deadline) .+")) {
            duke.tasklist.addTask(cmd);
        } else if (cmd.matches("find (.*)")) {
            duke.tasklist.findTask(cmd);
        } else if (cmd.matches("(todo|event|deadline)")) {
            try {
                throw new Duke.DukeException("☹ OOPS!!! The description of a " + cmd + " cannot be empty.");
            } catch (Duke.DukeException e) {
                duke.ui.printg(e.toString());
            }
        } else {
            try {
                throw new Duke.DukeException("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            } catch (Duke.DukeException e) {
                duke.ui.printg(e.toString());
            }
        }

    }


}
