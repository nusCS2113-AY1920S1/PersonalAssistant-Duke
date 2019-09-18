package duke.command;

import duke.exception.DukeException;
import duke.task.TaskList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class CommandReminder extends Command {
    //private String[] reminders;
    Date date = Calendar.getInstance().getTime();
    DateFormat dateFormat = new SimpleDateFormat("dd/mm/yyyy HHmm");
    private String strDate = dateFormat.format(date);


    @Override
    public void execute(TaskList taskList) {
        try {
            for (int i = 0; i < taskList.getSize(); i++) {

                System.out.println(taskList.getList().get(i).genTaskDesc());
            }
        } catch (Exception e){
            System.out.println("sorry");
        }
    }


}
