package duke.recipeCommand;

import duke.exception.DukeException;
import duke.recipebook.DishList;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class DeleteDishCommand extends RecipeCommand {

    private int Nb;

    public DeleteDishCommand(int dishNb) {
        this.Nb = Nb;
    }

    @Override
    public void execute(DishList dish1, TaskList taskList, Ui ui, Storage storage) throws DukeException {
        try {
            System.out.println("deleted " + DishList.getDish(Nb).toString());
            dish1.deleteDish(Nb);
        } catch (Exception e) {
            throw new DukeException("dish does not exist");
        }
    }
}
