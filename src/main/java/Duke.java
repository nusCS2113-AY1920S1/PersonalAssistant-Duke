import command.Command;
import exception.DukeException;
import storage.Storage;
import task.TaskList;
import ui.Ui;
import java.util.Timer;
import java.util.TimerTask;

public class Duke {

    public Ui ui;
    public Storage storage;
    public TaskList tasks;
    public Reminders reminders;


    public Duke(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        tasks = new TaskList(storage.loadFile());
        reminders = new Reminders();
    }

    public void run() {
        ui.greet();
        boolean isExit = false;
        while (!isExit) {
            try {
                Timer t = new Timer();
                t.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        reminders.checkForReminders(storage);
                    }
                }, 0, 1000);

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
        new Duke("data/duke.txt").run();
    }
}

