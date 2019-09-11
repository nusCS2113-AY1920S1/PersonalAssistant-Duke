import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Object;
import java.util.EnumSet;

public class Duke {
    static String lineSeparation = "____________________________________________________________\n";
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