package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.locker.Locker;
import duke.models.LockerList;
import duke.storage.Storage;
import duke.ui.Ui;

import static java.util.Objects.requireNonNull;

/**
 * Command for adding a locker to SpongeBob.
 */
public class AddLockerCommand extends Command {

    private final Locker addLocker;
    public static final String COMMAND_WORD = "addlocker";
    public static final String INVALID_FORMAT = " Invalid command format for adding a locker."
            + "\n     1. All tokens should be present (s/ z/ a/) "
            + "\n     2. There should not include any text between the command word and the first token.";

    /**
     * Creates an AddLockerCommand to add {@code Locker}.
     */
    public AddLockerCommand(Locker addLocker) {
        requireNonNull(addLocker);
        this.addLocker = addLocker;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {

        if (lockerList.isPresentLocker(addLocker)) {
            throw new DukeException(LockerList.DUPLICATE_LOCKERS_FOUND);
        }
        lockerList.addLocker(addLocker);
        ui.printAddLocker(lockerList.numLockers(), addLocker.toString());
        storage.saveData(lockerList);
    }
}
