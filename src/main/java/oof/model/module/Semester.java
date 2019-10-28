package oof.model.module;

import java.util.ArrayList;

/**
 * Represents a Semester class for an university semester.
 */
public class Semester {

    private static final String DELIMITER = "||";
    private String academicYear;
    private String semesterName;
    private String startDate;
    private String endDate;
    private static ArrayList<Module> modules;

    /**
     * Constructor for Semester object.
     *
     * @param academicYear String containing academic year of Semester.
     * @param semesterName String containing name of Semester.
     */
    public Semester(String academicYear, String semesterName) {
        this.academicYear = academicYear;
        this.semesterName = semesterName;
        modules = new ArrayList<>();
    }

    public Semester(ArrayList<Module> modules) {
        this.modules = modules;
    }

    public Semester() {
        this.modules = new ArrayList<>();
    }

    public static void addModule(Module module) {
        modules.add(module);
    }

    public static void removeModule(int index) {
        modules.remove(index);
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public void setAcademicYear(String academicYear) {
        this.academicYear = academicYear;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public void setSemesterName(String semesterName) {
        this.semesterName = semesterName;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public static ArrayList<Module> getModules() {
        return modules;
    }

    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }

    public static Module getModule(int index) {
        return modules.get(index);
    }

    /**
     * Converts a semester object to string format for storage.
     * @return Semester object in string format for storage.
     */
    public String toStorageString() {
        return "S" + DELIMITER + academicYear + DELIMITER + semesterName;
    }

    @Override
    public String toString() {
        return "Academic Year " + academicYear + ", " + semesterName;
    }
}