package executor.command;

import duke.exception.DukeException;
import storage.StorageManager;
import java.time.LocalDate;

public class CommandReminder extends Command {

    private LocalDate currentDate;

    /**
     * Constructor for CommandReminder subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandReminder(String userInput) {
        super();
        this.userInput = userInput;
        this.commandType = CommandType.REMINDER;
        this.currentDate = LocalDate.now();
        this.description = "Loops through list and checks if current "
                + "date matches date linked with task and prints it \n"
                + "FORMAT :  reminder ";

    }

    @Override
    public void execute(StorageManager storageManager) {
        String outputStr;
        try {
            outputStr = storageManager.getTasksByDate(LocalDate.now()).getPrintableTasks();
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        }
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(outputStr);
    }
}
