package duke.orderCommand;

import duke.command.ExitCommand;
import duke.exception.DukeException;
import duke.order.OrderList;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Represents  an abstract order Command that could be an add, delete, exit, done, find or list.
 */
public abstract class OrderCommand {

    public abstract void execute(OrderList orderList, Ui ui, Storage storage) throws DukeException;

    /**
     * Returns the boolean indicating that it is( not) an {@link ExitCommand}.
     *
     * @return false by default
     */
    public boolean isExit() {
        return false;
    }

}


