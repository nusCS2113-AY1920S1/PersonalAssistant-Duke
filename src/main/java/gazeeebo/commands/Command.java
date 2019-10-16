package gazeeebo.commands;
import gazeeebo.Tasks.Task;
import gazeeebo.UI.Ui;
import gazeeebo.Storage.Storage;
import gazeeebo.Exception.DukeException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

/**
 * A base abstract class of all the gazeeebo.commands.
 */
public abstract class Command {

    public abstract void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask) throws DukeException, ParseException, IOException, NullPointerException;
    public abstract boolean isExit();
}

