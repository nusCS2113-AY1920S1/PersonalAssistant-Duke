package duchess.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a grade in a module.
 */
public class Grade {
    private String description;
    private double marks;
    private double maxMarks;
    private double weightage;
    private boolean isComplete;
    private double modulePercentage; // marks obtained converted to percentage of the module

    /**
     * Creates a Grade object.
     *
     * @param description description of assessment
     * @param marks marks obtained
     * @param maxMarks maximum marks obtainable
     * @param weightage weightage of assessment out of 100
     * @param isComplete true if the assessment is complete, false otherwise
     */
    @JsonCreator
    public Grade(@JsonProperty("description") String description, @JsonProperty("marks") double marks,
                 @JsonProperty("maxMarks") double maxMarks, @JsonProperty("weightage") double weightage,
                 @JsonProperty("isComplete") boolean isComplete,
                 @JsonProperty("modulePercentage") double modulePercentage) {
        this.description = description;
        this.marks = marks;
        this.maxMarks = maxMarks;
        this.weightage = weightage;
        this.isComplete = isComplete;
        this.modulePercentage = modulePercentage;
    }

    private void calculateModulePercentage() {
        modulePercentage = marks / maxMarks * weightage;
    }

    /**
     * Creates a grade object.
     *
     * @param description description of assessment
     * @param weightage weightage of assessment out of 100
     */
    public Grade(String description, double weightage) {
        this.description = description;
        this.weightage = weightage;
        this.isComplete = false;
    }

    /**
     * Creates a grade object.
     *
     * @param description description of assessment
     * @param marks marks obtained
     * @param maxMarks maximum marks obtainable
     * @param weightage weightage of assessment out of 100
     */
    public Grade(String description, double marks, double maxMarks, double weightage) {
        this(description, weightage);
        this.marks = marks;
        this.maxMarks = maxMarks;
        this.isComplete = true;
        calculateModulePercentage();
    }

    @JsonGetter
    public String getDescription() {
        return description;
    }

    @JsonGetter
    public double getMarks() {
        return marks;
    }

    @JsonGetter
    public double getMaxMarks() {
        return maxMarks;
    }

    @JsonGetter
    public double getWeightage() {
        return weightage;
    }

    @JsonGetter
    public boolean getIsComplete() {
        return isComplete;
    }

    @JsonGetter
    public double getModulePercentage() {
        return modulePercentage;
    }

    /**
     * Marks a grade as complete.
     * Sets the marks and maxMarks to complete the grade.
     *
     * @param marks    marks obtained
     * @param maxMarks maximum marks obtainable
     */
    public void markAsComplete(double marks, double maxMarks) {
        this.isComplete = true;
        this.marks = marks;
        this.maxMarks = maxMarks;
        calculateModulePercentage();
    }

    @Override
    public String toString() {
        if (isComplete) {
            return String.format("%s %.1f/%.1f (%.1f%%)", description, marks, maxMarks, weightage);
        } else {
            return String.format("%s (%.1f%%)", description, weightage);
        }
    }
}
