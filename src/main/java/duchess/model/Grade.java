package duchess.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

/**
 * Represents a grade in a module.
 */
public class Grade {
    private String assessment;
    private double marks;
    private double maxMarks;
    private double weightage;
    private boolean isComplete;
    private double modulePercentage; // marks obtained converted to percentage of the module

    /**
     * Creates a Grade object.
     *
     * @param assessment description of assessment
     * @param marks marks obtained
     * @param maxMarks maximum marks obtainable
     * @param weightage weightage of assessment out of 100
     * @param isComplete true if the assessment is complete, false otherwise
     */
    @JsonCreator
    public Grade(@JsonProperty("task") String assessment, @JsonProperty("marks") int marks,
                 @JsonProperty("maxMarks") int maxMarks, @JsonProperty("weightage") int weightage,
                 @JsonProperty("isComplete") boolean isComplete) {
        this.assessment = assessment;
        this.marks = marks;
        this.maxMarks = maxMarks;
        this.weightage = weightage;
        this.isComplete = isComplete;
    }

    private void calculateModulePercentage() {
        modulePercentage = marks / maxMarks * weightage;
    }

    /**
     * Creates a grade object.
     *
     * @param assessment description of assessment
     * @param weightage weightage of assessment out of 100
     */
    public Grade(String assessment, int weightage) {
        this.assessment = assessment;
        this.weightage = weightage;
        this.isComplete = false;
    }

    /**
     * Creates a grade object.
     *
     * @param assessment description of assessment
     * @param marks marks obtained
     * @param maxMarks maximum marks obtainable
     * @param weightage weightage of assessment out of 100
     */
    public Grade(String assessment, int marks, int maxMarks, int weightage) {
        this(assessment, weightage);
        this.marks = marks;
        this.maxMarks = maxMarks;
        this.isComplete = true;
        calculateModulePercentage();
    }

    @JsonGetter
    public String getTask() {
        return assessment;
    }

    @JsonSetter
    public void setTask(String assessment) {
        this.assessment = assessment;
    }

    @JsonGetter
    public double getMarks() {
        return marks;
    }

    @JsonSetter
    public void setMarks(int marks) {
        this.marks = marks;
    }

    @JsonGetter
    public double getMaxMarks() {
        return maxMarks;
    }

    @JsonSetter
    public void setMaxMarks(int maxMarks) {
        this.maxMarks = maxMarks;
    }

    @JsonGetter
    public double getWeightage() {
        return weightage;
    }

    @JsonSetter
    public void setWeightage(int weightage) {
        this.weightage = weightage;
    }

    @JsonGetter
    public boolean getIsComplete() {
        return isComplete;
    }

    @JsonSetter
    public void setIsComplete(boolean isComplete) {
        this.isComplete = isComplete;
    }

    @JsonGetter
    public double getModulePercentage() {
        return modulePercentage;
    }

    @JsonSetter
    public void setModulePercentage(double modulePercentage) {
        this.modulePercentage = modulePercentage;
    }

    /**
     * Marks a grade as complete.
     * Sets the marks and maxMarks to complete the grade.
     *
     * @param marks    marks obtained
     * @param maxMarks maximum marks obtainable
     */
    public void markAsComplete(int marks, int maxMarks) {
        this.isComplete = true;
        this.marks = marks;
        this.maxMarks = maxMarks;
        calculateModulePercentage();
    }

    @Override
    public String toString() {
        if (isComplete) {
            return String.format("%s %.1f/%.1f %.1f%%", assessment, marks, maxMarks, weightage);
        } else {
            return String.format("%s %.1f%%", assessment, weightage);
        }
    }
}
