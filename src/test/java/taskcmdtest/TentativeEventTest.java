
package taskcmdtest;//@@author mononokehime14

import gazeeebo.tasks.TentativeEvent;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TentativeEventTest {
    @Test
    public void testListFormat() {
        String description = "return book";
        ArrayList<String> tentativetimes = new ArrayList<String>();
        tentativetimes.add("2008-06-07 05:05:05-08:08:08");
        tentativetimes.add("2007-03-02 04:04:04-10:10:10");
        TentativeEvent newtentative = new TentativeEvent(description,tentativetimes);
        String timeslots = "[TE]" + "[" + newtentative.getStatusIcon() + "] " + description + "\n";
        for (int i = 0; i < tentativetimes.size(); i++) {
            DateTimeFormatter fmtED = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter fmtET = DateTimeFormatter.ofPattern("HH:mm:ss");
            String[] dateTime = tentativetimes.get(i).split(" ");
            String[] time = dateTime[1].split("-");
            String datestring = LocalDate.parse(dateTime[0], fmtED)
                    .format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM));
            String output = datestring + " " +  LocalTime.parse(time[0], fmtET).format(fmtET) + "-"
                    + LocalTime.parse(time[1], fmtET).format(fmtET) + ")";
            if (i == 0) {
                timeslots += "at " + output + "\n";
            } else {
                timeslots += "or " + output + "\n";
            }
        }
        assertEquals(newtentative.listFormat(),timeslots);
    }
}