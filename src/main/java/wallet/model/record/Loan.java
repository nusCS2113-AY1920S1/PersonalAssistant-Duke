package wallet.model.record;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class Loan extends Record {
    String description;
    private double amount;
    //Contact person;
    LocalDate createdDate;
    private boolean isLend;
    private boolean isSettled;


    /**
     * The constructor for Loan objects.
     *
     * @param description The description of the loan.
     * @param amount      The amount of money lent to /borrowed from the Contact person.
     * @param isLend      If isLend is true, then it means you lend people money. Else, it means you borrow from people.
     * @param isSettled   If isSettled is true, then it means the loan has been settled.
     */

    public Loan(String description, LocalDate createdDate, double amount,
                boolean isLend, boolean isSettled) {
        super(description, createdDate);
        this.description = description;
        this.amount = amount;
        this.createdDate = createdDate;
        this.isLend = isLend;
        this.isSettled = isSettled;
    }

    /**
     * Returns the amount of loan.
     *
     * @return The amount of loan.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Sets the amount of the loan.
     *
     * @param amount The amount of the loan.
     */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /**
     * Returns true or false.
     *
     * @return true or false.
     */
    public boolean isLend() {
        return this.isLend;
    }

    /**
     * Sets isLend to true or false.
     *
     * @param isLend States whether user is borrowing or lending.
     */
    public void setLend(boolean isLend) {
        this.isLend = isLend;
    }

    /**
     * Checks if user has settled the loan.
     *
     * @return True or false - Whether the user has settled the loan.
     */
    public boolean isSettled() {
        return isSettled;
    }

    /**
     * Allows user to mark loans as settled/unsettled.
     *
     * @param isSettled Marks loans as settled or unsettled.
     */
    public void setIsSettled(boolean isSettled) {
        this.isSettled = isSettled;
    }

    @Override
    public String toString() {
        if (isLend) {
            return "[ID: " + getId() + "]" + "[" + (isSettled ? "Settled" : "Not Settled") + "]" + "[Lend] " + description + " Amount:$"
                    + amount + " Date:" + DateTimeFormatter.ofPattern("dd MMM yyyy").format(getDate());
        } else { //isBorrow
            return "[ID: " + getId() + "]" + "[" + (isSettled ? "Settled" : "Not Settled") + "]" + "[Borrow] " + description + " Amount:$"
                    + amount + " Date:" + DateTimeFormatter.ofPattern("dd MMM yyyy").format(getDate());
        }
    }

    @Override
    public String writeToFile() {
        if (!isLend && !isSettled) {
            return getId() + "," + getDescription() + "," + amount + ","
                    + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(getDate()) + "," + "0" + "," + "0";
        } else if (!isLend && isSettled) {
            return getId() + "," + getDescription() + "," + amount + ","
                    + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(getDate()) + "," + "0" + "," + "1";
        } else if (isLend && !isSettled) {
            return getId() + "," + getDescription() + "," + amount + ","
                    + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(getDate()) + "," + "1" + "," + "0";
        } else if(isLend && isSettled) {
            return getId() + "," + getDescription() + "," + amount + ","
                    + DateTimeFormatter.ofPattern("dd/MM/yyyy").format(getDate()) + "," + "1" + "," + "1";
        }
        return null;
    }
}
