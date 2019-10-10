package duke.worker;

import duke.command.Command;
import duke.command.CommandType;
import duke.task.TaskList;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ui {
    protected Scanner scanner;
    protected Boolean exitRequest;
    protected List<String> userInputHistory;
    public static final String LOGO =
            " ____        _        \n"
                    + "|  _ \\ _   _| | _____ \n"
                    + "| | | | | | | |/ / _ \\\n"
                    + "| |_| | |_| |   <  __/\n"
                    + "|____/ \\__,_|_|\\_\\___|\n";

    public static final String LINE = "________________________________________________";

    /**
     * Constructor for the 'Ui' Class.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
        this.exitRequest = false;
        this.userInputHistory = new ArrayList<String>();
    }

    /**
     * Method to start the Ui.
     */
    public void initialise(TaskList taskList) {
        printWelcomeMsg();
        while (!this.exitRequest) {
            this.interact(taskList);
        }
        this.exitUi();
    }

    /**
     * Obtains user input and executes commands.
     */
    public void interact(TaskList taskList)  {
        // Declarations
        String userInput;
        CommandType commandType;
        Command command;
        // Function Calls
        userInput = this.scanInput();
        command = Parser.parse(userInput);
        command.execute(taskList);
        this.exitRequest = command.getExitRequest();
    }

    /**
     * Scans the CLI for User Input.
     *
     * @return String representing the User Input
     */
    public String scanInput() {
        String userInput;
        System.out.print("User : ");
        userInput = this.scanner.nextLine();
        this.userInputHistory.add(userInput);
        return userInput;
    }

    /**
     * Exits the Ui.
     */
    public void exitUi() {
        this.scanner.close();
        this.exitRequest = true;
    }

    /**
     * Prints Exit Msg.
     */
    public static void printGoodbyeMsg() {
        dukeSays("Bye. Hope to see you again soon!");
        printSeparator();
    }

    /**
     * Decide which Command to do.
     */
    public static void executeCommand(CommandType commandType, String userInput) {
    }

    // Level 1 - Prints userInput as though Duke is responding
    public static void echoUser(String userInput) {
        dukeSays(userInput);
    }

    /**
     * Helper method to indicate duke is saying something.
     *
     * @param stringX The message duke wants to say
     */
    public static void dukeSays(String stringX) {
        System.out.println("Duke: " + stringX + "\n");
    }

    /**
     * Helper method to print Line Separator.
     */
    public static void printSeparator() {
        System.out.println(LINE);
    }

    /**
     * Prints Welcome Message.
     */
    public static void printWelcomeMsg() {
        printSeparator();
        System.out.println(LOGO); // Logo
        dukeSays("Hello! I'm Duke.\nDuke: What can I do for you?");
        printSeparator();
    }
}
