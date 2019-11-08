package project;

/**
 * State the fund of the project.
 */
public class Fund {
    private double fund;
    private double fundTaken;
    private double fundRemaining;

    public static final double NOFUND = 0.0;

    /**
     * Instantiates the fund object.
     */
    public Fund() {
        this.fund = NOFUND;
        this.fundTaken = 0;
        this.fundRemaining = NOFUND;
    }

    /**
     * set fund (again).
     * @param fund a double number input by the user.
     */
    public void setFund(double fund) {
        this.fund = fund;
        this.fundRemaining = fund - this.fundTaken;
    }

    /**
     * take fund from the total fund.
     * @param amount a double number input by the user.
     */
    public void takeFund(double amount) {
        this.fundTaken = this.fundTaken + amount;
        this.fundRemaining = this.fundRemaining - amount;
    }

    /**
     * add a value to the total fund.
     * @param amount a double number input by the user.
     */
    public void addFund(double amount) {
        this.fund = this.fund + amount;
        this.fundRemaining = this.fundRemaining + amount;
    }

    /**
     * get the private attribute fund.
     * @return the attribute fund.
     */
    public double getFund() {
        return fund;
    }

    /**
     * get the private attribute fundTaken.
     * @return the attribute fundTaken.
     */
    public double getFundTaken() {
        return fundTaken;
    }

    /**
     * get the private attribute fundRemaining.
     * @return the attribute fundRemaining.
     */
    public double getFundRemaining() {
        return fundRemaining;
    }

    /**
     * Return a string to print as message about status of funds.
     * @return String containing a message about fund status
     */
    public String giveFund() {
        return "\t" + "Total Fund = " + getFund() + "\n" + "\t" 
            + "Allocated Fund = " + getFundTaken() + "\n" + "\t" 
            + "Remaining Fund = " + getFundRemaining() + "\n";
    }

    public void loadFund(double fund, double fundTaken, double fundRemaining) {
        this.fund = fund;
        this.fundTaken = fundTaken;
        this.fundRemaining = fundRemaining;
    }
}
