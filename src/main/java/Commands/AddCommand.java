package Commands;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import Tasks.*;
import ControlPanel.*;

public class AddCommand extends Command {

    private String type;
    private String inputString;
    private SimpleDateFormat simpleDateFormat;

    public AddCommand(String string, String cmd){
        type = string;
        inputString = cmd;
        simpleDateFormat  = new SimpleDateFormat("d/M/yyyy HHmm");
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws ParseException, DukeException {
        switch (type) {
            case "deadline": {
                String[] getDate = inputString.split("/by ");
                Date date = simpleDateFormat.parse(getDate[getDate.length-1]);
                String formattedDate = simpleDateFormat.format(date);
                Task t = new Deadline(getDate[0].replaceFirst("deadline ", ""),
                        formattedDate);
                tasks.addTask(t);
                break;
            }
            case "event": {
                if (inputString.equals("event")){
                    throw new DukeException("OOPS!!! The description of a event cannot be empty.");
                }
                String[] getDate = inputString.split("/at ");
                Date date = simpleDateFormat.parse(getDate[getDate.length-1]);
                String formattedDate = simpleDateFormat.format(date);
                Task t = new Events(getDate[0].replaceFirst("event ", ""),
                        formattedDate);
                tasks.addTask(t);
                break;
            }
            case "todo": {
                if (inputString.equals("todo")){
                    throw new DukeException("OOPS!!! The description of a todo cannot be empty.");
                }
                Task t = new ToDos(inputString.replaceFirst("todo ", ""));
                tasks.addTask(t);
                break;
            }
        }
        storage.writeTheFile(tasks.getCheckList());
        System.out.println(" Got it. I've added this task: \n");
        System.out.println("     " + tasks.getTask(tasks.lengthOfList()-1).toString() + "\n");
        System.out.println(" Now you have " + tasks.lengthOfList() + " tasks in the list.");

    }


}
