import java.io.IOException;
import java.util.Scanner;

/**
 * Duke is a task list that supports 3 types of classes - Todos, deadlines and events.
 * Tasks can be added, marked as done, searched for, and deleted.
 * Tasks are loaded from and saved to file.
 */

public class Duke {
    private static Storage storage;
    private static Parser parser;
    private static TaskList taskList;
    private static Ui ui;

    //Consider not using the listIndex anymore?
    private static int listIndex = 0; //Starts from 0 now lol.

    private static void setListIndex(int value) {
        listIndex = value;
    }

    /*TODO Strip descriptions of leading/trailing spaces before submitting
        Accept multiple space delimiter to take the first word...?
    * */

    private Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        taskList = new TaskList(storage.load()); //Will always return the right object even if empty.
        parser = new Parser();
    }

    private void run() {
        ui.printIntro();

        String userInput;
        Command command;

        //Should probably wrap this in the UI class.
        do {
            ui.printNewLine();
            userInput = ui.read();
            command = parser.parse(userInput);
            command.execute(taskList, ui, storage);
            //taskList.handleListInput(command); //Should only be passed good inputs.
        } while (command.type != Command.CommandType.BYE);

    }

    public static void main(String[] args) {
        String saveFile = "/Users/rebecca/Documents/NUS/CS2113T/Project/duke/data/saved_tasks.txt";
        new Duke(saveFile).run();
    }
}
