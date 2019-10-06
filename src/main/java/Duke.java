import Dictionary.WordBank;
import command.Command;
import exception.CommandEmptyException;
import storage.Storage;
import ui.Ui;

public class Duke {

    public Ui ui;
    public Storage storage;
    public WordBank wordBank;


    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        wordBank = new WordBank();
    }

    public void run() {
        ui.greet();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                if (fullCommand.equals("")) { throw new CommandEmptyException(); }
                Command c = Parser.parse(fullCommand);
                c.execute(ui, wordBank, storage);
                isExit = c.isExit();
            } catch (CommandEmptyException e) {
                e.showError();
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Duke("data/duke.txt").run();
    }
}

