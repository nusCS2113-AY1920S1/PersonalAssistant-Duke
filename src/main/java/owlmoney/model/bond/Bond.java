package owlmoney.model.bond;

public class Bond {
    private String name;
    private double amount;
    private double rate;
    private String category;

    /**
     * Creates a Bond with details of name, amount, rate.
     *
     * @param name   The name of the bond issue.
     * @param amount The amount of money the bond costs.
     * @param rate   The coupon rate of the bond.
     */
    public Bond(String name, double amount, double rate) {
        this.name = name;
        this.amount = amount;
        this.rate = rate;
        this.category = "bonds";
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
     * Gets the category of the bond purchased
     *
     * @return the category of this bond purchased.
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Sets the bond to a new amount.
     *
     * @param newAmount new amount of the bond
     */
    public void setAmount(double newAmount) {
        this.amount = newAmount;
    }

    /**
     * Sets the name of the bond to a new name.
     *
     * @param newName new name of the bond
     */
    public void setName(String newName) {
        this.name = newName;
    }

}