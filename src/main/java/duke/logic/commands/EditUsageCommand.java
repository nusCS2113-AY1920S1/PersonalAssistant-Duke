package duke.logic.commands;

import duke.exceptions.DukeException;
import duke.models.LockerList;
import duke.models.locker.InUseLocker;
import duke.models.locker.Locker;
import duke.models.locker.LockerDate;
import duke.models.locker.SerialNumber;
import duke.models.student.Email;
import duke.models.student.Major;
import duke.models.student.MatricNumber;
import duke.models.student.Name;
import duke.models.student.Student;
import duke.models.tag.Tag;
import duke.parser.ParserCheck;
import duke.storage.FileHandling;
import duke.ui.Ui;

import java.util.Optional;

import static java.util.Objects.requireNonNull;

public class EditUsageCommand extends Command {

    private final SerialNumber serialNumberToEdit;
    private final EditStudent editStudent;
    private final EditLockerDate editDate;

    /**
     * This constructor instantiates the edit usage command.
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
    public void execute(LockerList lockerList, Ui ui, FileHandling storage) throws DukeException {
        Locker editedLocker = editUsageDetails(lockerList);
        ui.showSuccessfullyEdited(editedLocker.toString());
        storage.saveData(lockerList);
    }

    private Locker editUsageDetails(LockerList lockerList) throws DukeException {
        Locker lockerToEdit = CommandCheck.getLockerToEdit(lockerList,
                serialNumberToEdit);
        int storeIndex = lockerList.getIndexOfLocker(lockerToEdit);
        Tag tag = new Tag(Tag.IN_USE);
        if (!lockerToEdit.getTag().equals(tag)) {
            throw new DukeException(" You are allowed to edit usage of only type In-Use Locker");
        }
        Locker editedLocker = getEditedLocker((InUseLocker)lockerToEdit);
        lockerList.addLockerInPosition(editedLocker,storeIndex);
        return editedLocker;

    }

    private Locker getEditedLocker(InUseLocker lockerToEdit) throws DukeException {
        Student editedStudent = createEditedStudent(lockerToEdit,editStudent);
        LockerDate editedStartDate = createEditedStartDate(lockerToEdit,editDate);
        LockerDate editedEndDate = createEditedEndDate(lockerToEdit,editDate);
        ParserCheck.parseDifferenceBetweenStartAndEndDate(editedStartDate,editedEndDate);
        return new InUseLocker(lockerToEdit.getSerialNumber(),
                lockerToEdit.getAddress(),lockerToEdit.getZone(),
                lockerToEdit.getTag(),editedStudent,editedStartDate,editedEndDate);
    }

    private Student createEditedStudent(InUseLocker lockerToEdit,EditStudent editStudent) {
        assert lockerToEdit != null;
        Name editedName = editStudent.getName()
                .orElse(lockerToEdit.getStudent().getName());
        Major editedMajor = editStudent.getMajor()
                .orElse(lockerToEdit.getStudent().getMajor());
        Email editedEmail = editStudent.getEmail()
                .orElse(lockerToEdit.getStudent().getEmail());
        MatricNumber editedMatricNumber = editStudent.getMatricNumber()
                .orElse(lockerToEdit.getStudent().getMatricNumber());

        return new Student(editedName,editedMatricNumber,editedEmail,editedMajor);
    }

    private LockerDate createEditedStartDate(InUseLocker lockerToEdit, EditLockerDate editDate) throws DukeException {
        assert lockerToEdit != null;
        return new LockerDate((editDate.getStartDate()
                .orElse(lockerToEdit.getStartDate())).getDate());
    }

    private LockerDate createEditedEndDate(InUseLocker lockerToEdit, EditLockerDate editDate) throws DukeException {
        assert lockerToEdit != null;
        return new LockerDate((editDate.getEndDate()
                .orElse(lockerToEdit.getEndDate())).getDate());
    }


    public static class EditStudent {
        private Name name;
        private Email email;
        private MatricNumber matricNumber;
        private Major major;

        public EditStudent() {

        }

        /**
         * This is a copy constructor used for editing student details.
         * @param copyStudent stores the fields that are to be edited
         */
        public EditStudent(EditStudent copyStudent) {
            setName(copyStudent.name);
            setMatricNumber(copyStudent.matricNumber);
            setEmail(copyStudent.email);
            setMajor(copyStudent.major);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public void setMatricNumber(MatricNumber matricNumber) {
            this.matricNumber = matricNumber;
        }

        public void setMajor(Major major) {
            this.major = major;
        }

        public boolean checkAnyFieldUpdated() {
            return name != null || email != null || major != null
                    || matricNumber != null;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public Optional<MatricNumber> getMatricNumber() {
            return Optional.ofNullable(matricNumber);
        }

        public Optional<Major> getMajor() {
            return Optional.ofNullable(major);
        }
    }

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

