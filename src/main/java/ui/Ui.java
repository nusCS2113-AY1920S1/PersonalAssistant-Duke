package ui;

import duke.exception.DukeException;
import interpreter.Interpreter;
import interpreter.Parser;
import executor.command.Command;
import executor.command.CommandType;
import executor.task.TaskList;
import storage.StorageTask;
import storage.StorageWallet;
import utils.InfoCapsule;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ui {
    private Interpreter interpreterLayer;
    private Scanner scanner;
    private UiCode uiCode;
    private List<String> userInputHistory;
    private boolean exitRequest;
    static final String LOGO =
            "  ______            _        _______     _        _        _    \n"
                    + "(  __  \\ |\\     /|| \\    /\\(  ____ \\ __|_|___ __|_|___ __|_|___\n"
                    + "| (  \\  )| )   ( ||  \\  / /| (    \\/(  _____/(  _____/(  _____/\n"
                    + "| |   ) || |   | ||  (_/ / | (__    | (|_|__ | (|_|__ | (|_|__ \n"
                    + "| |   | || |   | ||   _ (  |  __)   (_____  )(_____  )(_____  )\n"
                    + "| |   ) || |   | ||  ( \\ \\ | (      /\\_|_|) |/\\_|_|) |/\\_|_|) |\n"
                    + "| (__/  )| (___) ||  /  \\ \\| (____/\\\\_______)\\_______)\\_______)\n"
                    + "(______/ (_______)|_/    \\/(_______/   |_|      |_|      |_|   \n"
                    + "                                                               \n"
                    + "\n";

    static final String LINE = "________________________________________________";

    /**
     * Constructor for the 'Ui' Class.
     */
    public Ui(String taskPath, String walletPath) {
        this.interpreterLayer = new Interpreter(taskPath, walletPath);
        this.scanner = new Scanner(System.in);
        this.uiCode = UiCode.UPDATE;
        this.exitRequest = false;
        this.userInputHistory = new ArrayList<String>();
    }

    /**
     * Method to start the Ui.
     */
    public void initialise() {
        printWelcomeMsg();
        while (!this.exitRequest) {
            this.interact();
        }
        this.exitUi();
    }

    /**
     * Obtains user input and executes commands.
     */
    private void interact()  {
        String userInput = this.scanInput();
        InfoCapsule infoCapsule = this.interpreterLayer.interpret(userInput);
        this.updateUi(infoCapsule);
    }

    /**
     * Scans the CLI for User Input.
     * @return String representing the User Input
     */
    private String scanInput() {
        String userInput;
        System.out.print("User : ");
        userInput = this.scanner.nextLine();
        this.userInputHistory.add(userInput);
        return userInput;
    }

    private void updateUi(InfoCapsule infoCapsule) {
        this.uiCode = infoCapsule.getUiCode();
        try {
            switch (this.uiCode) {
            case CLI:
                this.printToDisplay(infoCapsule.getOutputStr());
                break;
            case TOAST:
                this.dukeSays(infoCapsule.getOutputStr());
                break;
            case ERROR:
                infoCapsule.throwError();
                break;
            case EXIT:
                this.printGoodbyeMsg();
                this.exitRequest = true;
                break;
            case UPDATE:
                System.out.println(infoCapsule.getOutputStr());
                break;
            default:
            }
        } catch (DukeException e) {
            this.dukeSays(e.getMessage());
        }
    }

    /**
     * Exits the Ui.
     */
    private void exitUi() {
        this.scanner.close();
        InfoCapsule infoCapsule = this.interpreterLayer.requestSave();
        this.updateUi(infoCapsule);
    }

    /**
     * Prints Exit Msg.
     */
    private void printGoodbyeMsg() {
        dukeSays("Bye. Hope to see you again soon!");
        printSeparator();
    }

    private void printToDisplay(String string) {
        System.out.println(string + "\n");
        this.printSeparator();
    }

    /**
     * Helper method to indicate duke is saying something.
     *
     * @param stringX The message duke wants to say
     */
    private void dukeSays(String stringX) {
        System.out.println("Duke: " + stringX + "\n");
    }

    /**
     * Helper method to print Line Separator.
     */
    public void printSeparator() {
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
        InfoCapsule infoCapsule = this.interpreterLayer.interpret("balance");
        infoCapsule.setCodeUpdate();
        this.updateUi(infoCapsule);
    }

    /**
     * Prints the User's Current Expenses.
     */
    private void printUserExpenses() {
        InfoCapsule infoCapsule = this.interpreterLayer.interpret("expenses");
        infoCapsule.setCodeUpdate();
        this.updateUi(infoCapsule);
    }
}
