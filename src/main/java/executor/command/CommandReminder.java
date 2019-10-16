package executor.command;

import executor.task.Task;
import executor.task.TaskList;
import ui.Ui;
import ui.Wallet;

import java.util.Calendar;
import java.util.Date;

public class CommandReminder extends Command {
    //private String[] reminders;
    protected Date currentDate = Calendar.getInstance().getTime();

    public CommandReminder() {
        this.currentDate.setTime(0);
    }

    @Override
    public void execute(Wallet wallet) {

    }

    @Override
    public void execute(TaskList taskList) {
        try {
            for (Task task : taskList.getList()) {
                Date dateCopy = task.getDatetime();
                if (dateCopy != null) {
                    dateCopy.setTime(0);
                    if (dateCopy.equals(this.currentDate)) {
                        Ui.dukeSays(task.genTaskDesc());
                        Ui.printSeparator();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("sorry");
        }
    }
}
