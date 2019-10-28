package oof.model.module;

import java.util.ArrayList;

import oof.model.task.Assignment;

/**
 * Represents a Module class for university modules.
 */
public class Module {

    private static final String DELIMITER = "||";
    private static String moduleCode;
    private static String moduleName;
    private static ArrayList<Lesson> lessons;
    private static ArrayList<Assessment> assessments;
    private static ArrayList<Assignment> assignments;

    /**
     * Constructor for Module object.
     *
     * @param moduleCode String containing module code.
     * @param moduleName String containing module name.
     */
    public Module(String moduleCode, String moduleName) {
        Module.moduleCode = moduleCode.toUpperCase();
        Module.moduleName = moduleName;
        lessons = new ArrayList<>();
        assessments = new ArrayList<>();
        assignments = new ArrayList<>();
    }

    public Module(ArrayList<Lesson> lessons, ArrayList<Assessment> assessments) {
        this.lessons = lessons;
        this.assessments = assessments;
    }

    public Module() {
        this.lessons = new ArrayList<>();
        this.assessments = new ArrayList<>();
    }

    public static String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        Module.moduleCode = moduleCode;
    }

    public static String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        Module.moduleName = moduleName;
    }

    public static ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public static Lesson getLesson(int index) {
        return lessons.get(index);
    }

    public void setLessons(ArrayList<Lesson> lessons) {
        Module.lessons = lessons;
    }

    public static ArrayList<Assessment> getAssessments() {
        return assessments;
    }

    public static Assessment getAssessment(int index) {
        return assessments.get(index);
    }

    public void setAssessments(ArrayList<Assessment> assessments) {
        Module.assessments = assessments;
    }

    public static ArrayList<Assignment> getAssignments() {
        return assignments;
    }

    public static Assignment getAssignment(int index) {
        return assignments.get(index);
    }

    public void setAssignments(ArrayList<Assignment> assignments) {
        Module.assignments = assignments;
    }

    public static void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public static void removeAssignment(int index) {
        assignments.remove(index);
    }

    public static void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public static void removeLesson(int index) {
        lessons.remove(index);
    }

    public static void addAssessment(Assessment assessment) {
        assessments.add(assessment);
    }

    public static void removeAssessment(int index) {
        assessments.remove(index);
    }

    /**
     * Converts a Module object to string format for storage.
     * @return Module object in string format for storage.
     */
    public String toStorageString() {
        return "M" + DELIMITER + moduleCode + DELIMITER + moduleName;
    }

    @Override
    public String toString() {
        return moduleCode + " " + moduleName;
    }


}
