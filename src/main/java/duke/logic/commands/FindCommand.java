package duke.logic.commands;

import java.util.List;
import java.util.stream.Collectors;
import duke.models.LockerList;
import duke.models.locker.Address;
import duke.models.locker.Locker;
import duke.models.locker.SerialNumber;
import duke.models.locker.Zone;
import duke.models.student.Email;
import duke.models.student.Major;
import duke.models.student.StudentId;
import duke.models.student.Name;
import duke.models.tag.Tag;
import duke.storage.Storage;
import duke.ui.Ui;

public class FindCommand extends Command {
    private final FindLocker findLocker;
    private final FindStudent findStudent;

    public static final String COMMAND_WORD = "find";
    public static final String INVALID_FORMAT =  " Invalid command format for find command."
            + "\n     1. A least one token should be present (s/ a/ z/ c/ n/ i/ e/ m/ f/ t/ p/)"
            + "\n     2. There should not include any text between the command word and the first token"
            + "\n     3. Keywords to be searched for should have the exact number of characters.";

    /**
     * This constructor instantiates the FindCommand object.
     * @param findLocker stores the attribute of the locker to be found.
     * @param findStudent stores the details of the student for the locker to be found.
     */

    public FindCommand(FindLocker findLocker, FindStudent findStudent) {

        this.findLocker = new FindLocker(findLocker);
        this.findStudent = new FindStudent(findStudent);

    }

    @Override
    public void execute(LockerList lockerList, Ui ui, Storage storage) {

        List<Locker> containsMatchedLocker = lockerList.getLockerList().stream()
                .filter(s -> s.compare(this.findLocker, this.findStudent))
                .collect(Collectors.toList());

        ui.printFoundLockers(containsMatchedLocker);
    }


    public static class FindLocker {

        private SerialNumber serialNumber;
        private Address address;
        private Zone zone;
        private Tag tag;

        public FindLocker() {
        }

        /**
         * A copy constructor used to copy the contents of the locker to be found.
         */
        public FindLocker(FindLocker findLocker) {
            setSerialNumber(findLocker.serialNumber);
            setAddress(findLocker.address);
            setZone(findLocker.zone);
            setTag(findLocker.tag);

        }

        public void setSerialNumber(SerialNumber serialNumber) {
            this.serialNumber = serialNumber;
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public void setZone(Zone zone) {
            this.zone = zone;
        }

        public void setTag(Tag tag) {
            this.tag = tag;
        }

        /**
         * This function checks that there is at least one field is filled.
         */
        public boolean missingFields() {
            return (serialNumber != null || address != null
                    || zone != null || tag != null);

        }

        public SerialNumber getSerialNumber() {
            return serialNumber;
        }

        public Address getAddress() {
            return address;
        }

        public Zone getZone() {
            return zone;
        }

        public Tag getTag() {
            return tag;
        }
    }

    public static class FindStudent {
        private Name name;
        private StudentId studentId;
        private Email email;
        private Major major;

        public FindStudent() {
        }

        /**
         * A copy constructor used to copy the details of a student.
         */
        public FindStudent(FindStudent findStudent) {

            setName(findStudent.name);
            setMatricNumber(findStudent.studentId);
            setEmail(findStudent.email);
            setMajor(findStudent.major);

        }

        public void setName(Name name) {
            this.name = name;
        }

        public void setMatricNumber(StudentId studentId) {
            this.studentId = studentId;
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public void setMajor(Major major) {
            this.major = major;
        }

        /**
         * This function checks that there is at least one field is filled.
         */
        public boolean missingFields() {
            return (name != null || studentId != null
                    || email != null || major != null);

        }

        public Name getName() {
            return name;
        }

        public StudentId getStudentID() {
            return studentId;
        }

        public Email getEmail() {
            return email;
        }

        public Major getMajor() {
            return major;
        }

    }
}

