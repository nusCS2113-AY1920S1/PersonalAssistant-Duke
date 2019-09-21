import Events.Storage.Storage;
import Events.Storage.TaskList;
import UserElements.Command;
import UserElements.Parser;
import UserElements.UI;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Duke {
    private static Parser parser = new Parser();
    private static UI ui = new UI();
    private static Storage storage = new Storage(new File("data/Duke.txt"));
    private static TaskList tasks = new TaskList(storage.readFromFile(ui));

    /**
     * main Duke method
     */
    public static void main(String[] args) throws IOException {
        setup();
        ui.welcome(tasks);

        String userInput = parser.readUserInput();
        while (!userInput.equals("bye")) {
            Command currCommand = parser.parseInput(userInput);
            currCommand.execute(tasks, ui, storage);
            userInput = parser.readUserInput();
        }

        ui.bye();
    }

    /**
     * instantiates all necessary classes to run duke program
     */
    private static void setup() {
        parser = new Parser();
        ui = new UI();
        storage = new Storage(new File("data/Duke.txt"));
        tasks = new TaskList(storage.readFromFile(ui));
    }
}