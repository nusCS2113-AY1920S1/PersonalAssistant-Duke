package mistermusik;

import mistermusik.logic.EventList;
import mistermusik.storage.Instruments.InstrumentList;
import mistermusik.storage.Storage;
import mistermusik.logic.Command;
import mistermusik.ui.Parser;
import mistermusik.ui.UI;

import java.io.File;
import java.io.IOException;

public class Main {
    private static Parser parser = new Parser();
    private static UI ui = new UI();
    private static Storage storage;
    private static EventList tasks;
    private static InstrumentList instruments = new InstrumentList();

    /**
     * main mistermusik.Main method
     */
    public static void main(String[] args) throws IOException {
        setup();
        ui.welcome();

        String userInput = parser.readUserInput().toLowerCase();
        while (!userInput.equals("bye")) {
            Command currCommand = parser.parseInput(userInput);
            currCommand.execute(tasks, ui, storage, instruments);
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
        storage = new Storage(new File("data/mistermusik.Main.txt"));
        tasks = new EventList(storage.readFromFile(ui));
    }
}
