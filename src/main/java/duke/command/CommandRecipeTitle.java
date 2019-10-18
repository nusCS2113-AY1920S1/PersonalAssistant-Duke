package duke.command;

import duke.exception.DukeException;
import duke.list.recipelist.RecipeTitleList;
import duke.storage.RecipeTitleStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

public abstract class CommandRecipeTitle {
    protected String userInput;

    public abstract ArrayList<String> execute(RecipeTitleList recipeTitleList, Ui ui, RecipeTitleStorage recipeTitleStorage) throws DukeException, ParseException;

    public abstract boolean isExit();
}
