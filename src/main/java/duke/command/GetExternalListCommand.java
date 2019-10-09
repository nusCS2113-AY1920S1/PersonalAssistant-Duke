package duke.command;

import duke.dukeobject.ExpenseList;
import duke.exception.DukeException;
import duke.parser.CommandParams;
import duke.ui.Ui;

public class GetExternalListCommand extends Command {

    private String commandName;
    private String mainParam;

    public GetExternalListCommand() {
        super(null, null, null, null);
    }

    @Override
    public void execute(CommandParams commandParams, ExpenseList expensesList, Ui ui) throws DukeException {

        commandName = commandParams.getCommandName();
        mainParam = commandParams.getMainParam();

        switch(commandName) {
            case "sort":
                expensesList.setSortCriteria(mainParam);
                break;

            case "view":
                expensesList.setViewScope(mainParam);
                break;

            case "filter":
                expensesList.setFilterCriteria(mainParam);
                break;
        }
    }
}
