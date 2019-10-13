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

    public Loan(float amount, String description, LocalDate startDate, String type, String endDate,
                String status, float outstandingLoan) throws ParseException {
        super(amount, description);
        this.startDate = startDate;

        if (type.equals("OUTGOING")) {
            this.type = Type.OUTGOING;
        } else if (type.equals("INCOMING")) {
            this.type = Type.INCOMING;
        } else {
            this.type = null;
        }

//        LocalDate compareDate = Parser.shortcutTime("9/10/1997");
//        if (endDate == compareDate) {
//            this.endDate = null;
//        } else {
//            this.endDate = endDate;
//        }
        if (endDate.equals("")) {
            this.endDate = null;
        } else {
            this.endDate = LocalDate.parse(endDate, dateTimeFormatter);
        }

        if (status.equals("1")) {
            this.isSettled = true;
        } else {
            this.isSettled = false;
        }
        this.outstandingLoan = outstandingLoan;
    }

    public LocalDate getDateStartDate() {
        return startDate;
    }

    public LocalDate getDateEndDate() {
        return endDate;
    }

    private void setEndDate() throws ParseException {
        this.endDate = Parser.shortcutTime("now");
    }

    public boolean getStatus() {
        return isSettled;
    }

    public int getStatusInt() {
        if (isSettled) {
            return 1;
        } else {
            return 0;
        }
    }

    public float getOutstandingLoan() {
        return outstandingLoan;
    }

    public void settleLoanDebt(float amount) throws ParseException {
        if (amount == -2) {
            outstandingLoan = 0;
            isSettled = true;
            setEndDate();
        } else {
            outstandingLoan -= amount;
            if (outstandingLoan == 0) {
                isSettled = true;
                setEndDate();
            }
        }
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
                + getStartDate() + ")" + getEndDateString();
    }

    public String getStartDate() {
        return startDate.format(dateTimeFormatter);
    }

    public String getEndDate() {
        if (endDate == null) {
            return "";
        }
        return endDate.format(dateTimeFormatter);
    }

    private String getEndDateString() {
        if (endDate == null) {
            return " Outstanding Amount: $" + outstandingLoan;
        } else {
            return " (Paid Back On: " + getEndDate() + ")";

        }
    }

    public void updateExistingLoan(String typeStr, String endDate, int status, float outstandingLoan) {
        if (typeStr.equals("INCOMING")) {
            this.type = Type.INCOMING;
        } else if (typeStr.equals("OUTGOING")) {
            this.type = Type.OUTGOING;
        }

        if (endDate.equals("")) {
            this.endDate = null;
        }

        if (status == 1) {
            this.isSettled = true;
        } else {
            this.isSettled = false;
        }

        this.outstandingLoan = outstandingLoan;
    }

    public Type getType() {
        return type;
    }

    public String getTypeString() {
        return type.toString();
    }
}
