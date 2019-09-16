package duke.command;

import duke.DukeContext;
import duke.exception.DukeException;

import java.time.LocalDateTime;

public class NewRecurringTaskCommand extends MultiArgCommand {
    public NewRecurringTaskCommand() {
        emptyArgMsg = "You didn't tell me anything about the recurring tasks!";
    }

    @Override
    public void parse(String inputStr) throws DukeException {
        arg = inputStr.strip();
        if (arg.length() == 0) {
            throw new DukeException(emptyArgMsg);
        }
    }

    @Override
    public void execute(DukeContext ctx) throws DukeException {
        super.execute(ctx);
        Parser recurParser = new Parser();
        NewTimedTaskCommand refCommand;
        try {
            refCommand = (NewTimedTaskCommand) recurParser.parse(argv[1]); //TODO find out which argv value it's
            //supposed to be
        } catch (ClassCastException excp) {
            throw new DukeException("Can't have that as a recurring task!");
        }
        refCommand.execute(ctx); //TODO use refCommand to construct the other commands
    }
}
