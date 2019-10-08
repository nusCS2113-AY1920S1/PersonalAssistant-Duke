package commands;
import Tasks.Task;
import UI.Ui;
import Storage.Storage;
import Exception.DukeException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * A base abstract class of all the commands.
 */
public abstract class Command {

    public abstract void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask) throws DukeException, ParseException, IOException, NullPointerException;
    public abstract boolean isExit();
}

