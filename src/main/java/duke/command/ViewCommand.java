package duke.command;

import duke.Duke;
import duke.exception.DukeException;
import duke.parser.CommandParams;

public class ViewCommand extends Command {

    public ViewCommand() {
        super(null, null, null, null);
    }

    @Override
    public void execute(CommandParams commandParams, Duke duke) throws DukeException {
        String mainParam = commandParams.getMainParam();
        if(!commandParams.containsParams("p")) {
            duke.expenseList.setViewScope(mainParam, 0);
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
        duke.expenseList.setViewScope(mainParam, previous);
    }

}
