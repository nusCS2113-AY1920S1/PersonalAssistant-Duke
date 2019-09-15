import Events.Storage.Storage;
import Events.Storage.TaskList;
import UserElements.Command;
import UserElements.Parser;
import UserElements.UI;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Duke {
    static String lineSeparation = "____________________________________________________________\n";

    /**
     * main Duke method
     */
    public static void main(String[] args) throws IOException {
        Parser parser = new Parser();
        Scanner reader = new Scanner(System.in);
        UI ui = new UI();
        Storage storage = new Storage(new File("D:/duke/data/Duke.txt"));
        TaskList tasks = new TaskList(storage.readFromFile(ui));
        ui.welcome();

        String userInput = reader.nextLine();
        while (!userInput.equals("bye")) {
            Command currCommand = parser.parseInput(userInput);
            currCommand.execute(tasks, ui, storage);
            userInput = reader.nextLine();
        }

        ui.bye();
    }
}