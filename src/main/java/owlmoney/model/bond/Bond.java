package owlmoney.model.bond;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Bond class which represents one bond.
 */
public class Bond {
    private String name;
    private double amount;
    private double rate;
    private String category;
    private Date date;
    private int year;

    /**
     * Creates a Bond with details of name, amount, rate.
     *
     * @param name   The name of the bond issue.
     * @param amount The amount of money the bond costs.
     * @param rate   The coupon rate of the bond.
     * @param date   The date the bond was purchased.
     * @param year   The year the bond will last.
     */
    public Bond(String name, double amount, double rate, Date date, int year) {
        this.name = name;
        this.amount = amount;
        this.rate = rate;
        this.date = date;
        this.year = year;
        this.category = "bonds";
    }

    /**
     * Gets the number of years the bond will last.
     *
     * @return the number of years the bond will last.
     */
    public int getYear() {
        return this.year;
    }

    /**
     * Gets the date the bond was purchased.
     *
     * @return the date the bond was purchased.
     */
    public String getDate() {
        DateFormat temp = new SimpleDateFormat("dd MMMM yyyy");
        return temp.format(this.date);
    }

    /**
     * Gets the annual coupon rate of the bond.
     *
     * @return rate of the coupon annual rate.
     */
    public double getYearlyCouponRate() {
        return this.rate;
    }

    /**
     * Gets the half annual coupon rate that is used for biannual coupon rate issuance calculation.
     *
     * @return the half annual coupon rate.
     */
    public double getHalfYearlyCouponRate() {
        return this.rate / 2;
    }

    /**
     * Gets the amount of money the bond was bought for.
     *
     * @return the amount of money the bond costs.
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * Gets the name of the bond purchased.
     *
     * @return the name of the bond.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the category of the bond purchased.
     *
     * @return the category of this bond purchased.
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Sets the bond to a new interest rate.
     *
     * @param newRate new amount of the bond
     */
    public void setRate(double newRate) {
        this.rate = newRate;
    }

    /**
     * Sets the year of the bond to a new year.
     *
     * @param newYear new name of the bond
     */
    public void setYear(int newYear) {
        this.year = newYear;
    }

    /**
     * Gets the description of the bond.
     *
     * @return the description of the bond.
     */
    public String getBondDescription() {
        return "Name: " + getName() + "\n"
                + "Amount: $" + new DecimalFormat("0.00").format(getAmount()) + "\n"
                + "Rate: " + new DecimalFormat("0.00").format(getYearlyCouponRate()) + "\n"
                + "Date Purchased: " + getDate() + "\n"
                + "Number of years: " + getYear() + "\n";
    }

}
