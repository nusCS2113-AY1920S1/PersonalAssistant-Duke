package logic;

import command.Command;
import commons.DukeException;
import commons.Message;
import commons.Ui;
import parser.Parser;
import storage.Storage;
import task.TaskList;

import java.util.Scanner;

/**
 * Control logic of Duke.
 */
public class Duke {

    private Scanner scanner;
    private Storage storage;
    private static TaskList tasks;

    /**
     * Returns the response to userInput.
     *
     * @param input input from user.
     * @return the response to userInput.
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.getCommand(input);
            command.execute(tasks, storage);
        } catch (DukeException e) {
            Ui.showError(e.getMessage());
        }
        return Ui.getOutput();
    }

    /**
     * Initializes scanner and storage.
     */
    public void initialize() {

        Ui.showToUser(Message.getWelcome());

        scanner = new Scanner(System.in);

        storage = new Storage("duke.dat");

        try {
            tasks = storage.deserialize();
        } catch (DukeException e) {
            Ui.showError(e.getMessage());
        }
    }

    /**
     * Starts polling IO.
     */
    public void start() {

        initialize();

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            getResponse(line);
        }
    }

    public static void main(String[] args) {
        Duke duke = new Duke();
        duke.start();
    }

}
