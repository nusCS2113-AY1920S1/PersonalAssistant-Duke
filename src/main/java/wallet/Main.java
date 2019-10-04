package wallet;

import wallet.logic.LogicManager;
import wallet.ui.Reminder;
import wallet.ui.Ui;

public class Main {
    /**
     * The Ui object that handles input and output of the application.
     */
    private Ui ui;
    /**
     * The TaskList object that handles the list of task added by the user.
     */
    private LogicManager logicManager;
    /**
     * The Reminder object that handles the reminder of undone tasks.
     */
    private Reminder reminder;

    /**
     * Constructs a new Main object.
     */
    public Main() {
        ui = new Ui();
        logicManager = new LogicManager();
        reminder = new Reminder(logicManager.getWallet());
    }

    public static void main(String[] args) {
        new Main().run();
    }

    /**
     * Execute and run the Duke application.
     */
    public void run() {
        ui.welcomeMsg();
        boolean isExit = false;
        while (!isExit) {
            String fullCommand = ui.readLine();
            ui.printLine();
            isExit = logicManager.execute(fullCommand);
            ui.printLine();
        }
        ui.byeMsg();
        reminder.autoRemindStop();
    }
}
