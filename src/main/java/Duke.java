import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Date;

import Commands.*;
import ControlPanel.*;
import Money.*;
import MoneyCommands.MoneyCommand;
import Tasks.*;

/**
 * The main class which controls the overall flow, run the program
 */
public class Duke{

    private Ui ui;
    private TaskList tasks;
    private Storage storage;
    private Account account;

    /**
     * Duke class acts as a constructor to initialize and setup
     * @param filePath the path of the tasks.txt which contains the data of the tasks' list
     */
    public Duke(String filePath){
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
            account = new Account();
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
        if(account.isToInitialize()){
            System.out.println("You're a first time user, Please Enter your existing savings and Avg monthly expenditure");
            while(ui.inputStatus()){
                String initCommand = ui.readCommand();
                float userSavings = Float.parseFloat(initCommand.split(" ")[0]);
                float avgExp = Float.parseFloat(initCommand.split(" ")[1]);
                account.Initialize(userSavings,avgExp);
                System.out.println("You're ready to use Financial Ghosts");
                break;
            }
        }

        boolean isExit = false;
        System.out.println("Let's Go");
        while(!isExit && ui.inputStatus()){
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
//                Command c = Parser.parse(fullCommand);
//                c.execute(tasks, ui, storage);
//                isExit = c.isExit();
                MoneyCommand mc = Parser.moneyParse(fullCommand);
                mc.execute(account, ui, storage);
                isExit = mc.isExit();
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
        Path currentDir = Paths.get("data/tasks.txt");
        String file = currentDir.toAbsolutePath().toString();
        new Duke(file).run();
    }

}//duke class



