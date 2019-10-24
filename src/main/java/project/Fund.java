package project;

/**
 * State the fund of the project
 */
public class Fund {
    private double fund;
    private double fundTaken;
    private double fundRemaining;

    /**
     * Instantiates the fund object.
     */
    public Fund( ) {
        this.fund = -1;
        this.fundTaken = 0;
        this.fundRemaining = fund;
    }

    /**
     * set fund (again)
     * @param fund a double number input by the user.
     */
    public void setFund(double fund) {
        this.fund = fund;
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
    public double getFundRemaining(){
        return fundRemaining;
    }


    public String giveFund(){
        return "\t" + "Total Fund = " + getFund() + "\n" +
                "\t" + "Allocated Fund = " + getFundTaken() + "\n" +
                "\t" + "Remaining Fund = " + getFundRemaining() + "\n";
    }
}
