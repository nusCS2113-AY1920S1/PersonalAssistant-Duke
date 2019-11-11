package duchess.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an academic module taken by students.
 */
public class Module {
    private String name;
    private String code;
    private List<Grade> grades;
    private double weightageCompleted;
    private double weightageObtained;
    private double weightageTotal;

    /**
     * Creates a new module using Json data.
     *
     * @param code the code of the module
     * @param name the name of the module
     * @param grades list of grades
     * @param weightageCompleted total weightage of completed grades
     * @param weightageObtained weightage obtained from completed grades
     * @param weightageTotal total weightage for all the grades
     */
    @JsonCreator
    public Module(@JsonProperty("code") String code, @JsonProperty("name") String name,
                  @JsonProperty("grades") List<Grade> grades, @JsonProperty("weightageCompleted")
                  double weightageCompleted, @JsonProperty("weightageObtained") double weightageObtained,
                  @JsonProperty("weightageTotal") double weightageTotal) {
        this.code = code;
        this.name = name;
        this.grades = grades;
        this.weightageCompleted = weightageCompleted;
        this.weightageObtained = weightageObtained;
        this.weightageTotal = weightageTotal;
    }

    /**
     * Creates a new Module.
     *
     * @param code the code of the module
     * @param name the mame of the module
     */
    public Module(String code, String name) {
        this.code = code;
        this.name = name;
        this.grades = new ArrayList<>();
    }

    /**
     * Returns a string to represent the Module.
     *
     * @return string to represent module
     */
    public String toString() {
        if (weightageCompleted == 0) {
            return String.format("%s %s", code, name);
        }
        return String.format("%s %s (%.1f/%.1f%%)", code, name, weightageObtained, weightageCompleted);
    }

    /**
     * Returns true if the module has the same code as the supplied code.
     *
     * @param code the code to check against the module code
     * @return true if there is a match
     */
    public boolean isOfCode(String code) {
        return this.code.equalsIgnoreCase(code);
    }

    /**
     * Returns true if two modules share the same code.
     *
     * @param other the other module to compare
     * @return the equality status
     */
    public boolean equals(Object other) {
        if (!(other instanceof Module)) {
            return false;
        }
        Module that = (Module) other;
        return this.code.equalsIgnoreCase(that.code);
    }

    /**
     * Deletes the specified grade object from the module.
     * @param gradeNo the index of the grade to delete
     */
    public void deleteGrade(int gradeNo) {
        this.grades.remove(gradeNo);
    }

    /**
     * Adds a given grade object to the module.
     *
     * @param grade the grade to add
     */
    public void addGrade(Grade grade) {
        if (grade.getIsComplete()) {
            updateCompleteGradeWeightage(grade.getModulePercentage(), grade.getWeightage());
        } else {
            updateIncompleteGradeWeightage(grade.getWeightage());
        }
        this.grades.add(grade);
    }

    /**
     * Updates the completed and obtained weightages.
     * Called when a grade is marked as complete
     *
     * @param grade grade marked as complete
     */

    public void updateGrade(Grade grade) {
        this.weightageCompleted += grade.getWeightage();
        this.weightageObtained += grade.getModulePercentage();
    }

    /**
     * Updates the weightages related to completed grades.
     * Called when a completed grade is added to the list of grades.
     *
     * @param obtainedPercentage marks obtained in the newly added grade as a percentage of the module
     * @param gradeWeightage     the weightage of the grade
     */
    private void updateCompleteGradeWeightage(double obtainedPercentage, double gradeWeightage) {
        this.weightageCompleted += gradeWeightage;
        this.weightageTotal += gradeWeightage;
        this.weightageObtained += obtainedPercentage;
    }

    /**
     * Updates the total weightage.
     * Called when an incomplete grade is added to the list of grades.
     *
     * @param gradeWeightage the weightage of the grade
     */
    private void updateIncompleteGradeWeightage(double gradeWeightage) {
        this.weightageTotal += gradeWeightage;
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonGetter("code")
    public String getCode() {
        return code;
    }

    @JsonGetter("grades")
    public List<Grade> getGrades() {
        return grades;
    }

    @JsonGetter("weightageCompleted")
    public double getWeightageCompleted() {
        return weightageCompleted;
    }

    @JsonGetter("weightageObtained")
    public double getWeightageObtained() {
        return weightageObtained;
    }

    @JsonGetter("weightageTotal")
    public double getWeightageTotal() {
        return weightageTotal;
    }
}
