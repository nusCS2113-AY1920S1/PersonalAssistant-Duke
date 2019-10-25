package duke.logic.command.recipecommands;

import duke.logic.command.CommandRating;
import duke.model.list.recipelist.RatingList;
import duke.storage.RatingStorage;
import duke.ui.Ui;

import java.text.ParseException;
import java.util.ArrayList;

import static duke.common.Messages.*;
import static duke.common.RecipeMessages.COMMAND_ADD_RATING;

public class AddRatingCommand extends CommandRating {
    public AddRatingCommand(String userInput) {
        this.userInput = userInput;
    }

    @Override
    public ArrayList<String> execute(RatingList ratingList, Ui ui, RatingStorage ratingStorage) throws ParseException {
        ArrayList<String> arrayList = new ArrayList<>();
        if (userInput.trim().equals(COMMAND_ADD_RATING)) {
            arrayList.add(ERROR_MESSAGE_GENERAL + MESSAGE_FOLLOWUP_NUll);
            System.out.println("stuck here");
        } else if (userInput.trim().charAt(9) == ' ') {
            if (userInput.trim().contains("n/")) {
                String rating = userInput.split("n/", 2)[1].trim();
                ratingList.addRating(rating);
                System.out.println("added rating");
                ratingStorage.saveFile(ratingList);
                arrayList.add(MESSAGE_ADDED + "       " + ratingList.getRating2List().get(ratingList.getSize() - 1) + "\n" + "You have added the rating.");
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
