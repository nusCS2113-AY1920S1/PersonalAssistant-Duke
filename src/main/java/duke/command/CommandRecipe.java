package duke.command;

import duke.exception.DukeException;
import duke.list.recipelist.RecipeList;
import duke.storage.RecipeStorage;

import java.text.ParseException;
import java.util.ArrayList;

public abstract class CommandRecipe {

    protected String userInput;

    public abstract ArrayList<String> execute(RecipeList recipeList, RecipeStorage recipeStorage) throws DukeException, ParseException;

    public abstract boolean isExit();
}
