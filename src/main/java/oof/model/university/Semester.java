package oof.model.university;

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
    private ArrayList<Module> modules;

    /**
     * Constructor for Semester object.
     *
     * @param academicYear String containing academic year of Semester.
     * @param semesterName String containing name of Semester.
     * @param startDate    String containing start date of Semester.
     * @param endDate      String containing end date of Semester.
     */
    public Semester(String academicYear, String semesterName, String startDate, String endDate) {
        this.academicYear = academicYear;
        this.semesterName = semesterName;
        this.startDate = startDate;
        this.endDate = endDate;
        modules = new ArrayList<>();
    }

    public Semester(ArrayList<Module> modules) {
        this.modules = modules;
    }

    public Semester() {
        this.modules = new ArrayList<>();
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public void deleteModule(int index) {
        modules.remove(index);
    }

    public String getAcademicYear() {
        return academicYear;
    }

    public String getSemesterName() {
        return semesterName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public ArrayList<Module> getModules() {
        return modules;
    }

    public Module getModule(int index) {
        return modules.get(index);
    }

    /**
     * Converts a semester object to string format for storage.
     *
     * @return Semester object in string format for storage.
     */
    public String toStorageString() {
        return "SEMESTER" + DELIMITER + academicYear + DELIMITER + semesterName + DELIMITER + startDate + DELIMITER
                + endDate;
    }

    @Override
    public String toString() {
        return "Academic Year " + academicYear + ", " + semesterName + " (" + startDate + "-" + endDate + ")";
    }

    public boolean isIndexValid(int index) {
        return index >= 0 && index < modules.size();
    }
}