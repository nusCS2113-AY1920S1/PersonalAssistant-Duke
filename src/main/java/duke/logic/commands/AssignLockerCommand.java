package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.models.locker.Usage;
import duke.models.locker.Locker;
import duke.models.locker.Zone;
import duke.models.tag.Tag;
import duke.storage.Storage;
import duke.ui.Ui;

import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public class AssignLockerCommand extends Command {

    private final Usage usage;
    private final List<Zone> preferences;
    public static final int FIRST_FREE_LOCKER = 0;

    /**
     * This constructor instantiates all the fields necessary for assigning a locker to a student.
     * @param usage stores all the information required for locker subscription
     * @param preferences  stores the preferences as a list of zones for the student
     */
    public AssignLockerCommand(Usage usage,List<Zone> preferences) {
        requireNonNull(usage);
        requireNonNull(preferences);
        this.usage = usage;
        this.preferences = preferences;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {
        requireNonNull(lockerList);
        requireNonNull(ui);
        requireNonNull(storage);
        int storeIndex = assignLockerToStudent(lockerList,ui);
        ui.printSuccessfulAllocation(lockerList.getLocker(storeIndex).toString());
        storage.saveData(lockerList);
    }

    private int assignLockerToStudent(LockerList lockerList, Ui ui) throws DukeException {

        Locker freeLocker = getFreeLocker(lockerList,ui);
        int storeIndex = lockerList.getIndexOfLocker(freeLocker);
        Locker lockerAssignedToStudent = getLockerToAssign(freeLocker);

        lockerList.setLockerInPosition(lockerAssignedToStudent,storeIndex);
        return storeIndex;
    }

    private Locker getLockerToAssign(Locker freeLocker) throws DukeException {
        return new Locker(freeLocker.getSerialNumber(),freeLocker.getAddress(),
                freeLocker.getZone(),new Tag(Tag.IN_USE),usage);
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
        //If the control reaches here, that means SpongeBob was unable to allocate
        //any Lockers in the given preferences and hence we will arbitrarily
        //assign any locker that is free
        List<Locker> freeLockersInAnyZone = lockerList.getAnyAvailableLocker(new Tag(Tag.NOT_IN_USE));
        if (freeLockersInAnyZone.size() == 0) {
            throw new DukeException(" There are no available lockers at the moment.");
        }
        //We need to inform the user that a locker has been assigned not in the preferred
        //location
        ui.showNoLockersFoundInPreferences();
        return freeLockersInAnyZone.get(FIRST_FREE_LOCKER);
    }
}
