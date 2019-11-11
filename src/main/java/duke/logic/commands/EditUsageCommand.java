package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.log.Log;
import duke.models.LockerList;
import duke.models.locker.Usage;
import duke.models.locker.Locker;
import duke.models.locker.LockerDate;
import duke.models.locker.SerialNumber;
import duke.models.student.Email;
import duke.models.student.Major;
import duke.models.student.StudentId;
import duke.models.student.Name;
import duke.models.student.Student;
import duke.storage.Storage;
import duke.ui.Ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * Command to edit the subscription details of an in-use locker.
 */
public class EditUsageCommand extends Command {

    private static final String LOG_FOR_EDITING_USAGE = " Executing command for editing subscription "
            + "details of lockers";
    private final SerialNumber serialNumberToEdit;
    private final EditStudent editStudent;
    private final EditLockerDate editDate;

    public static final String COMMAND_WORD = "editusage";
    public static final String INVALID_FORMAT = " Invalid format for updating usage. "
            + "\n     1. The serial number of the locker whose usage is to be updated must be entered."
            + "\n     2. At least one field must be provided while updating usage.";
    private static final String EDIT_USAGE_CONSTRAINT = " You are allowed to edit usage of "
            + "only type In-Use Locker";
    private static final Logger logger = Log.getLogger();
    private static final String DATE_FORMAT = "dd-MM-uuuu";

    /**
     * Instantiates the edit usage command.
     * @param serialNumber stores the serial number of the locker to edit
     * @param editStudent stores the details of the student to be edited
     * @param editDate stores the details of the dates to be edited for usage
     */
    public EditUsageCommand(SerialNumber serialNumber,EditStudent editStudent,
                            EditLockerDate editDate) {
        requireNonNull(serialNumber);
        requireNonNull(editStudent);
        requireNonNull(editDate);
        this.serialNumberToEdit = serialNumber;
        this.editStudent = new EditStudent(editStudent);
        this.editDate = new EditLockerDate(editDate);
    }

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) throws DukeException {
        logger.log(Level.INFO, LOG_FOR_EDITING_USAGE);
        Locker editedLocker = editUsageDetails(lockerList);
        ui.showSuccessfullyEdited(editedLocker.toString());
        storage.saveData(lockerList);
        storage.updateStateList(lockerList);
    }

    private Locker editUsageDetails(LockerList lockerList) throws DukeException {
        Locker lockerToEdit = lockerList.getLockerToEdit(serialNumberToEdit);
        int storeIndex = lockerList.getIndexOfLocker(lockerToEdit);
        if (!lockerToEdit.isOfTypeInUse()) {
            throw new DukeException(EDIT_USAGE_CONSTRAINT);
        }
        Locker editedLocker = getEditedLocker(lockerToEdit);
        lockerList.setLockerInPosition(editedLocker, storeIndex);
        return editedLocker;
    }

    private Locker getEditedLocker(Locker lockerToEdit) throws DukeException {
        assert lockerToEdit.getUsage().isPresent();
        Usage usageToEdit = lockerToEdit.getUsage().get();
        Student editedStudent = createEditedStudent(usageToEdit, editStudent);
        LockerDate editedStartDate = createEditedStartDate(usageToEdit, editDate);
        LockerDate editedEndDate = createEditedEndDate(usageToEdit, editDate);
        Usage editedUsage = new Usage(editedStudent,editedStartDate, editedEndDate);
        if (!areDatesValid(editedStartDate.getDate(), editedEndDate.getDate())) {
            throw new DukeException(LockerDate.ERROR_IN_DATE_DIFFERENCE);
        }
        return new Locker(lockerToEdit.getSerialNumber(),
                lockerToEdit.getAddress(),lockerToEdit.getZone(),
                lockerToEdit.getTag(),editedUsage);
    }

    /**
     * Returns true if the dates are valid as per the rules of subscription of lockers
     * in SpongeBob.
     */
    private boolean areDatesValid(String startDate, String endDate) {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter checkDateFormat =
                DateTimeFormatter.ofPattern(DATE_FORMAT).withResolverStyle(ResolverStyle.STRICT);
        return LockerDate.isDifferenceBetweenDatesValid(startDate, endDate)
                && !LockerDate.isEndDateBeforeCurrentDate(endDate, checkDateFormat.format(currentDate));

    }

    /**
     * Creates and returns a {@code Student} with the details of {@code usageToEdit}
     * edited with {@code editStudent}.
     */
    private Student createEditedStudent(Usage usageToEdit, EditStudent editStudent) {
        assert usageToEdit != null;
        Name editedName = editStudent.getName()
                .orElse(usageToEdit.getStudent().getName());
        Major editedMajor = editStudent.getMajor()
                .orElse(usageToEdit.getStudent().getMajor());
        Email editedEmail = editStudent.getEmail()
                .orElse(usageToEdit.getStudent().getEmail());
        StudentId editedStudentId = editStudent.getStudentId()
                .orElse(usageToEdit.getStudent().getStudentId());

        return new Student(editedName, editedStudentId, editedEmail, editedMajor);
    }

    /**
     * Creates and returns a {@code LockerDate} with the details of {@code usageToEdit}
     * edited with {@code editLockerDate}.
     */
    private LockerDate createEditedStartDate(Usage usageToEdit, EditLockerDate editDate) throws DukeException {
        assert usageToEdit != null;
        return new LockerDate((editDate.getStartDate()
                .orElse(usageToEdit.getStartDate()).getDate()));
    }

    private LockerDate createEditedEndDate(Usage usageToEdit, EditLockerDate editDate) throws DukeException {
        assert usageToEdit != null;
        return new LockerDate((editDate.getEndDate()
                .orElse(usageToEdit.getEndDate())).getDate());
    }

    /**
     * Stores the details to edit the student with. Each non-empty field value will replace the
     * corresponding field value of the student.
     */
    public static class EditStudent {
        private Name name;
        private Email email;
        private StudentId studentId;
        private Major major;

        public EditStudent() {

        }

        /**
         * A copy constructor used for editing student details.
         * @param copyStudent stores the fields that are to be edited
         */
        public EditStudent(EditStudent copyStudent) {
            setName(copyStudent.name);
            setStudentId(copyStudent.studentId);
            setEmail(copyStudent.email);
            setMajor(copyStudent.major);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public void setStudentId(StudentId studentId) {
            this.studentId = studentId;
        }

        public void setMajor(Major major) {
            this.major = major;
        }

        /**
         * Returns true if at least one field is updated.
         */
        public boolean checkAnyFieldUpdated() {
            return name != null || email != null || major != null
                    || studentId != null;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public Optional<StudentId> getStudentId() {
            return Optional.ofNullable(studentId);
        }

        public Optional<Major> getMajor() {
            return Optional.ofNullable(major);
        }
    }

    /**
     * Stores the details to edit the rental period with. Each non-empty field value will replace the
     * corresponding field value of the rental period.
     */
    public static class EditLockerDate {
        private LockerDate startDate;
        private LockerDate endDate;

        public EditLockerDate() {

        }

        /**
         * A copy constructor to store the details of the edited usage.
         * @param copyEditDate stores the details that are to be edited
         */
        public EditLockerDate(EditLockerDate copyEditDate) {
            setStartDate(copyEditDate.startDate);
            setEndDate(copyEditDate.endDate);
        }

        /**
         * Returns true if at least one field is updated.
         */
        public boolean checkAnyFieldUpdated() {
            return startDate != null || endDate != null;
        }

        public void setStartDate(LockerDate startDate) {
            this.startDate = startDate;
        }

        public void setEndDate(LockerDate endDate) {
            this.endDate = endDate;
        }

        public Optional<LockerDate> getStartDate() {
            return Optional.ofNullable(startDate);
        }

        public Optional<LockerDate> getEndDate() {
            return Optional.ofNullable(endDate);
        }
    }
}

