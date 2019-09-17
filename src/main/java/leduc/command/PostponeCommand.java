package leduc.command;

import leduc.Ui;
import leduc.exception.*;
import leduc.storage.Storage;
import leduc.task.DeadlinesTask;
import leduc.task.Task;
import leduc.task.TaskList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class PostponeCommand extends Command {
    public PostponeCommand(String user){
        super(user);
    }

    public void execute(TaskList tasks, Ui ui , Storage storage) throws NonExistentTaskException,
            DeadlineTypeException, FileException, EmptyDeadlineDateException, NonExistentDateException,
            PostponeDeadlineException {
        String[] postponeString = user.substring(9).split("/by");
        if (postponeString.length == 1) { // no /by in input
            throw new EmptyDeadlineDateException();
        }
        int index = -1;
        try {
            index = Integer.parseInt(postponeString[0].trim()) - 1;
        }
        catch(Exception e){
            throw new NonExistentTaskException();
        }
        if (index > tasks.size() - 1 || index < 0) {
            throw new NonExistentTaskException();
        }
        else { // the tasks exist
            Task postponeTask = tasks.get(index);
            if (!postponeTask.isDeadline()){
                throw new DeadlineTypeException();
            }
            DeadlinesTask postponeDeadlineTask = (DeadlinesTask) postponeTask;
            LocalDateTime d1 = null;
            try{
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm", Locale.ENGLISH);
                d1 = LocalDateTime.parse(postponeString[1].trim(), formatter);
            }catch(Exception e){
                throw new NonExistentDateException();
            }
            postponeDeadlineTask.postponeDeadline(d1);
            storage.save(tasks.getList());
            ui.display("\t Noted. I've postponed this task: \n" +
                    "\t\t "+postponeDeadlineTask.getTag() + postponeDeadlineTask.getMark() + " " + postponeDeadlineTask.getTask()+
                    " by:" + postponeDeadlineTask.getDeadlines() + "\n");
        }
    }

    public boolean isExit(){
        return false;
    }

}
