package duke.models.locker;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import duke.models.student.Student;
import duke.models.tag.Tag;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class InUseLocker extends Locker {
    private Student student;
    private LockerDate startDate;
    private LockerDate endDate;

    /**
     * This constructor instantiates a locker that is currently being used by a student.
     * @param serialNumber stores the serial number of the locker
     * @param address stores the location of the locker
     * @param zone stores the zone assigned to the locker
     * @param tag stores the status of the locker (in-use)
     * @param student stores the details associated with students
     * @param startDate stores the starting date of the locker subscription
     * @param endDate stores the ending date of the locker subscription
     */
    public InUseLocker(SerialNumber serialNumber, Address address, Zone zone, Tag tag,
                       Student student,LockerDate startDate,LockerDate endDate) {
        super(serialNumber,address,zone,tag);
        requireNonNull(student);
        requireNonNull(startDate);
        requireNonNull(endDate);
        this.student = student;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public InUseLocker() {

    }


    @JsonGetter("student")
    public Student getStudent() {
        return student;
    }

    @JsonSetter("student")
    public void setStudent(Student student) {
        this.student  = student;
    }

    @JsonGetter("startDate")
    public LockerDate getStartDate() {
        return startDate;
    }

    @JsonSetter("startDate")
    public void setStartDate(LockerDate startDate) {
        this.startDate = startDate;
    }

    @JsonGetter("endDate")
    public LockerDate getEndDate() {
        return endDate;
    }

    @JsonSetter("endDate")
    public void setEndDate(LockerDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean isPresent(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof InUseLocker)) {
            return false;
        }

        return this.getSerialNumber().equals(((InUseLocker) other).getSerialNumber());
    }

    @Override
    public String toString() {
        return super.toString() + "\n      " + "Name: " + student.getName().getName()
                + " StudentID:" + student.getMatricNumber().getStudentCourse();
    }

    /* We need to override functions equals() and hashCode() in order to account
       for user defined checks for equality while using streams
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; //both represent the same objects
        }

        if (!(other instanceof InUseLocker)) {
            return false; //accounts for all null cases and irrelevant references
        }

        InUseLocker otherLocker = (InUseLocker) other;
        return otherLocker.getStudent().equals(this.getStudent())
                && otherLocker.getStartDate().equals(this.getStartDate())
                && otherLocker.getEndDate().equals(this.getEndDate()); //all equality checks
    }

    @Override
    public int hashCode() {
        return Objects.hash(student,startDate,endDate);
    }

}
