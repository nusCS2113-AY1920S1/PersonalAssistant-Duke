package executor.command;

import executor.task.TaskList;
import interpreter.Parser;
import ui.Ui;
import ui.Wallet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CommandSchedule extends Command {
    private String userInput;

    /**
     * Constructor for CommandSchedule subCommand Class.
     * @param userInput String the user input from the CLI
     */
    public CommandSchedule(String userInput) {
        this.userInput = userInput;
        this.commandType = CommandType.VIEWSCHEDULE;
        this.description = "Prints the schedule for the input date \n"
                + "FORMAT :  ";
    }

    @Override
    public void execute(Wallet wallet) {

    }

    @Override
    public void execute(TaskList taskList) {
        String dateInput = Parser.removeStr(this.commandType.toString(), this.userInput);
        printSchedule(dateInput, taskList);
    }

    private void printSchedule(String dateInput, TaskList taskList) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        try {
            LocalDate userDate = LocalDate.parse(dateInput, formatter);
            Ui.dukeSays("Here is your schedule for the following date \n"
                    + dateInput
                    + ":\n");
            for (int index = 0; index < taskList.getSize(); ++index) {
                String taskDate = taskList.getList().get(index).getDate().format(formatter);//creates of copy of datetime in the task
                if (taskDate.equals(userDate.format(formatter))) {
                    taskList.printTaskByIndex(index);
                }
            }
        } catch(Exception e){
            Ui.dukeSays("Please kindly type help to see the format for using Command Schedule");
        }
    }
}
