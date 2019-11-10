package duke.logic.commands;


import duke.models.LockerList;
import duke.storage.Storage;
import duke.ui.Ui;

/**
 * Command to list all the lockers managed by SpongeBob.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) {
        ui.printList(lockerList.getLockerList());
    }
}
