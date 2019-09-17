package command;

import exception.DukeException;
import storage.Storage;
import task.Deadline;
import task.Event;
import task.Task;
import task.TaskList;
import ui.Ui;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class PostponeCommand extends  Command {

    private int indexOfTask;
    private LocalDateTime atDate;
    private LocalDateTime toDate;
    private LocalDateTime fromDate;


    public PostponeCommand(int indexOfTask, LocalDateTime atDate, LocalDateTime fromDate, LocalDateTime toDate){
        this.indexOfTask = indexOfTask;
        this.atDate = atDate;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws DukeException {
        if (indexOfTask < 0 || indexOfTask > (tasks.getSize() - 1)) {
            throw new DukeException(DukeException.TASK_DOES_NOT_EXIST());
        }

        ArrayList<Task> taskList = tasks.getTasks();
        Task taskToBeChanged = taskList.get(indexOfTask);
        String description = taskToBeChanged.description;
        String oldTime = taskToBeChanged.toString();

        if(taskToBeChanged.toString().contains("[E]")){
            Task checkTask = new Event(description,toDate,fromDate);
            if(tasks.isClash(checkTask,"event")){
                throw new DukeException(DukeException.TaskClash());
            }
            else{
                tasks.updateDate(taskToBeChanged,"event",atDate,fromDate,toDate);
                storage.saveFile(tasks.getTasks());
                Ui.printOutput("Got it! I've postponed this event:" + "\n  " + oldTime + "\nNow the tasks details are:\n  " +
                        taskToBeChanged.toString());
            }
        }
        else if(taskToBeChanged.toString().contains("[D]")){
            Task checkTask = new Deadline(description,atDate);
            if(tasks.isClash(checkTask,"deadline")){
                throw new DukeException(DukeException.TaskClash());
            }
            else{
                tasks.updateDate(taskToBeChanged,"deadline",atDate,fromDate,toDate);
                storage.saveFile(tasks.getTasks());
                Ui.printOutput("Got it! I've postponed this deadline:" + "\n  " + oldTime + "\nNow the tasks details are:\n  " +
                        taskToBeChanged.toString());
            }
        }
        else{
            Ui.printOutput("Todo tasks has no schedule");
        }

    }
}
