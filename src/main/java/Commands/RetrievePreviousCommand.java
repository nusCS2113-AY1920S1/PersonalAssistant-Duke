package Commands;

import Interface.LookupTable;
import Interface.Storage;
import Interface.Ui;
import Tasks.TaskList;

import java.util.ArrayList;

public class RetrievePreviousCommand extends Command{

    private String fullCommand;
    public static String retrievedOutput;

    public RetrievePreviousCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public String execute(LookupTable LT, TaskList events, TaskList deadlines, Ui ui, Storage storage) throws Exception {
        fullCommand = fullCommand.replace("retrieve previous", "");
        fullCommand = fullCommand.trim();

        ArrayList<String> retrievedList;
        retrievedList = ShowPreviousCommand.getOutputList();
        int size = retrievedList.size();
        if (size == 0) {
            return ui.showEmptyListMessage();
        }

        int intFullCommand = Integer.parseInt(fullCommand);
        if (intFullCommand > size) {
            return ui.showInvalidNumberErrorMessage(size);
        }

//        for (String output : retrievedList) {
//            System.out.println(output);
//        }

        int index = intFullCommand - 1;
        retrievedOutput = retrievedList.get(index);
        return ui.showChosenPreviousChoice(retrievedOutput);
    }

    public static String getChosenOutput() {
        return retrievedOutput;
    }
}
