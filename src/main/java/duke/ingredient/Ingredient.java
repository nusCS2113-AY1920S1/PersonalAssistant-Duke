package duke.ingredient;

import duke.exception.DukeException;
import duke.parser.Convert;
import duke.storage.Printable;

import java.util.Date;

public class Ingredient implements Printable {
    private String name;
    private int amount;
    private Date expiryDate;
    private String dateAsString;

    public Ingredient(String name, Integer amount, Date expiryDate) throws DukeException       //beef 200 19/07/2019
    {
        this.name = name;
        if (amount < 0)
            throw new DukeException("The ingredient amount can not be negative, use a valid amount");
        this.amount = amount;
        this.expiryDate = expiryDate;
    }

    public Ingredient(String name, Integer amount, String expiryDate) throws DukeException {
        this(name, amount, Convert.stringToDate(expiryDate));
        dateAsString=expiryDate;
    }

    public int getAmount() {
        return amount;
    }

    public String getName() {
        return name;
    }

    public void changeDate(Date date)       //to change date, we need new date
    {
        this.expiryDate = date;
    }

    public void setName(String name)        //to change name, we need new name
    {
        this.name = name;
    }

    public void changeAmount(Integer amount) //to change amount, we need new amount
    {
        this.amount = amount;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Ingredient && ((Ingredient) other).getName().equals(this.name);
    }

    public String printInFile() {
        return this.getName()+"|"+this.getAmount()+"|"+ dateAsString;
    }
}
