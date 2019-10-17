package duke.command;

import duke.exception.DukeException;

import java.text.ParseException;
import java.util.ArrayList;

public abstract class Command<T, S, U> {
    protected String userInput;

    public abstract ArrayList<String> execute(T t, S s, U u) throws DukeException, ParseException;

    public abstract boolean isExit();
}