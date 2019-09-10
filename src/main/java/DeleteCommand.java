/**
 * Represents a Delete Command.
 */
public class DeleteCommand extends Command {
    /**
     * Constructor of DeleteCommand.
     * @param user String which represent the input string of the user.
     */
    public  DeleteCommand(String user){
        super(user);
    }

    /**
     * Delete the task from the task list and rewrite the data file without the deleted task.
     * @param tasks TaskList which is the list of task.
     * @param ui Ui which deals with the interactions with the user.
     * @param storage Storage which deals with loading tasks from the file and saving tasks in the file.
     * @param parser Parser which deals with making sense of the user command.
     * @throws InexistentTaskException Exception caught when the task to delete does not exist.
     */
    public void execute(TaskList tasks, Ui ui , Storage storage, Parser parser)throws InexistentTaskException{
        int index = Integer.parseInt(user.substring(7)) - 1;
        if (index > tasks.size() - 1 || index < 0) {
            throw new InexistentTaskException(ui);
        }
        else { // the tasks exist
            Task removedTask = tasks.remove(index);
            String text = storage.getDeleteTaskString(removedTask,index,ui,tasks.size());
            //rewriter of file by replacing the whole file
            storage.rewriteFile(text,ui);
            ui.display("\t Noted. I've removed this task: \n" +
                    "\t\t "+removedTask.getTag() + removedTask.getMark() + " " + removedTask.getTask()+
                    "\n\t Now you have "+ tasks.size() +" tasks in the list");
        }
    }

    /**
     * Returns a boolean false as it is a delete command.
     * @return a boolean false.
     */
    public boolean isExit(){
        return false;
    }
}
