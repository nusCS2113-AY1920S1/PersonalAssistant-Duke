package oof.model.module;

import java.util.ArrayList;

/**
 * Represents a Semester class for an university semester.
 */
public class Semester {

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
        this.modules = new ArrayList<>();
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public void removeModule(int index) {
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

    public ArrayList<Module> getModules() {
        return modules;
    }

    public void setModules(ArrayList<Module> modules) {
        this.modules = modules;
    }

    @Override
    public String toString() {
        return "Academic Year " + academicYear + ", " + semesterName;
    }
}