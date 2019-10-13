package duke.command;

import duke.dukeobject.ExpenseList;
import duke.exception.DukeException;
import duke.parser.CommandParams;
import duke.ui.Ui;

public class ViewCommand extends Command {

    public ViewCommand() {
        super(null, null, null, null);
    }

    @Override
    public void execute(CommandParams commandParams, ExpenseList expensesList, Ui ui) throws DukeException {
        String mainParam = commandParams.getMainParam();
        if(!commandParams.containsParams("p")) {
            expensesList.setViewScope(mainParam, 0);
            return;
        }

        int previous;
        try {
            previous = Integer.parseInt(commandParams.getParam("p"));
        } catch (NumberFormatException e) {
            throw new DukeException(String.format(
                    DukeException.MESSAGE_EXPENSE_VIEW_SCOPE_NUMBER_INVALID,
                    commandParams.getParam("p")));
        }
        expensesList.setViewScope(mainParam, previous);
    }

}
