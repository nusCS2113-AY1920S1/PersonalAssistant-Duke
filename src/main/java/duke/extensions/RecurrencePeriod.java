package duke.extensions;

/**
 * Enum class that denotes the recurrence period
 */
public enum RecurrencePeriod {
    NONE, DAILY, WEEKLY;

    /**
     * Method that returns the recurrence code for printing
     *
     * @return a string of the code to print
     */
    public String recurrenceCode() {
        switch (this) {
        case DAILY:
            return "Daily";
        case WEEKLY:
            return "Weekly";
        default:
            return "NA";
        }
    }

    /**
     * Method that returns the recurrence description for printing
     *
     * @return a string of the description
     */
    public String recurrenceDescription() {
        switch (this) {
        case DAILY:
            return " every day";
        case WEEKLY:
            return " every week";
        default:
            return "N";
        }
    }

    /**
     * Method that returns the recurrence icon for printing
     *
     * @return a string of the icon
     */
    public String recurrenceIcon() {
        switch (this) {
        case DAILY:
        case WEEKLY:
            return "[R]";
        default:
            return "";
        }
    }
}
