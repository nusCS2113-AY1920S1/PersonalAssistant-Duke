package duke.command;

import duke.exception.DukeException;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class Ui {
    private final Parser parser;
    private final Scanner scanIn;
    private final PrintStream stdout;

    /**
     * Constructs a new Ui object, with a new Parser, constructs a Scanner for the input stream, and specifies output.
     * stream
     *
     * @see Parser
     */
    public Ui(InputStream stdin, PrintStream stdout) {
        parser = new Parser();
        this.stdout = stdout;
        this.scanIn = new Scanner(stdin);
    }

    /**
     * Prints logo and welcome message.
     */
    public void printWelcome() {
        String logoSpace = "                                  ";
        String titleSpace = "                                        ";
        String logo = logoSpace + " ____        _        " + System.lineSeparator()
                + logoSpace + "|  _ \\ _   _| | _____ " + System.lineSeparator()
                + logoSpace + "| | | | | | | |/ / _ \\" + System.lineSeparator()
                + logoSpace + "| |_| | |_| |   <  __/" + System.lineSeparator()
                + logoSpace + "|____/ \\__,_|_|\\_\\___|" + System.lineSeparator();
        stdout.println(System.lineSeparator() + titleSpace + "Hello from" + System.lineSeparator() + logo);
    }

    /**
     * Prints hello message to indicate that setup is completed and Duke can receive user input.
     */
    public void printHello() {
        print("Hello, I'm Duke!\nWhat can I do for you?");
    }

    /**
     * Gets the next line of input from the user.
     *
     * @return The next line of input from the user.
     */
    public String readLine() {
        return scanIn.nextLine();
    }

    /**
     * Sanitises input and use the Parse to extract the requested command, which will be loaded with parameters
     * extracted from the user's arguments.
     *
     * @return The command specified by the user, with arguments parsed.
     * @throws DukeException If Parser fails to find a matching command or the arguments do not meet the command's
     *                       requirements.
     */
    public Command parseCommand() throws DukeException {
        String inputStr = readLine();
        inputStr = inputStr.replaceAll("\t", "    "); //sanitise input
        return parser.parse(inputStr);
    }

    /**
     * Prints a message, indented and bracketed between two lines. Newlines in the message will have indents added
     * after them.
     *
     * @param msg Message to be printed.
     */
    public void print(String msg) {
        String line = "    ________________________________________________________________________________";
        String indentline = System.lineSeparator() + "    ";
        stdout.println(line);
        msg = msg.replaceAll("(\\r\\n|\\n|\\r)", indentline);
        stdout.println("    " + msg);
        stdout.println(line + System.lineSeparator());
    }

    /**
     * Closes the scanner and print a goodbye message. Ui should not be used any more after calling this function.
     */
    public void closeUi() {
        scanIn.close();
        print("Bye. Hope to see you again soon!");
    }

    //Leaving this here for future expansion (red text, etc.)

    /**
     * Prints the error message from an exception.
     *
     * @param excp Exception whose message we want to print.
     */
    public void printError(DukeException excp) {
        print(excp.getMessage());
    }
}
