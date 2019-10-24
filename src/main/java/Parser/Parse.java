package Parser;

import Commands.Command;
import Interface.LookupTable;
import Interface.Storage;
import Interface.Ui;
import Tasks.TaskList;

public abstract class Parse {
    public abstract Command execute(LookupTable LT, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception;
}
