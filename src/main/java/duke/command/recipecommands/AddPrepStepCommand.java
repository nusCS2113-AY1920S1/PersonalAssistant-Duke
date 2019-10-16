package duke.command.recipecommands;

import duke.command.CommandPrepStep;
import duke.list.recipelist.PrepStepList;
import duke.storage.PrepStepStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.COMMAND_ADD_PREPSTEP;

public class AddPrepStepCommand extends CommandPrepStep {
    public AddPrepStepCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> execute(PrepStepList prepStepList, Ui ui, PrepStepStorage prepStepStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_ADD_PREPSTEP)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
            System.out.println("stuck here");
        } else if (userInput.trim().charAt(11) == ' ') {
            if (userInput.trim().contains("n/")) {
                String prepStep = userInput.split("n/", 2)[1].trim();
                prepStepList.addPrepStep(prepStep);
                System.out.println("added prepStep");
                prepStepStorage.saveFile(prepStepList);
                arrayList.add(MESSAGE_ADDED + "       " + prepStepList.getPrepStepList().get(prepStepList.getSize() - 1) + "\n" + "You have added the prep steps.");
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
            }
        } else {
            arrayList.add(ERROR_MESSAGE_RANDOM);
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
