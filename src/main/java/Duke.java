
public class Duke{
    private Tasklist tasks;
    private Ui ui;
    private Storage storage;

    public Duke(String filepath){
        ui = new Ui();
        storage = new Storage(filepath);
        try {
            tasks = new Tasklist(storage.load());
        } catch (Exception e) {
            tasks = new Tasklist();
        }
    }

    public void run() throws Exception{
        ui.showWelcome();
        while (!ui.exit()) {
            String fullCommand = ui.readCommand();
            Command c = Parser.parse(fullCommand);
            c.execute(ui, tasks, storage);
        }
        storage.write(tasks.list());
    }

    public static void main(String args[]) throws Exception {
        new Duke("data.txt").run();
    }
}
