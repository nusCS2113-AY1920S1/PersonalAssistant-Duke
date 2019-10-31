package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.models.locker.InUseLocker;
import duke.models.locker.Locker;
import duke.models.locker.LockerDate;
import duke.models.locker.Zone;
import duke.models.student.Student;
import duke.models.tag.Tag;
import duke.storage.FileHandling;
import duke.ui.Ui;

import java.util.List;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

public class AssignLockerCommand extends Command {

    private final Student student;
    private final LockerDate startDate;
    private final LockerDate endDate;
    private final List<Zone> preferences;
    public static final int FIRST_FREE_LOCKER = 0;

    /**
     * This constructor instantiates all the fields necessary for assigning a locker to a student.
     * @param student stores the information ond details of the student
     * @param startDate stores the starting date of the subscription
     * @param endDate stores the ending date of the subscription
     * @param preferences  stores the preferences as a list of zones for the student
     */
    public AssignLockerCommand(Student student, LockerDate startDate,
                               LockerDate endDate, List<Zone> preferences) {
        requireNonNull(student);
        requireNonNull(startDate);
        requireNonNull(endDate);
        requireNonNull(preferences);
        this.student = student;
        this.startDate = startDate;
        this.endDate = endDate;
        this.preferences = preferences;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, FileHandling storage) throws DukeException {
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
        freeLocker.setStatusAsInUse();
        Locker lockerAssignedToStudent = getLockerToAssign(freeLocker);

        lockerList.addLockerInPosition(lockerAssignedToStudent,storeIndex);
        return storeIndex;
    }

    private Locker getLockerToAssign(Locker freeLocker) {
        return new InUseLocker(freeLocker.getSerialNumber(),
                freeLocker.getAddress(),freeLocker.getZone(),freeLocker.getTag(),
                student, startDate, endDate);
    }

    private Predicate<Locker> findLockerBasedOnPreferences(Zone zone) throws DukeException {
        Tag checkTag = new Tag(Tag.NOT_IN_USE);
        return p -> p.getTag().equals(checkTag)
                && p.getZone().equals(zone);
    }

    private Predicate<Locker> findLockersInAnyZone() throws DukeException {
        Tag checkTag = new Tag(Tag.NOT_IN_USE);
        return p -> p.getTag().equals(checkTag);
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
        List<Locker> freeLockersInAnyZone = lockerList.getMatchingLockers(
                findLockersInAnyZone());

        if (freeLockersInAnyZone.size() == 0) {
            throw new DukeException(" There are no available lockers at the moment.");
        }
        //We need to inform the user that a locker has been assigned not in the preferred
        //location
        ui.showNoLockersFoundInPreferences();
        return freeLockersInAnyZone.get(FIRST_FREE_LOCKER);
    }
}
