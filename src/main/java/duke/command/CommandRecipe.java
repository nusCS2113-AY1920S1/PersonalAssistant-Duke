package duke.command;

import duke.exception.DukeException;
import duke.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.ui.Ui;

import java.util.ArrayList;

public abstract class CommandRecipe {
    protected String userInput;

    public abstract ArrayList<String> feedback(RecipeList recipeList, Ui ui, RecipeStorage recipeStorage) throws DukeException;

    public abstract boolean isExit();
}
