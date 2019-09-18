public class Duke {

    public Ui ui;
    public Storage storage;
    public TaskList tasks;


    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.loadFile());
    }

    public void run() {
        ui.greet();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                if (fullCommand.equals("")) { throw new DukeException(DukeException.ErrorType.COMMAND_EMPTY); }
                Command c = Parser.parse(fullCommand);
                c.execute(ui, tasks, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                e.showError();
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Duke("C:/Users/user/gitclones/main/data/duke.txt").run();

    }

}

