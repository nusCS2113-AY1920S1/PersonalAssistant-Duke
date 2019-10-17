package duke.ingredients;
import duke.Duke;
import java.text.SimpleDateFormat;
import java.util.Date;
import duke.parser.Convert;

public abstract class Ingredients
{
    private Integer Nb;
    private String name;
    private Integer amount;
    private Date date = new Date();
    public Ingredients(String ingredients, Integer amount, Date date)       //beef 200 19/07/2019
    {
        this.name = ingredients;
        this.amount = amount;
        this.date = date;
    }
    public void changeDate(Date date)       //to change date, we need new date
    {
        this.date = date;
    }
    public void setName(String name)        //to change name, we need new name
    {
        this.name = name;
    }
    public void changeAmount(Integer amount) //to change amount, we need new amount
    {
        this.amount = amount;
    }
    //private String pattern = "dd/MM/yyyy";
    //private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
    //private String TodayDate = simpleDateFormat.format(today);
}
