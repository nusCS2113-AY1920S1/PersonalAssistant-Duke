import Storage.Storage;
import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import UI.Ui;
import commands.DeadlineCommand;
import commands.SnoozeCommand;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import Exception.DukeException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SnoozeCommandTest {
    @Test
    public void testExecuteSnooze() throws ParseException,IOException,DukeException{
        DeadlineCommand deadlineCommand = new DeadlineCommand();
        SnoozeCommand snoozeCommand = new SnoozeCommand();
        ArrayList<Task> tasks = new ArrayList<Task>();
        Ui ui = new Ui();
        Storage storage = new Storage();
        ui.FullCommand = "deadline return book /by 2008-7-7 3:3:3";
        deadlineCommand.execute(tasks,ui,storage);
        ui.FullCommand = "snooze 1";
        int index = Integer.parseInt(ui.FullCommand.substring(6).trim()) - 1;
        String Decription = tasks.get(index).description;
        SimpleDateFormat fmt = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        Date initial = fmt.parse(tasks.get(index).toString().split("\\|")[3].substring(3).trim());
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(initial);
        int year = 1,month=1,day=1,hour=1;
        if(year >0) rightNow.add(Calendar.YEAR,year);
        if(month>0) rightNow.add(Calendar.MONTH,month);
        if(day > 0) rightNow.add(Calendar.DAY_OF_YEAR,day);
        if(hour > 0) rightNow.add(Calendar.HOUR,hour);
        Date after = rightNow.getTime();
        Task snoozedDeadline = new Deadline(Decription,after);
        tasks.add(snoozedDeadline);
        assertEquals(tasks.get(1).listformat(),"[D]" + "[" + tasks.get(1).getStatusIcon() + "]" + "return book " + "(by:" + "Sat Aug 08 04:03:03 SGT 2009" + ")");
    }
}