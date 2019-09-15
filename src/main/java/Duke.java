import java.text.ParseException;
import java.util.Date;

import Commands.*;
import ControlPanel.*;
import Tasks.*;

/**
 * The main class which controls the overall flow, run the program
 */
public class Duke{

    private Ui ui;
    private TaskList tasks;
    private EventsList schedule;
    private Storage storage;

    /**
     * Duke class acts as a constructor to initialize and setup
     * @param filePath the path of the tasks.txt which contains the data of the tasks' list
     */
    public Duke(String filePath){
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            schedule = new EventsList();
            tasks = new TaskList(storage.load(schedule));
        } catch (Exception e) {
            ui.showLoadingError();
            tasks = new TaskList();
        }
    }

    /**
     * This method runs the overall program
     */
    public void run(){
        ui.showWelcome();
        boolean isExit = false;
        while(!isExit && ui.inputStatus()){
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (ParseException | DukeException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }

        }
    }

    /**
     * the main class which trigger the program
     * @param args arguments
     * @throws DukeException if any exception is caught
     */
    public static void main(String[] args) throws DukeException {
        new Duke("C:\\Users\\User\\Documents\\GitHub\\main\\data\\tasks.txt").run();
    }

    public static interface Intervals {
        public Date getInterval() {}
    }
}//duke class



