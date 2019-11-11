package duke.logic.command;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Abstract class to represent command.
 *
 * @param <T> Type of list
 * @param <S> Type of ui
 * @param <U> Type of storage
 */
public abstract class Command<T, S, U> {
    protected String userInput;

    public abstract ArrayList<String> execute(T t, S s, U u) throws ParseException;
}