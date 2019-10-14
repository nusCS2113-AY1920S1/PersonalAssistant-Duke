package duke.logic.parser.order;

import duke.logic.command.order.DeleteOrderCommand;
import duke.logic.parser.commons.Parser;
import duke.logic.parser.exceptions.ParseException;

import java.util.HashSet;


public class DeleteOrderCommandParser implements Parser<DeleteOrderCommand> {
    private static final String MESSAGE_EMPTY_INDICES = "Indices cannot be empty.";

    @Override
    public DeleteOrderCommand parse(String args) throws ParseException {
        return new DeleteOrderCommand(new HashSet<>());
    }

}
