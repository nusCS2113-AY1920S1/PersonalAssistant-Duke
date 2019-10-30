package duke.models.student;

import java.util.Objects;

import static java.util.Objects.requireNonNull;

public class Student {
    private final Name name;
    private final MatricNumber matricNumber;
    private final Email email;
    private final Major major;

    /**
     * This constructor instantiates the student and all its properties.
     * @param name stores the name of the student
     * @param matricNumber stores the student ID/ matric number of the student
     * @param email stores the email id of the student
     * @param major stores the major/course of the student
     */
    public Student(Name name, MatricNumber matricNumber, Email email, Major major) {
        requireNonNull(name);
        requireNonNull(matricNumber);
        requireNonNull(email);
        requireNonNull(major);
        this.name = name;
        this.matricNumber = matricNumber;
        this.email = email;
        this.major = major;
    }

    public Name getName() {
        return name;
    }

    public MatricNumber getMatricNumber() {
        return matricNumber;
    }

    public Email getEmail() {
        return email;
    }

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
        return Objects.hash(name,matricNumber,email,major);
    }
}
