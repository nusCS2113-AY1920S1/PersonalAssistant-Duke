package  duke;

import duke.command.Command;
import duke.exception.DukeException;
import duke.parser.Parser;
import duke.recipeCommand.RecipeCommand;
import duke.recipebook.DishList;
import duke.storage.Storage;
import duke.task.TaskList;
import duke.ui.Ui;

/**
 * MAIN CLASS DUKE, start from main function.
 */
public class Duke {

    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private DishList dish;

    /**
     * The constructor method for Duke.
     * @param filePath used to specify the location of the file in the hard disc.
     */
    public Duke(String filePath) {
        dish = new DishList();
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (DukeException e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * The execution core of the Duke class.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand, tasks.size());
//                RecipeCommand c = Parser.Parse(fullCommand); //execute the recipeCommands, add dishes etc
                c.execute(dish, tasks, ui, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * =============== MAIN FUNCTION ===============.
     */
    public static void main(String[] args) {
        new Duke("data/tasks.txt").run();
    }
}
