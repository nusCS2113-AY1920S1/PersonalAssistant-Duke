package Commands;

import Commons.LookupTable;
import Commons.Storage;
import Commons.Ui;
import Tasks.TaskList;

import java.util.ArrayList;

public class RetrieveFreeTimesCommand extends Command {
    private String fullCommand;
    private static String selectedOption;

    public RetrieveFreeTimesCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public String execute(LookupTable LT, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception {
        Integer option = Integer.parseInt(fullCommand);
        ArrayList<String> retrievedFreeTimes = FindFreeTimesCommand.getCompiledFreeTimesList();
        if(retrievedFreeTimes.size() == 0) return "Please find free times";
        else if(option > 5) return "Please select options between 1 - 5";
        selectedOption = retrievedFreeTimes.get(option-1);
        return "Selected option " + option + "\n" + selectedOption;
    }

    public static String getSelectedOption() {
        return selectedOption;
    }
}
