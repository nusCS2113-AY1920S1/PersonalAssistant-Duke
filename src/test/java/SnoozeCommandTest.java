//import Storage.Storage;
//import Tasks.Deadline;
//import Tasks.Task;
//import UI.Ui;
//import commands.DeadlineCommand;
//import commands.SnoozeCommand;
//import org.junit.jupiter.api.Test;
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//import Exception.DukeException;
//
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class SnoozeCommandTest {
//    @Test
//    public void testExecuteSnooze() throws ParseException,IOException,DukeException{
//        DeadlineCommand deadlineCommand = new DeadlineCommand();
//        SnoozeCommand snoozeCommand = new SnoozeCommand();
//        ArrayList<Task> tasks = new ArrayList<Task>();
//        Ui ui = new Ui();
//        Storage storage = new Storage();
//        ui.FullCommand = "deadline return book /by 2008-07-07 3:3:3";
//        deadlineCommand.execute(tasks,ui,storage);
//        ui.FullCommand = "snooze 1 1 1 1 1";
//        int index = Integer.parseInt(ui.FullCommand.split(" ")[1]) - 1;
//        int year = Integer.parseInt(ui.FullCommand.split(" ")[2]);
//        int day = Integer.parseInt(ui.FullCommand.split(" ")[4]);
//        int month =Integer.parseInt(ui.FullCommand.split(" ")[3]);
//        int hour = Integer.parseInt(ui.FullCommand.split(" ")[5]);
//        String Decription = tasks.get(index).description;
//        SimpleDateFormat fmt = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
//        Date initial = fmt.parse(tasks.get(index).toString().split("\\|")[3].substring(3).trim());
//        Calendar rightNow = Calendar.getInstance();
//        rightNow.setTime(initial);
//        if(year >0) rightNow.add(Calendar.YEAR,year);
//        if(month>0) rightNow.add(Calendar.MONTH,month);
//        if(day > 0) rightNow.add(Calendar.DAY_OF_YEAR,day);
//        if(hour > 0) rightNow.add(Calendar.HOUR,hour);
//        Date after = rightNow.getTime();
//        Task snoozedDeadline = new Deadline(Decription,after);
//        tasks.add(snoozedDeadline);
//        assertEquals(tasks.get(1).listFormat(),"[D]" + "[" + tasks.get(1).getStatusIcon() + "]" + "return book " + "(by:" + "Sat Aug 08 04:03:03 SGT 2009" + ")");
//    }
//}