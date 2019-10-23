package owlmoney.model.bond;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BondStub extends Bond {

    private String name;
    private double amount;
    private double rate;
    private String category;
    private Date date;
    private int year;

    BondStub() throws ParseException {
        super("TEST BOND",500,2.0,new SimpleDateFormat("dd/MM/yyyy").parse("1/1/2019"),1);
    }


    /**
     * Gets the number of years the bond will last.
     *
     * @return the number of years the bond will last.
     */
    @Override
    public int getYear() {
        return 1;
    }

    /**
     * Gets the date the bond was purchased.
     *
     * @return the date the bond was purchased.
     */
    @Override
    public String getDate() {
        return "01 January 2019";
    }

    /**
     * Gets the annual coupon rate of the bond.
     *
     * @return rate of the coupon annual rate.
     */
    @Override
    public double getYearlyCouponRate() {
        return 2.0;
    }

    /**
     * Gets the half annual coupon rate that is used for biannual coupon rate issuance calculation.
     *
     * @return the half annual coupon rate.
     */
    @Override
    public double getHalfYearlyCouponRate() {
        return 2.0 / 2;
    }

    /**
     * Gets the amount of money the bond was bought for.
     *
     * @return the amount of money the bond costs.
     */
    @Override
    public double getAmount() {
        return 500;
    }

    /**
     * Gets the name of the bond purchased.
     *
     * @return the name of the bond.
     */
    @Override
    public String getName() {
        return "TEST BOND";
    }

    /**
     * Gets the category of the bond purchased.
     *
     * @return the category of this bond purchased.
     */
    @Override
    public String getCategory() {
        return "bonds";
    }

    /**
     * Sets the bond to a new interest rate.
     *
     * @param newRate new amount of the bond
     */
    @Override
    public void setRate(double newRate) {
        this.rate = 5.0;
    }

    public double getNewRate() {
        return this.rate;
    }

    /**
     * Sets the year of the bond to a new year.
     *
     * @param newYear new name of the bond
     */
    @Override
    public void setYear(int newYear) {
        this.year = 5;
    }

    public int getNewYear() {
        return this.year;
    }

    /**
     * Gets the description of the bond.
     *
     * @return the description of the bond.
     */
    @Override
    public String getBondDescription() {
        return "Name: " + getName()
                + " Amount: $" + new DecimalFormat("0.00").format(getAmount())
                + " Rate: " + new DecimalFormat("0.00").format(getYearlyCouponRate())
                + " Date Purchased: " + getDate()
                + " Number of years: " + getYear();
    }
}
