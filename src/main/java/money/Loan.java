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
     * Constructor of the Loan Object to record outgoing and incoming loans.
     * @param amount Total amount of the loan
     * @param description Party which to loan is lent to/borrowed from
     * @param startDate Date which the loan was made
     * @param type Type determining the loan is incoming or outgoing
     */
    //@@author chengweixuan
    public Loan(float amount, String description, LocalDate startDate, Type type) {
        super(amount, description);
        this.startDate = startDate;
        this.endDate = null;
        this.isSettled = false;
        this.outstandingLoan = amount;
        this.type = type;

        dateTimeFormatter  = DateTimeFormatter.ofPattern("d/M/yyyy");
    }

    /**
     * This method returns a String with the information of the loan.
     * String contains the type of loan, whether the loan is settled or outstanding,
     * and the endDate if the loan is settled, or the outstanding amount if the loan is outstanding.
     * @return String containing information of the loan
     */
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

    public LocalDate getDateStartDate() {
        return startDate;
    }

    public LocalDate getDateEndDate() {
        return endDate;
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

    /**
     * This method is called to settle the loan if it is repaid.
     * If the entire loan is settled, the loan is set as settled.
     * @param amount Amount repaid to the loan
     * @throws ParseException If invalid date is parsed
     */
    public void settleLoanDebt(float amount) throws ParseException {
        outstandingLoan -= amount;
        if (outstandingLoan == 0) {
            isSettled = true;
            endDate = Parser.shortcutTime("now");
        }
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

    /**
     * This method creates a String that represents the state of the loan.
     * If loan is settled, returns the endDate of the loan, else it prints
     * the outstanding amount of the loan.
     * @return String representing state of the loan
     */
    private String getEndDateString() {
        if (endDate == null) {
            return " Outstanding Amount: $" + outstandingLoan;
        } else {
            return " (Paid Back On: " + getEndDate() + ")";

        }
    }

    /**
     * This method is called to update an existing loan with its data from the save file during
     * the load process on start up of Financial Ghost.
     * @param typeStr String denoting the type of loan
     * @param endDate String representing endDate of loan
     * @param status Integer representing status of loan
     * @param outstandingLoan Float of the outstanding amount of the loan
     */
    public void updateExistingLoan(String typeStr, String endDate, int status, float outstandingLoan) {
        if (typeStr.equals("INCOMING")) {
            this.type = Type.INCOMING;
        } else if (typeStr.equals("OUTGOING")) {
            this.type = Type.OUTGOING;
        } else {
            this.type = Type.ALL;
        }
        if (endDate.equals("")) {
            this.endDate = null;
        }
        this.isSettled = status == 1;
        this.outstandingLoan = outstandingLoan;
    }

    public Type getType() {
        return type;
    }

}
