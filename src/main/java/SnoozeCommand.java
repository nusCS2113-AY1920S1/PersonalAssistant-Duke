/**
 * Postpone the date of a task.
 * snooze [index] [DD/MM/YYYY HHMM]
 * @author Chelsea
 */
public class SnoozeCommand extends Command{

    protected int index;
    protected String date;
    SnoozeCommand(String num, String date) {
        num = num.trim();
        this.index = Integer.parseInt(num)-1;
        this.date = date;
    }

    @Override
    public void execute(Ui ui, TaskList tasks, Storage storage) {
        if(tasks.getTaskItem(this.index).getType().equals("T")){
            ui.noSnooze(tasks.getTaskItem(index));
            return;
        }
        System.out.println(this.date);
        tasks.getTaskItem(this.index).modifyDate(this.date);
        ui.showSnooze(tasks.getTaskItem(index));
        storage.writeFile(tasks.getArrayData(), false);

    }
}
