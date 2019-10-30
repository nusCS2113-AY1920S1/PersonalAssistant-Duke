package seedu.hustler.logic.command.task;

import seedu.hustler.Hustler;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.parser.anomaly.OneWordAnomaly;
import seedu.hustler.task.Reminders;
import seedu.hustler.ui.Ui;

/**
 * Command that executes reminders.
 */
public class RemindCommand extends Command {

    private final String[] userInput;
    private OneWordAnomaly remindAnomaly = new OneWordAnomaly();

    public RemindCommand(String[] userInput) {
        this.userInput = userInput;
    }

    /**
     * Executes remind pipeline.
     */
    public void execute() {
        Ui ui = new Ui();
        try {
            remindAnomaly.detect(userInput);
            Reminders.runAll(Hustler.list);
            Reminders.displayReminders();
        } catch (CommandLineException e) {
            ui.showMessage(e.getMessage());
        }

    } 
}
