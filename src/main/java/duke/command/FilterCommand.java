package duke.command;

import duke.Duke;
import duke.exception.DukeException;
import duke.parser.CommandParams;


/**
 *
 */
public class FilterCommand extends Command {

    public FilterCommand() {
        super(null, null, null, null);
    }

    @Override
    public void execute(CommandParams commandParams, Duke duke) throws DukeException {
        String mainParam = commandParams.getMainParam();
        duke.expenseList.setFilterCriteria(mainParam);
    }

}
