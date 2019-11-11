package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.log.Log;
import duke.models.LockerList;
import duke.models.locker.Usage;
import duke.models.locker.Locker;
import duke.models.locker.Zone;
import duke.models.tag.Tag;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * Command to assign locker to a student based on preferences.
 */
public class AssignLockerCommand extends Command {

    private static final String LOG_FOR_ASSIGN_LOCKER = " Executing command for assigning locker to the student";
    private static final int FIRST_FREE_LOCKER = 0;
    public static final String COMMAND_WORD = "assign";
    public static final String INVALID_FORMAT =  " Invalid command format for assigning lockers."
            + "\n     1. All tokens should be present (n/ i/ m/ e/ f/ t/ p/)"
            + "\n     2.There should not include any text between the command word and the first token";
    private static final String NO_AVAILABLE_LOCKERS = " There are no available lockers at the moment.";
    private static final Logger logger = Log.getLogger();
    private final Usage usage;
    private final List<Zone> preferences;

    /**
     * Instantiates all the fields necessary for assigning a locker to a student.
     * @param usage stores all the information required for locker subscription
     * @param preferences  stores the preferences as a list of zones for the student
     */
    public AssignLockerCommand(Usage usage, List<Zone> preferences) {
        requireNonNull(usage);
        requireNonNull(preferences);
        this.usage = usage;
        this.preferences = preferences;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {
        logger.log(Level.INFO, LOG_FOR_ASSIGN_LOCKER);
        int storeIndex = assignLockerToStudent(lockerList, ui);
        ui.printSuccessfulAllocation(lockerList.getLocker(storeIndex).toString());
        storage.saveData(lockerList);
        storage.updateStateList(lockerList);
    }

    private int assignLockerToStudent(LockerList lockerList, Ui ui) throws DukeException {

        Locker freeLocker = getFreeLocker(lockerList, ui);
        int storeIndex = lockerList.getIndexOfLocker(freeLocker);
        Locker lockerAssignedToStudent = getLockerToAssign(freeLocker);

        lockerList.setLockerInPosition(lockerAssignedToStudent, storeIndex);
        return storeIndex;
    }

    private Locker getLockerToAssign(Locker freeLocker) throws DukeException {
        return new Locker(freeLocker.getSerialNumber(), freeLocker.getAddress(),
                freeLocker.getZone(), new Tag(Tag.IN_USE), usage);
    }

    private Predicate<Locker> findLockerBasedOnPreferences(Zone zone) throws DukeException {
        Tag checkTag = new Tag(Tag.NOT_IN_USE);
        return p -> p.getTag().equals(checkTag)
                && p.getZone().equals(zone);
    }

    private Locker getFreeLocker(LockerList lockerList, Ui ui) throws DukeException {
        for (Zone zone: preferences) {
            List<Locker> freeLockersInZone = lockerList.getMatchingLockers(
                    findLockerBasedOnPreferences(zone));
            if (freeLockersInZone.size() > 0) {
                return freeLockersInZone.get(FIRST_FREE_LOCKER);
            }
        }
        /* If the control reaches here, that means SpongeBob was unable to allocate
          any Lockers in the given preferences and hence we will arbitrarily
          assign any locker that is free */
        List<Locker> freeLockersInAnyZone = lockerList.getAnyAvailableLocker(new Tag(Tag.NOT_IN_USE));
        if (freeLockersInAnyZone.size() == 0) {
            throw new DukeException(NO_AVAILABLE_LOCKERS);
        }
        /*Need to inform the user that a locker has been assigned not in the preferred
          location */
        ui.showNoLockersFoundInPreferences();
        return freeLockersInAnyZone.get(FIRST_FREE_LOCKER);
    }
}
