package duke.models.locker;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import duke.models.student.Student;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Usage {
    private  Student student;
    private  LockerDate startDate;
    private  LockerDate endDate;

    /**
     * This constructor instantiates a locker that is currently being used by a student.
     * @param student stores the details associated with students
     * @param startDate stores the starting date of the locker subscription
     * @param endDate stores the ending date of the locker subscription
     */
    @JsonCreator
    public Usage(@JsonProperty("student") Student student,
                 @JsonProperty("startDate") LockerDate startDate,
                 @JsonProperty("endDate") LockerDate endDate) {
        requireNonNull(student);
        requireNonNull(startDate);
        requireNonNull(endDate);
        this.student = student;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @JsonGetter("student")
    public Student getStudent() {
        return student;
    }


    @JsonGetter("startDate")
    public LockerDate getStartDate() {
        return startDate;
    }


    @JsonGetter("endDate")
    public LockerDate getEndDate() {
        return endDate;
    }

    @Override
    public String toString() {
        return super.toString() + "\n      " + "Name: " + student.getName().getName()
                + " StudentID:" + student.getMatricNumber().getStudentId();
    }

    /* We need to override functions equals() and hashCode() in order to account
       for user defined checks for equality while using streams
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true; //both represent the same objects
        }

        if (!(other instanceof Usage)) {
            return false; //accounts for all null cases and irrelevant references
        }

        Usage otherLocker = (Usage) other;
        return otherLocker.getStudent().equals(this.getStudent())
                && otherLocker.getStartDate().equals(this.getStartDate())
                && otherLocker.getEndDate().equals(this.getEndDate()); //all equality checks
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, startDate, endDate);
    }

}
