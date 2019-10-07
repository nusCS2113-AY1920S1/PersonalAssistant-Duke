import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.concurrent.TimeUnit;

// have a list of all possible objects / all objects currently in inventory [Frisbee, Basketball]
// return /item frisbee /qty 3 /user id1

public class Item extends Resource {
    public Item(String name) {
        super(name);
        this.type = 'I';
        // find some way to generate ID
    }

    public Item(String name, int id, boolean isBooked) {
        super(name, id, isBooked);
        this.type = 'I';
    }

    public Item(String name, int id, boolean isBooked, int loanId, String stringDateFrom, String stringDateTill) throws ParseException {
        super(name, id, isBooked, loanId, stringDateFrom, stringDateTill);
        System.out.println(this.loanId);
        this.type = 'I';
    }
}