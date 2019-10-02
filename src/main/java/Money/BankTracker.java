package Money;

import java.text.SimpleDateFormat;
import java.util.Date;

public class BankTracker {

    private String description;
    private int amt;
    private Date latestDate;
    private double rate;
    private SimpleDateFormat simpleDateFormat;


    public BankTracker(String accountDescription, int initialAmt, Date initialDate, double interestRate) {
        description = accountDescription;
        amt = initialAmt;
        latestDate =  initialDate;
        rate = interestRate;
        simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
    }

    public String getBankAccountInfo() {
        return "Name: " + description + " Balance: " + Integer.toString(amt) + " Initial Date: "
                + simpleDateFormat.format(latestDate) + " Interest Rate: " + Double.toString(rate);
    }

    public String getDescription() {
        return description;
    }

    public int getAmt() {
        return amt;
    }

    public Date getLatestDate() {
        return latestDate;
    }

    public double getRate() {
        return rate;
    }
}
