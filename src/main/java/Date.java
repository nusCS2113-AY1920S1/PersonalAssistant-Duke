import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

/**
 * Represents a date and contains a GregorianCalendar type field.
 */
public class Date {
    private GregorianCalendar d;

    /**
     * Constructor of Date.
     * @param d GregorianCalendar which is a date.
     */
    public Date(GregorianCalendar d){
        this.d = d;
    }

    /**
     * Returns a String representing a date.
     * @return a String representation of date.
     */
    @Override
    public String toString(){
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        fmt.setCalendar(this.d);
        String dateFormatted = fmt.format(this.d.getTime());
        return  dateFormatted; //no need secondes and time zone
    }
}
