package duke.storage;

import duke.commons.exceptions.DukeException;
import duke.commons.fileIO.FilePaths;
import duke.commons.fileIO.FileUtil;
import duke.model.Goal;
import duke.model.Meal;
import duke.model.MealList;
import duke.model.user.Gender;
import duke.model.user.Tuple;
import duke.model.user.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This object is in charge of all writing to save operations.
 */
public class Write {
    FilePaths filePaths = new FilePaths();

    /**
     * This is a function that will update the input/output file from the current arraylist of meals.
     * @param mealData the structure that will store the tasks from the input file
     */
    //TODO: maybe we can put the errors in the ui file
    public void writeFile(MealList mealData) throws DukeException {
        HashMap<String, ArrayList<Meal>> meals = mealData.getMealTracker();
        String toWriteStr = "";
        for (String i : meals.keySet()) { //write process for stored food entries
            ArrayList<Meal> mealsInDay = meals.get(i);
            for (int j = 0; j < meals.get(i).size(); j++) {
                String tempLineStr = "";
                Meal currentMeal = mealsInDay.get(j);
                String status = "0";
                if (currentMeal.getIsDone()) {
                    status = "1";
                }
                tempLineStr += currentMeal.getType() + "|" + status + "|" + currentMeal.getDescription()
                        + "|date|" + currentMeal.getDate();
                HashMap<String, Integer> nutritionData = currentMeal.getNutritionalValue();
                if (nutritionData.size() != 0) {
                    tempLineStr += "|";
                    for (String k : nutritionData.keySet()) {
                        tempLineStr += k + "|" + nutritionData.get(k) + "|";
                    }
                    toWriteStr += tempLineStr.substring(0, tempLineStr.length() - 1) + "\n";
                }
            }
        }

        FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePaths.FILE_PATH_NAMES.FILE_PATH_USER_MEALS_FILE));

    }

    public void writeDefaults(MealList mealData) throws DukeException {
        HashMap<String, HashMap<String, Integer>> storedItems = mealData.getStoredList();
            String toWriteStr = "";
            for (String i : storedItems.keySet()) { //write process for stored default food values
                toWriteStr += "S|0|" + i;
                HashMap<String, Integer> nutritionData = storedItems.get(i);
                if (nutritionData.size() != 0) {
                    toWriteStr += "|";
                    for (String k : nutritionData.keySet()) {
                        toWriteStr += k + "|" + nutritionData.get(k) + "|";
                    }
                    toWriteStr = toWriteStr.substring(0, toWriteStr.length() - 1) + "\n";
                }
            }

            FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePaths.FILE_PATH_NAMES.FILE_PATH_DEFAULT_MEAL_FILE));
    }

    public void writeGoal(MealList mealData) throws DukeException {
            Goal goal = mealData.getGoal();
            String toWriteStr = "G|0|" + goal.getEndDate() + "|" + goal.getStartDate();
            HashMap<String, Integer> nutritionData = goal.getNutritionalValue();
            if (nutritionData.size() != 0) {
                for (String k : nutritionData.keySet()) {
                    toWriteStr += k + "|" + nutritionData.get(k) + "|";
                }
                toWriteStr = toWriteStr.substring(0, toWriteStr.length() - 1) + "\n";
            }

            FileUtil.writeFile(toWriteStr, filePaths.getFilePathStr(FilePaths.FILE_PATH_NAMES.FILE_PATH_GOAL_FILE));

    }


    /**
     * This is a function that will store the user information into a file.
     * @param user the user class that contains all personal information to be stored.
     */

    public void writeUser(User user) throws DukeException {
        String toWrite = user.getName() + "|" + user.getAge() + "|"
                + user.getHeight() + "|" + user.getActivityLevel() + "|" + user.getLoseWeight() + "|";
        if (user.getSex() == Gender.MALE) {
            toWrite += "M";
        } else {
            toWrite += "F";
        }
        ArrayList<Tuple> allWeight = user.getAllWeight();
        for (int i = 0; i < user.getAllWeight().size(); i += 1) {
            toWrite += "\n";
            String date = allWeight.get(i).date;
            int weight = allWeight.get(i).weight;
            toWrite += date + "|" + weight;
        }

        FileUtil.writeFile(toWrite, filePaths.getFilePathStr(FilePaths.FILE_PATH_NAMES.FILE_PATH_USER_FILE));
    }
}
