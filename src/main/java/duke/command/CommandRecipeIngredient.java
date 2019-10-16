package duke.command;

import duke.exception.DukeException;
import duke.list.recipelist.RecipeIngredientList;
import duke.storage.RecipeIngredientStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

public abstract class CommandRecipeIngredient {
    protected String userInput;

    public abstract ArrayList<String> execute(RecipeIngredientList recipeIngredientList, Ui ui, RecipeIngredientStorage recipeIngredientStorage) throws DukeException, ParseException;

    public abstract boolean isExit();
}
