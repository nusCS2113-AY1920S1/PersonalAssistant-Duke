package spinbox.items;

import spinbox.exceptions.InputException;

import java.text.DecimalFormat;

public class GradedComponent extends Item {
    private static final String UNKNOWN_SCORE = "----";
    private static final String DIVIDE_BY_ZERO = "Maximum possible score should be non-zero";
    private static final String TWO_DP = "#.##";

    private double weight;
    private boolean scoreKnown;
    private double weightedScore;

    /**
     * Constructor for a Graded component.
     * @param name Name of graded component as a String, e.g. "English Essay"
     * @param weight Percentage weight of this graded component as a double e.g. 20, 25.5065 etc
     */
    public GradedComponent(String name, double weight) {
        super(name);
        this.weight = weight;
        this.scoreKnown = false;
        this.weightedScore = 0.0;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public String storeString() {
        return super.storeString() + " | " + (this.isScoreKnown() ? 1 : 0)
            + " | " + Double.toString(this.weight) + " | " + Double.toString(this.weightedScore);
    }

    /**
     * Converts a previously entered weighted score into a human-readable String to be displayed to user.
     * If the score was not previously entered, returns dashes to indicate that it does not exist.
     * @return String containing weighted score to 2 decimal places, if it exists, or the String '----'
     */
    public String getWeightedScoreAsString() {
        if (this.isScoreKnown()) {
            DecimalFormat decimalFormat = new DecimalFormat(TWO_DP);
            return decimalFormat.format(this.weightedScore);
        } else {
            return UNKNOWN_SCORE;
        }
    }

    /**
     * Calculates and updates the weighted percentage scored by a user. Also marks the graded component
     * as done, regardless of previous state.
     * @param yourScore This is the score achieved by the user for this graded component. e.g. 22.5
     *                  It can exceed the maximumScore value as some graded components of certain modules
     *                  may have bonus marks, allowing a user to potentially score higher than the maximum.
     * @param maximumScore This is the maximum possible score achievable by the user for this graded component,
     *                     excluding bonus marks, if any. e.g. 25
     * @throws InputException This is thrown when the maximum possible score entered for this graded component is zero.
     */
    public void updateWeightedScore(double yourScore, double maximumScore) throws InputException {
        if (this.checkDivideByZero(maximumScore)) {
            throw new InputException(DIVIDE_BY_ZERO);
        } else {
            this.setComplete();
            double score = this.calculateWeightedScore(yourScore, maximumScore);
            this.setWeightedScore(score);
        }
    }

    /**
     * Takes in an already weighted score (%) and updates the weighted percentage achieved by the user. Also marks
     * the graded component as done, regardless of previous state.
     * @param weightedScore A double that reflects the percentage achieved by the user for that graded component.
     */
    public void updateWeightedScore(double weightedScore) {
        this.setComplete();
        this.setWeightedScore(weightedScore);
    }

    private boolean checkDivideByZero(double maximumScore) {
        return (Double.compare(0.0, maximumScore) == 0);
    }

    private void setScoreKnown() {
        this.scoreKnown = true;
    }

    private boolean isScoreKnown() {
        return this.scoreKnown;
    }

    private void setComplete() {
        this.markDone();
        this.setScoreKnown();
    }

    private void setWeightedScore(double weightedScore) {
        this.weightedScore = weightedScore;
    }

    private double calculateWeightedScore(double yourScore, double maximumScore) {
        return ((yourScore / maximumScore)) * this.weight;
    }
}
