package money;

import controlpanel.Parser;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Loan extends Item {

    public enum Type {
        OUTGOING,
        INCOMING,
        ALL
    }

    private LocalDate startDate;
    private LocalDate endDate;
    private boolean isSettled;
    private float outstandingLoan;
    Type type;

    private DateTimeFormatter dateTimeFormatter;

    /**
     * The constructor for the Loan Object to record loans.
     * @param amount
     * @param description
     * @param startDate
     */
    public Loan(float amount, String description, LocalDate startDate, Type type) {
        super(amount, description);
        this.startDate = startDate;
        this.endDate = null;
        this.isSettled = false;
        this.outstandingLoan = amount;
        this.type = type;

        dateTimeFormatter  = DateTimeFormatter.ofPattern("d/M/yyyy");
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setEndDate(String endDate) throws ParseException {
        this.endDate = Parser.shortcutTime(endDate);
    }

    @Override
    public String toString() {
        String typeStr;
        if (type == Type.OUTGOING) {
            typeStr = " [O]";
        } else if (type == Type.INCOMING) {
            typeStr = " [I]";
        } else {
            typeStr = null;
        }
        String status = isSettled ? "[Settled]" : "[Outstanding]";
        return status + typeStr + " " + super.getDescription() + "(loan: $" + super.getPrice() + ") (Lent On: "
                + getStartDateTime() + ")" + getEndDateString();
    }

    public String getStartDateTime() {
        return startDate.format(dateTimeFormatter);
    }

    public String getEndDateTime() {
        return endDate.format(dateTimeFormatter);
    }

    private String getEndDateString() {
        if (endDate == null) {
            return "";
        } else {
            return " (Paid Back On: " + getEndDateTime() + ")";

        }
    }

    public Type getType() {
        return type;
    }

    public String getTypeString() {
        return type.toString();
    }
}
