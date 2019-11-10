package duke.models.student;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Stores all the information pertaining to a student renting a locker.
 */
public class Student {
    private final Name name;
    private final StudentId studentId;
    private final Email email;
    private final Major major;

    /**
     * Instantiates the student and all its properties.
     * @param name stores the name of the student
     * @param studentId stores the student ID of the student
     * @param email stores the email id of the student
     * @param major stores the major/course of the student
     */
    @JsonCreator
    public Student(@JsonProperty("studentName") Name name,
                   @JsonProperty("studentId") StudentId studentId,
                   @JsonProperty("studentEmail") Email email,
                   @JsonProperty("studentMajor") Major major) {
        requireNonNull(name);
        requireNonNull(studentId);
        requireNonNull(email);
        requireNonNull(major);
        this.name = name;
        this.studentId = studentId;
        this.email = email;
        this.major = major;
    }

    @JsonGetter("studentName")
    public Name getName() {
        return name;
    }

    @JsonGetter("studentId")
    public StudentId getStudentId() {
        return studentId;
    }

    @JsonGetter("studentEmail")
    public Email getEmail() {
        return email;
    }

    @JsonGetter("studentMajor")
    public Major getMajor() {
        return major;
    }


    /*Need to override function equals() and hashCode() in order to account
      for user defined checks for equality while using streams
    */
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true; //both the objects are the same
        }

        if (!(other instanceof Student)) {
            return false; //checks for all null cases and irrelevant instances
        }

        Student otherStudent = (Student) other;
        return (this.getName().equals(otherStudent.getName())
                && this.getEmail().equals(otherStudent.getEmail())
                && this.getStudentId().equals(otherStudent.getStudentId())
                && this.getMajor().equals(otherStudent.getMajor())); //checks for equality
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, studentId, email, major);
    }
}
