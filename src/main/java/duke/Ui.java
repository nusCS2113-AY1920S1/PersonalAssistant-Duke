package duke;

import java.util.ArrayList;
import java.util.Scanner;
import duke.Data.Parser;
import duke.Task.TaskList;
import duke.Task.item;
import java.util.Date;
import duke.Task.TaskList;
import duke.Data.Storage;

public class Ui {

    /**
     * This function prints out the welcome message of Duke
     */
    public void welcome() {
        System.out.println("Hello! I'm Duke\n" +
                "What can I do for you?");
    }

    /**
     * This function prints out the goodbye message of Duke
     */
    public void goodbye() {
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * This function takes the standard input defined by the user and passes it
     * into the Parser class
     * @param input This is the string input defined by the user
     */
    public void readCommand(String input, TaskList tasks, Storage storage) {
        Parser parser = new Parser();
        parser.parseInput(input, tasks, storage);
    }

}
