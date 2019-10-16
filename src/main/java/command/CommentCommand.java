package command;

import exception.DukeException;
import storage.Storage;
import task.Task;
import task.TaskList;
import ui.Ui;

/**
 * Used to add a comment to an existing task.
 * 
 * @author Hans Kurnia
 * @version 1.2
 */
public class CommentCommand extends Command {
    private int indexOfTask;
    private String comment;

    /**
     * creates the command object.
     * 
     * @param com comment inputted by user
     */
    public CommentCommand(int indexOfTask, String com) {
        this.indexOfTask = indexOfTask;
        this.comment = com;
    }

    @Override
    public void execute(TaskList tasks, Storage storage) throws DukeException {
        if (indexOfTask >= 0 && indexOfTask <= (tasks.getSize() - 1)) {
            Task taskToEdit = tasks.editTaskComment(indexOfTask, comment);
            storage.saveFile(tasks.getTasks());
            Ui.printOutput("Noted. Your new task comment is:" + "\n " + taskToEdit.comment);
        } else {
            throw new DukeException(DukeException.taskDoesNotExist());
        }
    }

    public int getIndexOfTask() {
        return indexOfTask;
    }

    public String getComment() {
        return comment;
    }

}