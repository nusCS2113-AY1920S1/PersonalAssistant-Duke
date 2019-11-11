package duke.logic.commands;

import duke.exceptions.DukeException;

import duke.models.LockerList;
import duke.models.locker.Locker;
import duke.models.locker.SerialNumber;

import duke.storage.Storage;

import duke.ui.Ui;

import static java.util.Objects.requireNonNull;

/**
 * Command to select a particular locker and access its information.
 */
public class SelectLockerCommand extends Command {

    private final SerialNumber serialNumberOfSelectedLocker;
    public static final String COMMAND_WORD = "selectlocker";
    public static final String INVALID_FORMAT = " Invalid command format for selecting a locker."
            + "\n     1. Should enter only selectLocker <SERIALNUMBER>.";

    public SelectLockerCommand(SerialNumber serialNumber) {
        requireNonNull(serialNumber);
        this.serialNumberOfSelectedLocker = serialNumber;
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {

        Locker selectedLocker = lockerList.getLockerToEdit(serialNumberOfSelectedLocker);
        String serialNumber = selectedLocker.serialNumberToString();
        String zone = selectedLocker.zoneToString();
        String area = selectedLocker.areaToString();
        String tag = selectedLocker.tagToString();
        String studentName = "-";
        String studentID = "-";
        String studentEmail = "-";
        String studentMajor = "-";
        String startDate = "-";
        String endDate = "-";
        if (tag.equalsIgnoreCase("in-use")) {
            studentName = selectedLocker.getUsage().get().getStudent().getName().getName();
            studentID = selectedLocker.getUsage().get().getStudent().getStudentId().getStudentId();
            studentEmail = selectedLocker.getUsage().get().getStudent().getEmail().getEmail();
            studentMajor = selectedLocker.getUsage().get().getStudent().getMajor().getCourse();
            startDate = selectedLocker.getUsage().get().getStartDate().getDate();
            endDate = selectedLocker.getUsage().get().getEndDate().getDate();
        }

        ui.selectMessage(serialNumber, tag, zone, area, studentName, studentID, studentEmail, studentMajor,
                 startDate, endDate);
    }
}
