import java.util.List;

class DoneCommand extends Command {
    private List<String> splitInput;

    public DoneCommand(List<String> splitInput) {
        this.splitInput = splitInput;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, FileHandling storage)throws DukeException {
        try {
            int temp = Integer.parseInt(splitInput.get(1)) - 1;
            tasks.getTask(temp).markAsDone();
            ui.printMarkAsDone(tasks.getTask(temp).toString());
        } catch (NumberFormatException obj) {
            throw new DukeException(" OOPS! Enter a positive integer after \"done\"");
        } catch (IndexOutOfBoundsException obj) {
            throw new DukeException(" OOPS! Enter a number that is present in the list");
        }
    }
}