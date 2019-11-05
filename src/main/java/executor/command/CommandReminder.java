package executor.command;

import executor.task.Task;
import executor.task.TaskList;
import ui.Ui;
import ui.Wallet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CommandReminder extends Command {

    private LocalDate currentDate;

    /**
     * Constructor for CommandReminder subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandReminder(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.REMINDER;
        this.description = "Loops through list and checks if current date matches date linked with task and prints it";
        this.currentDate = LocalDate.now();
    }


    @Override
    public void execute(Wallet wallet) {

    }

    @Override
    public void execute(TaskList taskList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        try {
            for (Task task : taskList.getList()) {
                String dateOfTask = task.getDate().format(formatter);
                if (dateOfTask.equals(this.currentDate.format(formatter))) {
                        Ui.dukeSays(task.genTaskDesc());
                        Ui.printSeparator();
                    }
                }
            } catch (Exception e) {
            Ui.dukeSays("Either there are no tasks for today OR \n"
                    + "Please enter the correct format for reminder available if you"
                    + "type help on the CLI ! \n");
        }
    }
}
