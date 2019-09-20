package wallet.model.record;

import wallet.model.contact.Contact;

import java.time.LocalDate;

public class Loan extends Record {
    private double amount;
    private Contact person;
    private boolean isLend;
    private boolean isSettled;

    /**
     * The constructor for Loan objects.
     *
     * @param amount The amount of money lent to /borrowed from the Contact person.
     * @param person The Contact person.
     * @param isLend If isLend is true, then it means you lend people money. Else, it means you borrow from people.
     * @param isSettled If isSettled is true, then it means the loan has been settled.
     */

    public Loan(String description, LocalDate createdDate, double amount,
                Contact person, boolean isLend, boolean isSettled) {
        super(description, createdDate);
        this.amount = amount;
        this.person = person;
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
     * Returns the Contact object.
     *
     * @return The Contact object.
     */
    public Contact getPerson() {
        return person;
    }

    /**
     * Sets the Contact Object.
     *
     * @param person The Contact Object.
     */
    public void setPerson(Contact person) {
        this.person = person;
    }

    /**
     * Returns true or false.
     *
     * @return true or false.
     */
    public boolean isLend() {
        return isLend;
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
}
