package Parser;

import Commands.Command;
import Interface.LookupTable;
import Interface.Storage;
import Interface.Ui;
import Tasks.TaskList;

public abstract class Parse {
    public abstract Command execute() throws Exception;
}
