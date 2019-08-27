import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeParser {
    public String convertStringToDate (String time) {
        try{
            SimpleDateFormat formatter1 = new SimpleDateFormat("dd/MM/yyyy HHmm");
            Date date = formatter1.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            int actualDate = calendar.get(Calendar.DATE);
            if ((actualDate <= 3) || (actualDate >= 21)) {
                if (actualDate % 10 == 1) {
                    return new SimpleDateFormat("dd'st of' MMMMMMMM yyyy',' hh:mm aaa").format(date);
                } else if (actualDate % 10 == 2) {
                    return new SimpleDateFormat("dd'nd of' MMMMMMMM yyyy',' hh:mm aaa").format(date);
                } else if (actualDate % 10 == 3) {
                    return new SimpleDateFormat("dd'rd of' MMMMMMMM yyyy',' hh:mm aaa").format(date);
                } else {
                    return new SimpleDateFormat("dd'th of' MMMMMMMM yyyy',' hh:mm aaa").format(date);
                }
            } else {
                return new SimpleDateFormat("dd'th of' MMMMMMMM yyyy',' hh:mm aaa").format(date);
            }
        } catch (ParseException e) {
            // cannot convert to date and so return back my time
            return time;
        }
    }

}
