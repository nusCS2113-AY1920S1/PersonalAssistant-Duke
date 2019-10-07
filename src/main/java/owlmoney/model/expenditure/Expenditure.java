package owlmoney.model.expenditure;

/**
 * The Expenditure class that stores details of each expenditure.
 */

public class Expenditure {

    private String description;
    private double amount;
    private String date;
    private String category;

    /**
     * Creates an instance of a expenditure object.
     *
     * @param description The description that describes this expenditure.
     * @param amount      The amount of money spent in this instance of expenditure.
     * @param date        The date when this expenditure was made.
     * @param category    The category that this expenditure can be tagged to.
     */
    public Expenditure(String description, double amount, String date, String category) {
        this.description = description;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }

    /**
     * Gets the amount of money spent in this transaction.
     *
     * @return The amount of money spent in this transaction.
     */
    public double getAmount() {
        return this.amount;
    }

    /**
     * Gets the description of the instance of expenditure.
     *
     * @return The description of the expenditure.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Gets the date that this expenditure was made.
     *
     * @return The date that the expenditure was made.
     */
    public String getDate() {
        return this.date;
    }

    /**
     * Gets the category of that the expenditure was tagged to.
     *
     * @return The category of the expenditure that it was tagged to.
     */
    public String getCategory() {
        return this.category;
    }

    String getDetails() {
        return "Description: " + this.description + "\nAmount: " + this.amount + "\nDate: " + this.date.toString()
                + "\nCategory: " + category;
    }
}