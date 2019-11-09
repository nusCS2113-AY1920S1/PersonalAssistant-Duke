package duke.models.student;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Student {
    private Name name;
    private MatricNumber matricNumber;
    private Email email;
    private Major major;

    /**
     * This constructor instantiates the student and all its properties.
     * @param name stores the name of the student
     * @param matricNumber stores the student ID/ matric number of the student
     * @param email stores the email id of the student
     * @param major stores the major/course of the student
     */
    @JsonCreator
    public Student(@JsonProperty("studentName") Name name,
                   @JsonProperty("studentId") MatricNumber matricNumber,
                   @JsonProperty("studentEmail") Email email,
                   @JsonProperty("studentMajor") Major major) {
        requireNonNull(name);
        requireNonNull(matricNumber);
        requireNonNull(email);
        requireNonNull(major);
        this.name = name;
        this.matricNumber = matricNumber;
        this.email = email;
        this.major = major;
    }

    @JsonGetter("studentName")
    public Name getName() {
        return name;
    }

    @JsonGetter("studentId")
    public MatricNumber getMatricNumber() {
        return matricNumber;
    }

    @JsonGetter("studentEmail")
    public Email getEmail() {
        return email;
    }

    @JsonGetter("studentMajor")
    public Major getMajor() {
        return major;
    }


    /* We need to override function equals() and hashCode() in order to account
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
                && this.getMatricNumber().equals(otherStudent.getMatricNumber())
                && this.getMajor().equals(otherStudent.getMajor())); //checks for equality
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, matricNumber, email, major);
    }
}
