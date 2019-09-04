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
            storage.saveData(tasks.getAllTasks());
        } catch (StringIndexOutOfBoundsException e) {
            throw new DukeException(" OOPS! The description of a todo list cannot be empty");
        }
    }
}