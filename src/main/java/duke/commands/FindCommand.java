package duke.commands;
import duke.TaskList;
import duke.Ui;
import duke.Storage;
/**
 * Command objects for searching for tasks by name.
 */
public class FindCommand extends Command {
    private String search;

    public FindCommand(CommandType type, String search) {
        super(type);
        this.search = search;
    }

    @Override
    public void execute(TaskList list, Ui ui, Storage storage) {
        list.searchForTask(search);
    }

}

