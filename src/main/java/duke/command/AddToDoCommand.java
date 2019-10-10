package duke.command;

import duke.tasks.TaskList;
import duke.ui.Ui;
import duke.storage.FileHandling;
import duke.exceptions.DukeException;
import duke.tasks.Todo;

public class AddToDoCommand extends Command {
    private String fullCommand;

    public AddToDoCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public void execute(TaskList tasks,Ui ui,FileHandling storage)throws DukeException {
        try {
            tasks.addTask(new Todo(fullCommand.substring(5)));
            String taskA = tasks.getTask(tasks.numTasks() - 1).toString();
            ui.printAddTask(tasks.getAllTasks(),taskA);
            storage.saveData(tasks);
        } catch (StringIndexOutOfBoundsException e) {
            throw new DukeException(" OOPS! The description of a todo list cannot be empty");
        }
    }
}