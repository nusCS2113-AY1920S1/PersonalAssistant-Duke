package commands;
import Tasks.Task;
import UI.Ui;
import Storage.Storage;
import Exception.DukeException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class ByeCommand extends Command {
    /**
     * Returns void execute function for commands.ByeCommand.
     *
     * @param list An array list of type Tasks.Task.
     * @param ui Class ui
     * @param storage class storage
     * @param commandStack
     * @param deletedTask
     * @return Void.
     * @throws DukeException | ParseException | IOException
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask) throws DukeException, ParseException, IOException, NullPointerException {
        System.out.println("Bye! Hope to see you again soon!");
    }
    @Override
    public boolean isExit() {
        return true;
    }
}
