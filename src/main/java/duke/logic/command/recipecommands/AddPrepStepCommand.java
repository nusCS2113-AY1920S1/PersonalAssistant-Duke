package duke.logic.command.recipecommands;

import duke.logic.command.CommandPrepStep;
import duke.model.list.recipelist.PrepStepList;
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
        String stepDescription = "";
        if (userInput.trim().equals(COMMAND_ADD_PREPSTEP)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
            System.out.println("stuck here1");
        }
        else if (userInput.trim().charAt(19) == ' ') {
            String description = userInput.split("\\s", 2)[1].trim();
            if (description.contains("q/")) {
                String temp = description.split("q/", 2)[0].trim();
                stepDescription = temp.split("\\s", 2)[0].trim();
            } else {
                arrayList.add(ERROR_MESSAGE_RANDOM);
            }
        }
        return arrayList;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
