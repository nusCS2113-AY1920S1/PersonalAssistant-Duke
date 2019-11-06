package money;

import controlpanel.DukeException;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

/**
 * This class represents a bank account tracker which can be used to
 * store the related information of a bank account
 * (account description, balance, the latest update date and the interest rate)
 */
public class BankTracker {

    private String description;
    private float amt;
    private LocalDate latestDate;
    private double rate;

    //@@author cctt1014
    public BankTracker(String accountDescription, float initialAmt, LocalDate initialDate, double interestRate) {
        description = accountDescription;
        amt = initialAmt;
        latestDate =  initialDate;
        rate = interestRate;
    }

    /**
     * The method is a getter to get a formatted string which contains
     * all the information of a bank account tracker
     * @return A string which contains all the information of a bank account tracker
     */
    public String getBankAccountInfo() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        String balance = decimalFormat.format(amt);
        return "  Name: " + description + "\n  Balance: " + balance + "\n  Latest Update Date: "
                + dateTimeFormatter.format(latestDate) + "\n  Interest Rate: " + rate;
    }

    /**
     * The method is a getter to get the description of the bank account
     * @return The description of the bank account
     */
    public String getDescription() {
        return description;
    }

    /**
     * The method is a getter to get the latest update date for the account tracker
     * @return The latest update date for the account tracker
     */
    public LocalDate getLatestDate() {
        return latestDate;
    }

    /**
     * The method is a getter to get the balance at the latest update date
     * @return The balance at the latest update date
     */
    public float getAmt() {
        return amt;
    }

    /**
     * The method is a getter to get the interest rate
     * @return The interest rate
     */
    public double getRate() {
        return rate;
    }

    /**
     * This method adds the given value to the current balance and update the balance based on
     * the current amount and the given interest rate.
     * @param value The given value (can be negative)
     */
    public void addAmt(double value) {
        amt += value;
    }

    /**
     * This method updates the balance based on the given date and
     * changes the latest date to the given date.
     * @param date The latest update date
     */
    public void updateDate(LocalDate date) throws DukeException {
        if (date.isBefore(latestDate)) {
            throw new DukeException("The new date cannot be before the current latest update date!");
        }
        Period period = Period.between(latestDate, date);
        int length = period.getMonths() + period.getYears()*12;
        amt *= Math.pow((1+rate), length);
        latestDate = date;
    }

    /**
     * This methods just predicts the future balance on the given date
     * without changing the balance and the latest update date.
     * @param date the given date
     * @return the future balanced based on the given date
     */
    public float predictAmt(LocalDate date) throws DukeException {
        if (date.isBefore(latestDate)) {
            throw new DukeException("The date cannot be early than the current latest update date!");
        }
        Period period = Period.between(latestDate, date);
        int length = period.getMonths() + period.getYears()*12;
        return (float) (amt * Math.pow((1+rate), length));
    }
}
