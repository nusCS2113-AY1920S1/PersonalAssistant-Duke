package oof.model.university;

import java.util.ArrayList;

import oof.model.task.Assessment;
import oof.model.task.Assignment;

/**
 * Represents a Module class for university modules.
 */
public class Module {

    private static final String DELIMITER = "||";
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

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public ArrayList<Lesson> getLessons() {
        return lessons;
    }

    public Lesson getLesson(int index) {
        return lessons.get(index);
    }

    public void addAssignment(Assignment assignment) {
        assignments.add(assignment);
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
    }

    public void deleteLesson(int index) {
        lessons.remove(index);
    }

    public void addAssessment(Assessment assessment) {
        assessments.add(assessment);
    }

    /**
     * Converts a Module object to string format for storage.
     * @return Module object in string format for storage.
     */
    public String toStorageString() {
        return "MODULE" + DELIMITER + moduleCode + DELIMITER + moduleName;
    }

    @Override
    public String toString() {
        return moduleCode + " " + moduleName;
    }


    public boolean isLessonIndexValid(int index) {
        return index >= 0 && index < lessons.size();
    }
}
