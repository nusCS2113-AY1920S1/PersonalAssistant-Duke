package command;

import process.DukeException;
import process.Storage;
import process.Ui;
import task.Deadline;
import task.Event;
import task.TaskList;

import java.lang.reflect.Field;
import java.text.FieldPosition;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class RecurringCommand extends Command {
    private String description;
    private String tasktype;
    private String datetime;
    String type;

    public RecurringCommand(String tasktype, String description, String datetime, String type) {
        this.description = description;
        this.tasktype = tasktype;
        this.datetime = datetime;
        this.type = type;
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws DukeException, ParseException {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy hhmm");
            Calendar c = Calendar.getInstance();
            c.setTime((sdf.parse(datetime)));
            int times = 0; int increment = 0;
            if (type.equals("daily")) {
                times = 7; increment = 1;
            } else if (type.equals("weekly")) {
                times = 4; increment = 7;
            }
            if (tasktype.equals("deadline")) tasks.add(new Deadline(description, datetime, false));
            else if (tasktype.equals("event")) tasks.add(new Event(description, datetime, false));
            for (int i = 1; i < times; i++) {
                c.add(Calendar.DATE, increment);
                StringBuffer stringBuffer = new StringBuffer();
                sdf.format(c.getTime(), stringBuffer, new FieldPosition(0));
                if (tasktype.equals("deadline")) {
                    tasks.add(new Deadline(description, stringBuffer.toString(), false));
                } else if (tasktype.equals("event")) {
                    tasks.add(new Event(description, stringBuffer.toString(), false));
                }
            }
        storage.save(tasks);
        return ui.showTaskAdded(tasks.get(tasks.size()-1).toString(), tasks.size());
    }
}
