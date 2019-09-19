package duke.command;

import duke.task.TaskList;
import duke.worker.Parser;
import duke.worker.Ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CommandSchedule extends Command {
    protected String userInput;


    //constructor
    public CommandSchedule(String userInput){
        this.userInput = userInput;
        this.commandType = CommandType.VIEWSCHEDULE;
    };


    @Override
    public void execute(TaskList taskList) {
        String dateInput = Parser.removeStr(this.commandType.toString(), this.userInput); //removes command ViewSchedule leaving the date
        Ui.dukeSays("Here is your schedule for the following date '"
                + dateInput
                + "'."
        );
        try {
            printSchedule(dateInput, taskList);
        } catch (Exception e) {
            System.out.println("Read invalid input");
        }
    }

    private void printSchedule(String dateInput, TaskList taskList) throws ParseException {
        Date date = new Date();
        //insert code to convert string to date type
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date userDate = format.parse(dateInput);
        userDate.setTime(0);
        for (int index = 0; index < taskList.getSize(); ++index){
            try {
                Date taskDATE = taskList.getList().get(index).getDatetime();//creates of copy of datetime in the task
                if (taskDATE == null) {
                    return;
                }
                taskDATE.setTime(0);//this sets the time to zero
                if (taskDATE.equals(userDate)) {
                    taskList.printTaskByIndex(index);
                }
            } catch (Exception e){
                System.out.println("invalid");
            }
        }

    }
}
