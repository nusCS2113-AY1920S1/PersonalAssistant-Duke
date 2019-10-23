package duke.command.ingredientCommand;

import duke.command.Cmd;
import duke.exception.DukeException;
import duke.ingredient.Ingredient;
import duke.ingredient.IngredientsList;
import duke.list.GenericList;
import duke.storage.Printable;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.util.List;

/**
 * Represents a Command to add a specific {@link Task} in the {@link TaskList}.
 */
public class AddCommand<T> extends Cmd<T> {

    private T entry;

    /**
     * The constructor method for AddCommand.
     *
     * @param entry : the {@link Ingredient} to be added in the list
     */
    public AddCommand(T entry) {
        this.entry = entry;
    }

    /**
     * Public method used to add the task in the taskList, and write it on the hard disc.
     *
     *
     * @param list the {@link IngredientsList} to be expanded
     * @param ui       {@link Ui} used for printing the task output
     * @param storage  {@link Storage} writes in the file on the hard disc
     * @throws DukeException Error while adding the command to the duke.txt file
     */
    @Override
    public void execute(GenericList<T> list, Ui ui, Storage storage) throws DukeException {
        list.addEntry(entry);
        ui.showAddCommand(entry.toString(), list.size());
        try {
            storage.addInFile(((Printable)entry).printInFile());
        } catch (IOException e) {
            throw new DukeException("Error while adding the command to the duke.txt file");
        }
    }


}
