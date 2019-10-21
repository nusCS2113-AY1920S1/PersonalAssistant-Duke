package oof.model.module;

import java.util.ArrayList;

/**
 * Represents a list of Semester objects.
 */
public class SemesterList {

    private ArrayList<Semester> semesterList;

    /**
     * Constructor for SemesterList.
     *
     * @param semesterList ArrayList of Semesters.
     */
    public SemesterList(ArrayList<Semester> semesterList) {
        this.semesterList = semesterList;
    }

    public SemesterList() {
        this.semesterList = new ArrayList<>();
    }

    public ArrayList<Semester> getSemesterList() {
        return semesterList;
    }

    public int getSize() {
        return semesterList.size();
    }

    public Semester getSemester(int index) {
        return semesterList.get(index);
    }

    public void addSemester(Semester semester) {
        semesterList.add(semester);
    }

    public void removeSemester(int index) {
        semesterList.remove(index);
    }
}
