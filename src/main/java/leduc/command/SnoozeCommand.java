package leduc.command;

import leduc.Date;
import leduc.Parser;
import leduc.Ui;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.DeadlinesTask;
import leduc.task.Task;
import leduc.task.TaskList;

import java.io.IOException;

public class SnoozeCommand extends Command{
    public SnoozeCommand(String user){
        super(user);
    }

    public void execute(TaskList tasks, Ui ui , Storage storage, Parser parser) throws NonExistentTaskException, SnoozeTypeException {
        int index = Integer.parseInt(user.substring(7)) - 1;
        if (index > tasks.size() - 1 || index < 0) {
            throw new NonExistentTaskException(ui);
        }
        else { // the tasks exist
            Task snoozeTask = tasks.get(index);
            if (!(snoozeTask instanceof DeadlinesTask)){
                throw new SnoozeTypeException(ui);
            }
            DeadlinesTask snoozeDeadlineTask = (DeadlinesTask) snoozeTask;
            snoozeDeadlineTask.snoozeDeadline();
            String text = storage.getSnoozeTaskString(snoozeDeadlineTask,index,ui,tasks.size());
            //rewriter of file by replacing the whole file
            storage.rewriteFile(text,ui);
            ui.display("\t Noted. I've snoozed this task: \n" +
                    "\t\t "+snoozeDeadlineTask.getTag() + snoozeDeadlineTask.getMark() + " " + snoozeDeadlineTask.getTask()+
                    " by:" + snoozeDeadlineTask.getDeadlines() + "\n");
        }
    }

    public boolean isExit(){
        return false;
    }


}
