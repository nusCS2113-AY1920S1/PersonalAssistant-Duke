package ui;

import interpreter.Interpreter;
import interpreter.Parser;
import executor.command.Command;
import executor.command.CommandType;
import executor.task.TaskList;
import storage.Storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ui {
    private Storage storage;
    private TaskList taskList;
    private Wallet wallet;
    private Scanner scanner;
    private Boolean exitRequest;
    private List<String> userInputHistory;
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
    public Ui(String dataPath) {
        this.storage = new Storage(dataPath);
        this.taskList = storage.loadData();
        this.wallet = new Wallet();
        this.scanner = new Scanner(System.in);
        this.exitRequest = false;
        this.userInputHistory = new ArrayList<String>();
    }

    /**
     * Method to start the Ui.
     */
    public void initialise() {
        printWelcomeMsg();
        while (!this.exitRequest) {
            this.interact(this.taskList, this.wallet);
        }
        this.exitUi();
    }

    /**
     * Obtains user input and executes commands.
     */
    public void interact(TaskList taskList, Wallet wallet)  {
        String userInput = this.scanInput();
        this.exitRequest = Interpreter.interpret(taskList, wallet, userInput);
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
    private void exitUi() {
        this.scanner.close();
        this.storage.saveData(this.taskList);
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
    private void printWelcomeMsg() {
        printSeparator();
        System.out.println(LOGO); // Logo
        dukeSays("Hello! I'm Duke.\nDuke: What can I do for you?");
        printUserBalance();
        printUserExpenses();
        printSeparator();
    }

    /**
     * Prints the User's Current Balance.
     */
    private void printUserBalance() {
        Interpreter.interpret(this.taskList, this.wallet, "balance");
    }

    private void printUserExpenses() {
        Interpreter.interpret(this.taskList, this.wallet, "expenses");
    }
}
