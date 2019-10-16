package duke.command;

import duke.exception.DukeException;
import duke.list.recipelist.PrepStepList;
import duke.storage.PrepStepStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

public abstract class CommandPrepStep {
    protected String userInput;

    public abstract ArrayList<String> execute(PrepStepList prepStepList, Ui ui, PrepStepStorage ratingStorage) throws DukeException, ParseException;

    public abstract boolean isExit();
}
