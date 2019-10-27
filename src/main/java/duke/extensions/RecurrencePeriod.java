package duke.extensions;

public enum RecurrencePeriod {
    NONE, DAILY, WEEKLY;

    public String recurrenceCode() {
        switch (this) {
            case DAILY:
                return "D";
            case WEEKLY:
                return "W";
            default:
                return "N";
        }
    }

    public String recurrenceDescription() {
        switch (this) {
            case DAILY:
                return " every dai";
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