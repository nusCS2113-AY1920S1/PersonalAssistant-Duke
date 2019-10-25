package duke.logic.command;

import duke.exception.DukeException;
import duke.model.list.recipelist.FeedbackList;
import duke.storage.FeedbackStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

public abstract class CommandFeedback {
    protected String userInput;

    public abstract ArrayList<String> execute(FeedbackList feedbackList, Ui ui, FeedbackStorage feedbackStorage) throws DukeException, ParseException;

    public abstract boolean isExit();
}
