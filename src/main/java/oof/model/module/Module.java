package oof.model.module;

import java.util.ArrayList;

import oof.model.task.Assignment;

/**
 * Represents a Module class for university modules.
 */
public class Module {

    private String moduleCode;
    private String moduleName;
    private ArrayList<Lesson> lessons;
    private ArrayList<Assessment> assessments;
    private ArrayList<Assignment> assignments;

    /**
     * Constructor for Module object.
     *
     * @param moduleCode String containing module code.
     * @param moduleName String containing module name.
     */
    public Module(String moduleCode, String moduleName) {
        this.moduleCode = moduleCode.toUpperCase();
        this.moduleName = moduleName;
        this.lessons = new ArrayList<>();
        this.assessments = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        this.lessons = lessons;
    }

    public ArrayList<Assessment> getAssessments() {
        return assessments;
    }

    public void setAssessments(ArrayList<Assessment> assessments) {
        this.assessments = assessments;
    }

    public ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public void setAssignments(ArrayList<Assignment> assignments) {
        this.assignments = assignments;
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public void removeLesson(int index) {
        lessons.remove(index);
    }

    public void addAssessment(Assessment assessment) {
        assessments.add(assessment);
    }

    public void removeAssessment(int index) {
        assessments.remove(index);
    }

    @Override
    public String toString() {
        return moduleCode + " " + moduleName;
    }


}
