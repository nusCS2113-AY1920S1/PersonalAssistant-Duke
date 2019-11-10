package spinbox.entities.items;

import spinbox.exceptions.CorruptedDataException;
import spinbox.exceptions.InputException;

import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GradedComponent extends Item {
    private static final Logger LOGGER = Logger.getLogger(GradedComponent.class.getName());
    private static final String LOG_CORRUPTED = "Corrupted graded component: ";
    private static final String LOG_FROM_STORAGE = "GradedComponent recreated from storage: ";
    private static final String LOG_TO_STORAGE = "GradedComponent sent to storage: ";
    private static final String LOG_DIVISION_BY_ZERO = "Division by zero, user input: ";
    private static final String UNKNOWN_SCORE = "----";
    private static final String DIVIDE_BY_ZERO = "Maximum possible score should be non-zero";
    private static final String TWO_DP = "#.##";
    private static final String STORE_DELIMITER = " | ";
    private static final String DELIMITER_FILTER = " \\| ";
    private static final String BRACKET_OPEN = "[";
    private static final String BRACKET_CLOSE = "] ";
    private static final String COMPLETED = "COMPLETED";
    private static final String NOT_COMPLETED = "NOT COMPLETED";

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
        LOGGER.setLevel(Level.INFO);
        LOGGER.setUseParentHandlers(true);
        LOGGER.entering(getClass().getName(), "Constructor");
        this.weight = weight;
        this.scoreKnown = false;
        this.weightedScore = 0.0;
        LOGGER.exiting(getClass().getName(), "Constructor");
    }

    /**
     * Empty constructor to be populated from Storage string.
     */
    public GradedComponent() {
        super();
    }

    /**
     * This method describes the current status of the gradedComponent - complete or incomplete.
     * @return String describing the status of the gradedComponent
     */
    @Override
    public String getStatusText() {
        return (this.getDone() ? COMPLETED : NOT_COMPLETED);
    }

    /**
     * This method converts the gradedComponent data into a human readable string for display on the GUI.
     * @return a String that is human-readable and contains data to be shown on the screen.
     */
    @Override
    public String toString() {
        return BRACKET_OPEN + this.getStatusText() + BRACKET_CLOSE + this.getName() + "\n"
                + this.getWeightedScoreAsString() + "/" + Double.toString(this.getWeight());
    }

    /**
     * This is to create a stringified version of a GradedComponent instance for storage purposes.
     * @return String version of GradedComponent, ready for storage.
     */
    @Override
    public String storeString() {
        LOGGER.entering(getClass().getName(), "storeString");
        LOGGER.fine(LOG_TO_STORAGE + this.toString());
        LOGGER.exiting(getClass().getName(), "storeString");
        return super.storeString() + STORE_DELIMITER + (this.isScoreKnown() ? 1 : 0)
            + STORE_DELIMITER + Double.toString(this.weight) + STORE_DELIMITER + Double.toString(this.weightedScore);
    }

    /**
     * This method repopulates the newly created GradedComponent object with data from a String extracted from storage.
     * @param fromStorage This String is provided directly from the localStorage instance.
     * @throws CorruptedDataException Thrown when a user manually edits the .txt file incorrectly.
     */
    @Override
    public void fromStoredString(String fromStorage) throws CorruptedDataException {
        LOGGER.entering(getClass().getName(), "fromStoredString");
        try {
            String[] components = fromStorage.split(DELIMITER_FILTER);
            this.updateDone(Integer.parseInt(components[0]) == 1);
            this.setName(components[1]);
            this.setScoreKnown(Integer.parseInt(components[2]) == 1);
            this.setWeight(Double.parseDouble(components[3]));
            this.setWeightedScore(Double.parseDouble(components[4]));
            LOGGER.fine(LOG_FROM_STORAGE + this.toString());
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            LOGGER.severe(LOG_CORRUPTED + fromStorage);
            throw new CorruptedDataException();
        }
        LOGGER.exiting(getClass().getName(), "Constructor");
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
        LOGGER.entering(getClass().getName(), "updateWeightedScore");
        if (this.checkDivideByZero(maximumScore)) {
            LOGGER.warning(LOG_DIVISION_BY_ZERO + yourScore + "/" + maximumScore);
            throw new InputException(DIVIDE_BY_ZERO);
        } else {
            this.setComplete();
            double score = this.calculateWeightedScore(yourScore, maximumScore);
            this.setWeightedScore(score);
            assert this.isScoreKnown();
            assert this.getDone();
        }
        LOGGER.exiting(getClass().getName(), "updateWeightedScore");
    }

    /**
     * Takes in an already weighted score (%) and updates the weighted percentage achieved by the user. Also marks
     * the graded component as done, regardless of previous state.
     * @param weightedScore A double that reflects the percentage achieved by the user for that graded component.
     */
    public void updateWeightedScore(double weightedScore) {
        LOGGER.entering(getClass().getName(), "updateWeightedScore");
        this.setComplete();
        this.setWeightedScore(weightedScore);
        assert this.isScoreKnown();
        assert this.getDone();
        LOGGER.exiting(getClass().getName(), "updateWeightedScore");
    }

    /**
     * Returns the relative weight in % of the graded component across 100% of module assessment.
     * @return a double, stating the relative weight of the graded component.
     */
    public double getWeight() {
        return weight;
    }

    private boolean checkDivideByZero(double maximumScore) {
        return (Double.compare(0.0, maximumScore) == 0);
    }

    private void setScoreKnownAsTrue() {
        this.scoreKnown = true;
    }

    private void setScoreKnown(boolean scoreKnown) {
        this.scoreKnown = scoreKnown;
    }

    private boolean isScoreKnown() {
        return this.scoreKnown;
    }

    private void setComplete() {
        this.markDone();
        this.setScoreKnownAsTrue();
    }

    private void setWeight(double weight) {
        this.weight = weight;
    }

    private void setWeightedScore(double weightedScore) {
        this.weightedScore = weightedScore;
    }

    private double calculateWeightedScore(double yourScore, double maximumScore) {
        assert maximumScore != 0.0;
        return ((yourScore / maximumScore)) * this.weight;
    }
}
