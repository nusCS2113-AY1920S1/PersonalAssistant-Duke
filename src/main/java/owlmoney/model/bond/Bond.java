package owlmoney.model.bond;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Represents one investment bond.
 */
public class Bond {
    private String name;
    private double amount;
    private double rate;
    private String category;
    private Date date;
    private int year;
    private Date nextDateToCreditBondCouponInterest;

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
        this.nextDateToCreditBondCouponInterest = calculateNextDateToCreditInterest(this.date);
    }

    /**
     * Creates a Bond with details of name, amount, rate, nextDateToCreditBondCouponInterest.
     *
     * @param name   The name of the bond issue.
     * @param amount The amount of money the bond costs.
     * @param rate   The coupon rate of the bond.
     * @param date   The date the bond was purchased.
     * @param year   The year the bond will last.
     * @param nextDateToCreditBondCouponInterest The next date that interest will be credited.
     */
    public Bond(String name, double amount, double rate, Date date, int year, Date nextDateToCreditBondCouponInterest) {
        this.name = name;
        this.amount = amount;
        this.rate = rate;
        this.date = date;
        this.year = year;
        this.category = "bonds";
        this.nextDateToCreditBondCouponInterest = nextDateToCreditBondCouponInterest;
    }

    /**
     * Calculates the next date the bond interest will be credited upon bond creation.
     *
     * @param boughtDate the date the bond was bought.
     * @return the next date coupon interest will be credited.
     */
    private Date calculateNextDateToCreditInterest(Date boughtDate) {
        Calendar nextDateToCreditInterest = Calendar.getInstance();
        nextDateToCreditInterest.clear();
        nextDateToCreditInterest.setTime(boughtDate);
        nextDateToCreditInterest.add(Calendar.MONTH, 6);
        return nextDateToCreditInterest.getTime();
    }

    /**
     * Gets the next date to credit interest.
     *
     * @return the next date that interest can be credited.
     */
    public Date getNextDateToCreditInterest() {
        return this.nextDateToCreditBondCouponInterest;
    }

    /**
     * Sets the next date to credit interest.
     */
    public void setNextDateToCreditInterest(Date newDate) {
        this.nextDateToCreditBondCouponInterest = newDate;
    }

    /**
     * Gets the date of expiry of the bond.
     * @return the date of expiry of the bond.
     */
    public Date getBondEndDate() {
        Calendar nextDateToCreditInterest = Calendar.getInstance();
        nextDateToCreditInterest.clear();
        nextDateToCreditInterest.setTime(this.date);
        nextDateToCreditInterest.add(Calendar.YEAR, this.year);
        return nextDateToCreditInterest.getTime();
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
     * Gets the date the bond was purchased.
     *
     * @return the date the bond was purchased in Date format.
     */
    public Date getDateInDateFormat() {
        return this.date;
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
