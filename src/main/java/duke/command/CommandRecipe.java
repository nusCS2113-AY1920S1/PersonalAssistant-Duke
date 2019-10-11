package duke.command;

import duke.exception.DukeException;
import duke.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;
import duke.task.recipetasks.Feedback;
import duke.task.recipetasks.Rating;
import duke.task.recipetasks.RecipeIngredient;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

public abstract class CommandRecipe {
    protected String userInput;

    public abstract ArrayList<String> feedback(RecipeList recipeList, RecipeIngredient recipeIngredient,
                                               Rating rating, Feedback feedback, Ui ui, RecipeStorage recipeStorage) throws DukeException, ParseException;

    public abstract boolean isExit();
}
