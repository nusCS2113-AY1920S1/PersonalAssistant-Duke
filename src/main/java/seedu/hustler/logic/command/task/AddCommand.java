package seedu.hustler.logic.command.task;

import seedu.hustler.Hustler;
import seedu.hustler.logic.command.Command;
import seedu.hustler.logic.CommandLineException;
import seedu.hustler.logic.parser.anomaly.AddCommandAnomaly;
import seedu.hustler.ui.Ui;
import seedu.hustler.data.CommandLog;
import seedu.hustler.schedule.Scheduler;
import java.util.List;
import java.util.Arrays;

/**
 * Command that adds task to list.
 */
public class AddCommand extends Command {
    /**
     * Contains task type and description.
     */
    private String[] taskInfo;

    /**
     * Detects anomalies in user input.
     */
    private AddCommandAnomaly anomaly = new AddCommandAnomaly();

    /**
     * Initializes taskInfo.
     *
     * @param taskInfo the info of the task to add.
     */
    public AddCommand(String[] taskInfo) {
        this.taskInfo = taskInfo;
    }
    
    /**
     * Adds task of type and description inside taskInfo.
     */
    public void execute() {
        Ui ui = new Ui();
        if (this.taskInfo.length == 1) {
            ui.empty_description_error();
            CommandLog.deleteLatestLoggedCommand();
            return;
        }
        try {
            anomaly.detect(taskInfo);
        } catch (CommandLineException e) {
            ui.show_message(e.getMessage());
            return;
        }

        List<String> taskDescription = Arrays.asList(taskInfo[1].split(" "));

        if (taskDescription.contains("/by")) {
            Hustler.list.add("deadline", this.taskInfo[1]);
        } else if (taskDescription.contains("/at")) {
            Hustler.list.add("event", this.taskInfo[1]);
        } else {
            Hustler.list.add("todo", this.taskInfo[1]);
        }
    }
}
