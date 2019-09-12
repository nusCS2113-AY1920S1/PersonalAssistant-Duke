package duke.command;

import duke.task.TaskList;
import duke.worker.Parser;
import duke.worker.Ui;

public class CommandMarkDone extends Command {
    protected String userInput;

    // Constructor
    public CommandMarkDone(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public void execute(TaskList taskList) {
        try {
            int index = Integer.valueOf(Parser.removeStr("done", this.userInput)) - 1;
            taskList.getList().get(index).markDone();
            Ui.dukeSays("Alrighty, I've marked task '"
                    + String.valueOf(index + 1)
                    + ") "
                    + taskList.getList().get(index).taskName
                    + "' as done!"
            );
        } catch (Exception e) {
            Ui.dukeSays("Invalid 'done' statement. Please indicate the index of the task you wish to mark done.");
        }
    }
}
