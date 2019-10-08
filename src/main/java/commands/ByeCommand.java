package commands;
import Tasks.Task;
import UI.Ui;
import Storage.Storage;
import exception.DukeException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

public class ByeCommand extends Command {
    /**
     * Returns void execute function for commands.ByeCommand.
     *
     * @param list An array list of type Tasks.Task.
     * @param ui Class ui
     * @param storage class storage
     * @return Void.
     * @throws DukeException | ParseException | IOException
     */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws ParseException, IOException, NullPointerException {
        System.out.println("Bye! Hope to see you again soon!");
    }
    @Override
    public boolean isExit() {
        return true;
    }
}
