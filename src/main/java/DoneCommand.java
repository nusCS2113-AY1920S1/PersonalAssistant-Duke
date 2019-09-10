/**
 * Represents a Delete Command.
 */
public class DoneCommand extends Command {
    /**
     * Constructor of DoneCommand
     * @param user String which represent the input string of the user.
     */
    public  DoneCommand(String user){
        super(user);
    }

    /**
     * Change the mark of a task to done ("[✓]").
     * @param tasks TaskList which is the list of task.
     * @param ui Ui which deals with the interactions with the user.
     * @param storage Storage which deals with loading tasks from the file and saving tasks in the file.
     * @param parser Parser which deals with making sense of the user command.
     * @throws InexistentTaskException Exception caught when the task which is done does not exist.
     */
    public void execute(TaskList tasks, Ui ui , Storage storage, Parser parser) throws InexistentTaskException{
        int index = Integer.parseInt(user.substring(5)) - 1;
        if (index > tasks.size() - 1 || index < 0) {
            throw new InexistentTaskException(ui);
        }
        else { // to change the mark, the whole file is rewritten ( probably a better way to do it)
            tasks.get(index).taskDone();
            //get the String with the index task marked done
            String text = storage.getDoneString(tasks,index,ui);
            //  rewriter of file by replacing the whole file
            storage.rewriteFile(text,ui);
            ui.display("\t Nice! I've marked this task as done:\n\t " + tasks.get(index).getTag() +
                    tasks.get(index).getMark() + " " + tasks.get(index).getTask());
        }
    }

    /**
     * Returns a boolean false as it is a done command.
     * @return a boolean false.
     */
    public boolean isExit(){
        return false;
    }
}
