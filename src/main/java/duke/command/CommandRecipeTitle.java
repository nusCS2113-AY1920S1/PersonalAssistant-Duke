package duke.command;

import duke.exception.DukeException;
import duke.list.recipelist.RecipeIngredientList;
import duke.list.recipelist.RecipeTitleList;
import duke.storage.RecipeIngredientStorage;
import duke.storage.RecipeTitleStorage;
import duke.task.recipetasks.Feedback;
import duke.task.recipetasks.Rating;
import duke.task.recipetasks.RecipeIngredient;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

public abstract class CommandRecipeTitle {
    protected String userInput;

    public abstract ArrayList<String> execute(RecipeTitleList recipeTitleList, Ui ui, RecipeTitleStorage recipeTitleStorage) throws DukeException, ParseException;

    public abstract ArrayList<String> execute(RecipeIngredientList recipeIngredientList, Ui ui, RecipeIngredientStorage recipeIngredientStorage) throws DukeException, ParseException;

    public abstract boolean isExit();
}
