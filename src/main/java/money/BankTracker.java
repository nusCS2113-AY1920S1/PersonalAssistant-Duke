package money;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * This class represents a bank account tracker which can be used to
 * store the related information of a bank account
 * (account description, balance, the latest update date and the interest rate)
 */
public class BankTracker {

    private String description;
    private int amt;
    private LocalDate latestDate;
    private double rate;
    private DateTimeFormatter dateTimeFormatter;

    public BankTracker(String accountDescription, int initialAmt, LocalDate initialDate, double interestRate) {
        description = accountDescription;
        amt = initialAmt;
        latestDate =  initialDate;
        rate = interestRate;
        dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    }

    /**
     * The method is a getter to get a formatted string which contains
     * all the information of a bank account tracker
     * @return A string which contains all the information of a bank account tracker
     */
    public String getBankAccountInfo() {
        return "  Name: " + description + "\n  Balance: " + amt + "\n  Initial Date: "
                + dateTimeFormatter.format(latestDate) + "\n  Interest Rate: " + rate;
    }
}
