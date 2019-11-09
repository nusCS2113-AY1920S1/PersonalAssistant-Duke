package wallet.model.record;

public enum RecurrenceRate {
    DAILY,
    WEEKLY,
    MONTHLY,
    NO;

    /**
     * Parses string input into corresponding recurrence rate.
     * @param recurrence String input of recurrence rate.
     * @return RecurrenceRate enum.
     */
    public static RecurrenceRate getRecurrence(String recurrence) {
        switch (recurrence.toLowerCase()) {
        case "daily":
            return RecurrenceRate.DAILY;
        case "weekly":
            return RecurrenceRate.WEEKLY;
        case "monthly":
            return RecurrenceRate.MONTHLY;
        case "no":
            return RecurrenceRate.NO;
        default:
            return null;
        }
    }
}
