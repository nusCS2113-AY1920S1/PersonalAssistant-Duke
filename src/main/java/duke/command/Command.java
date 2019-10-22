package duke.command;

import duke.exception.DukeException;

import java.text.ParseException;
import java.util.ArrayList;

public abstract class Command<T, S, U, V> {
    protected String userInput;

    public abstract ArrayList<String> execute(T t, S s, U u, V v) throws DukeException, ParseException;

    public abstract boolean isExit();
}