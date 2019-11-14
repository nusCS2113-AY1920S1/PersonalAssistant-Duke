package executor.command;

import duke.exception.DukeException;
import interpreter.Parser;
import storage.StorageManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CommandSchedule extends Command {
    private String userInput;

    /**
     * Constructor for CommandSchedule subCommand Class.
     * @param userInput String the user input from the CLI
     */
    public CommandSchedule(String userInput) {
        super();
        this.userInput = userInput;
        this.commandType = CommandType.VIEWSCHEDULE;
        this.description = "Prints the schedule for the input date \n"
                + "FORMAT: Viewschedule dd/mm/yy";
    }

    @Override
    public void execute(StorageManager storageManager) {
        String dateInput = Parser.removeStr(this.commandType.toString(), this.userInput);
        String outputStr;
        try {
            outputStr = printSchedule(dateInput, storageManager);
        } catch (DukeException e) {
            this.infoCapsule.setCodeError();
            this.infoCapsule.setOutputStr(e.getMessage());
            return;
        }
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(outputStr);
    }

    private String printSchedule(String dateInput, StorageManager storageManager) throws DukeException {
        StringBuilder outputStr = new StringBuilder();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        try {
            LocalDate userDate = LocalDate.parse(dateInput, formatter);
            outputStr.append("Here is your schedule for the following date: \n")
                    .append(dateInput)
                    .append(":\n");
            for (int index = 0; index < storageManager.getTaskListSize(); ++index) {
                outputStr.append(storageManager.getTasksByDate(userDate).getPrintableTasks()).append("\n");
            }
        } catch (DukeException e) {
            throw e;
        } catch (Exception e) {
            throw new DukeException("Please kindly type help to see the format for using Command Schedule");
        }
        return outputStr.toString();
    }
}
