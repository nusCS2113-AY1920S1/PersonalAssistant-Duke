package duke.logic.command.order;

import duke.commons.core.index.Index;
import duke.logic.command.CommandResult;
import duke.logic.command.exceptions.CommandException;
import duke.model.Model;

import java.util.Set;

import static java.util.Objects.requireNonNull;

/**
 * A command to delete orders from Order List.
 */
public class DeleteOrderCommand extends OrderCommand {
    public static final String COMMAND_WORD = "remove";
    private final Set<Index> indices;

    /**
     * Creates a {@code DeleteOrderCommand}.
     *
     * @param indices of the orders to delete
     */
    public DeleteOrderCommand(Set<Index> indices) {
        requireNonNull(indices);

        this.indices = indices;
    }


    public CommandResult execute(Model model) throws CommandException {
        return new CommandResult("");
    }

}
