package duke.ingredient;

import duke.exception.DukeException;
import duke.list.IngredientsList;
import duke.parser.Convert;
import duke.storage.Printable;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Represents objects of Ingredients with name, amount and expirydate.
 * Used by {@link IngredientsList}s
 *
 * @@author x3chillax
 */
public class Ingredient implements Printable {
    private String name;
    private int amount;
    private Date expiryDate;
    private String dateAsString;

    /**
     * Constructor of the class {@link Ingredient}
     * Creates a new {@link Ingredient}
     *
     * @param name       the name of the {@link Ingredient}
     * @param amount     the amount of the {@link Ingredient}
     * @param expiryDate the expiry date of the {@link Ingredient}
     */
    public Ingredient(String name, Integer amount, Date expiryDate) throws DukeException       //beef 200 19/07/2019
    {
        this.name = name;
        if (amount < 0)
            throw new DukeException("The ingredient amount cannot be negative, use a valid amount");
        this.amount = amount;
        this.expiryDate = expiryDate;
    }

    public Ingredient(String name, Integer amount, String expiryDate) throws DukeException {
        this(name, amount, Convert.stringToDate(expiryDate));
        dateAsString = expiryDate;
    }

    /**
     * Assigns a new date to {@link Ingredient}.
     *
     * @param date, the new date we want to set to the {@link Ingredient}
     */
    public void setDate(Date date)       //to change date, we need new date
    {
        this.expiryDate = date;
    }

    /**
     * Returns the amount attribute of the {@link Ingredient}.
     */
    public int getAmount() {
        return amount;
    }

    /**
     * Returns the name attribute of the {@link Ingredient}.
     */
    public String getName() {
        return name;
    }

    /**
     * Assigns a new name to {@link Ingredient}.
     *
     * @param name, the new name we want to set to the {@link Ingredient}
     */
    public void setName(String name)        //to change name, we need new name
    {
        this.name = name;
    }

    /**
     * Assigns a new amount to {@link Ingredient}.
     *
     * @param amount, the new amount we want to set to the {@link Ingredient}
     */
    public void setAmount(Integer amount) //to change amount, we need new amount
    {
        this.amount = amount;
    }

    /**
     * Returns the expiryDate of {@link Ingredient}.
     */
    public Date getExpiryDate() {
        return expiryDate;
    }

    @Override
    /**
     * Checks if 2 {@link Ingredient} are the same. The ingredients are considered 'equal' if their names are the same,
     * Returns True: If they have the same name.
     * Returns False: Otherwise.
     *
     * @param other {@link Ingredient} to be compared
     */
    public boolean equals(Object other) {
        return other instanceof Ingredient && ((Ingredient) other).getName().equals(this.name);
    }

    /**
     * Returns a nicely formatted string of name,amount and expiry dates of {@link Ingredient} to show as an output.
     * Different output for an expired ingredient as compared to an non-expired ingredient
     */
    public String toString() {
        return !this.isExpired() ? name + ", amount is: " + amount + ", expiring on " + Convert.getDateString(expiryDate, dateAsString) : "WARNING! expired ingredient: " + name + ", amount is: " + amount + ", expired on " + Convert.getDateString(expiryDate, dateAsString);
    }

    /**
     * Returns a nicely formatted string of name,amount and expiry dates of {@link Ingredient} to show as an output,
     * without the warning label.
     * Different output for an expired ingredient as compared to an non-expired ingredient(expired on/expiring on)
     */
    public String toStringNoWarning() {
        String representation = isExpired() ? ", expired on " : ", expiring on ";
        return name + ", amount: " + amount + representation + Convert.getDateString(expiryDate, dateAsString);
    }

    /**
     * Returns a nicely formatted string of name and amount of {@link Ingredient} to show as an output.
     */
    public String toStringWithoutDate() {
        return name + ", amount: " + amount;
    }

    /**
     * Checks if an {@link Ingredient} is expired
     * Returns True: If Ingredient is expired
     * Returns False: Otherwise
     */
    public boolean isExpired() {
        return !expiryDate.after(new Date());
    }

    /**
     * Checks if the date of an {@link Ingredient} is the same as today's date
     * Returns True: Date in question is today's date
     * Returns False: Otherwise.
     *
     * @param DateInQuestion, the date that we want to check
     */
    public boolean isExpiredToday(String DateInQuestion) {

        Date today = new Date();
        String pattern = "dd/MM/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String TodayDate = simpleDateFormat.format(today);
        return ((DateInQuestion).equals(TodayDate));
    }

    /**
     * Checks if 2 {@link Ingredient} are the same. The ingredients are considered 'equal completely' if their names
     * and expiry dates are the same
     * Returns True: If they have the same name and expiry dates
     * Returns: False otherwise.
     *
     * @param other {@link Ingredient} to be compared
     */
    public boolean equalsCompletely(Ingredient other) {
        return this.equals(other) && this.getExpiryDate().equals(other.getExpiryDate());
    }

    /**
     * Returns a nicely formatted string of {@link Ingredient}'s name, amount and date
     */
    public String printInFile() {
        return this.getName() + "|" + this.getAmount() + "|" + dateAsString;
    }

}
