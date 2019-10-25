package duke.logic.command.recipecommands;

import duke.logic.command.CommandFeedback;
import duke.model.list.recipelist.FeedbackList;
import duke.storage.FeedbackStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.COMMAND_ADD_FEEDBACK;

public class AddFeedbackCommand extends CommandFeedback {
    public AddFeedbackCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> execute(FeedbackList feedbackList, Ui ui, FeedbackStorage feedbackStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_ADD_FEEDBACK)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
            System.out.println("stuck here");
        } else if (userInput.trim().charAt(11) == ' ') {
            if (userInput.trim().contains("n/")) {
                String feedback = userInput.split("n/", 2)[1].trim();
                feedbackList.addFeedback(feedback);
                System.out.println("added feedback");
                feedbackStorage.saveFile(feedbackList);
                arrayList.add(MESSAGE_ADDED + "       " + feedbackList.getFeedbackList().get(feedbackList.getSize() - 1) + "\n" + "You have added the feedback.");
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