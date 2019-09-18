package duke.command;

import duke.dukeexception.DukeException;
import duke.storage.Storage;
import duke.task.Task;
import duke.task.TaskList;
import duke.ui.Ui;

public class RemindCommand extends Command{
    protected int taskIndex;
    protected int reminder;

    public RemindCommand(int task, int remind){
        this.taskIndex = task;
        this.reminder = remind;
    }
    @Override
    public void execute(TaskList items, Ui ui){

    }

//    @Override
//    public String executeGui(TaskList items, Ui ui) {
//        return null;
//    }

    @Override
    public void executeStorage(TaskList items, Ui ui, Storage storage){
        Task task = items.get(taskIndex);
        task.setReminder(reminder);
        storage.saveFile(items.getTasks());
        System.out.println(String.format("You will be get a reminder for this task % days", reminder));
        System.out.println(" " + task.toString());
    }

    @Override
    public String executeGui(TaskList items, Ui ui) {
        return null;
    }
}
