import Storage.Storage;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.TentativeEvent;
import UI.Ui;
import commands.DeadlineCommand;
import commands.SnoozeCommand;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import Exception.DukeException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TentativeEventTest {
    @Test
    public void testTentativeEvent() throws ParseException,IOException,DukeException{
        String description = "return book";
        ArrayList<Date> tentativeoptions = new ArrayList<Date>();
        ArrayList<String> tentativetimes = new ArrayList<String>();

        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tentativeoptions.add(fmt.parse("2008-6-7 5:5:5"));
        tentativeoptions.add(fmt.parse("2007-3-2 4:4:4"));
        tentativetimes.add("2008-6-7 5:5:5");
        tentativetimes.add("2007-3-2 4:4:4");
        TentativeEvent newtentative = new TentativeEvent(description, tentativeoptions,tentativetimes);
        String timeslots = "[TE]"+ "[" + newtentative.getStatusIcon() + "] "+description+"\n" ;
        for (int i = 0; i < tentativetimes.size(); i++) {
            if(i==0){
                timeslots += "at "+tentativetimes.get(i)+"\n";
            }else{
                timeslots += "or "+tentativetimes.get(i)+"\n";
            }
        }

        assertEquals(newtentative.listformat(),timeslots);
    }
}