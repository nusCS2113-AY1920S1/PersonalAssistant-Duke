package Parser;

import Commands.Command;
import Commands.FindFreeTimesCommand;
import Interface.DukeException;
import Interface.LookupTable;
import Interface.Storage;
import Interface.Ui;
import Tasks.TaskList;
import java.text.ParseException;

public class FindFreeTimesParse extends Parse {
    private String fullCommand;
    private Integer duration;

    public FindFreeTimesParse(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public Command execute() throws DukeException {
        fullCommand = fullCommand.replaceFirst("Find", "");
        fullCommand = fullCommand.trim();
        fullCommand = fullCommand.replaceFirst("hours", "");
        fullCommand = fullCommand.trim();
        if(fullCommand.isEmpty()){
            throw new DukeException("Invalid input. Please enter the command as follows. \n" +
                    "Find 'x' hours , where 'x' is a digit");
        } else {
            duration = Integer.parseInt(fullCommand);
            return new FindFreeTimesCommand(duration);
        }
    }
}

