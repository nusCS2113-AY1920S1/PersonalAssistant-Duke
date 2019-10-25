package duke.logic.command;

import duke.exception.DukeException;
import duke.model.list.recipelist.RatingList;
import duke.storage.RatingStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

public abstract class CommandRating {
    protected String userInput;

    public abstract ArrayList<String> execute(RatingList ratingList, Ui ui, RatingStorage ratingStorage) throws DukeException, ParseException;

    public abstract boolean isExit();
}
