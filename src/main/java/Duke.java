public class Duke {
    private Ui ui;
    private FileHandling storage;
    private TaskList tasks;

    public Duke (String filename) {
        try {
            ui = new Ui();
            storage = new FileHandling(filename);
            tasks = new TaskList(storage.retrieveData());
        } catch (DukeException e) {
            ui.showLoadingError(e.getMessage());
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.printDash();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (DukeException e) {
                ui.showError(e.getMessage());
            }
        }
    }

    public static void main (String[] args) {
        new Duke("storeData.txt").run();
    }
}
