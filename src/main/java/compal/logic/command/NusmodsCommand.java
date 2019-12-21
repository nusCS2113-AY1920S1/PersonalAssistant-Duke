package compal.logic.command;

import compal.logic.command.exceptions.CommandException;
import compal.model.tasks.TaskList;

import java.util.ArrayList;

public class NusmodsCommand extends Command {
    /**
     * Public Constants.
     */
    public static final String MESSAGE_USAGE = "NUSMODS \n\t";

    /**
     * Private Constants.
     */
    public static final String TOKEN_TUT = "TUT";
    public static final String TOKEN_LEC = "LEC";
    public static final String TOKEN_LAB = "LAB";

    /**
     * Private Attributes.
     */
    private String semStartDateString;
    private ArrayList<String> moduleDetailsList;

    public NusmodsCommand(String semStartDateString, ArrayList<String> moduleDetailsList){
        this.semStartDateString = semStartDateString;
        this.moduleDetailsList = moduleDetailsList;
    }

    @Override
    public CommandResult commandExecute(TaskList tasklist) throws CommandException {
        String resultString = "NUSMODS: ";
        for (String moduleDetails : moduleDetailsList) {
            resultString = resultString.concat(moduleDetails + "\n");
        }
        return new CommandResult(resultString, false);
    }
}
