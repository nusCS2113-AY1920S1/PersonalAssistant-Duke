package duke.extensions;

public enum RecurrencePeriod {
    NONE, DAILY, WEEKLY;

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