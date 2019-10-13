package duke.command;

import duke.dukeobject.ExpenseList;
import duke.exception.DukeException;
import duke.parser.CommandParams;
import duke.ui.Ui;


public class FilterCommand extends Command {

    public FilterCommand() {
        super(null, null, null, null);
    }

    @Override
    public void execute(CommandParams commandParams, ExpenseList expensesList, Ui ui) throws DukeException {
        String mainParam = commandParams.getMainParam();
        expensesList.setFilterCriteria(mainParam);
    }

}
