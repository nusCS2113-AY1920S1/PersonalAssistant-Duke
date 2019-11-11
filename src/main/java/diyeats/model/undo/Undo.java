package diyeats.model.undo;

import diyeats.commons.datatypes.Pair;
import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.AddCommand;
import diyeats.logic.commands.AddDefaultValueCommand;
import diyeats.logic.commands.AddExerciseCommand;
import diyeats.logic.commands.AddGoalCommand;
import diyeats.logic.commands.AddTransactionCommand;
import diyeats.logic.commands.DeleteCommand;
import diyeats.logic.commands.DeleteDefaultValueCommand;
import diyeats.logic.commands.MarkDoneCommand;
import diyeats.logic.commands.SuggestExerciseCommand;
import diyeats.logic.commands.UpdateCommand;
import diyeats.logic.parsers.AddBreakfastCommandParser;
import diyeats.logic.parsers.AddDefaultValueCommandParser;
import diyeats.logic.parsers.AddDinnerCommandParser;
import diyeats.logic.parsers.AddExerciseCommandParser;
import diyeats.logic.parsers.AddLunchCommandParser;
import diyeats.logic.parsers.UpdateCommandParser;
import diyeats.model.meal.Meal;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Transaction;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

//@@author koushireo

/**
 * Undo is a class that helps the user undo previous commands.
 */

public class Undo {
    private Stack<String> history = new Stack();
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d/M/yyyy");
    private Stack<ArrayList<Meal>> mealListHistory = new Stack();
    private Stack<Meal> mealHistory = new Stack();
    private ArrayList<ArrayList<Meal>> clearHolder = new ArrayList();

    public int getSize() {
        return history.size();
    }

    /**
     * Execute is a function that pops the latest instruction from history and execute it,
     * inverse to the previous command input.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @return String to be confirmed as the appropriate command has been inversed
     * @throws ProgramException Throws an exception if it occurs to UndoCommand to be output
     */

    public String execute(MealList meals, Storage storage, User user, Wallet wallet) throws ProgramException {
        if (history.size() == 0) {
            throw new ProgramException("There is no previous command to undo.");
        }
        String temp = history.pop();
        String[] execution = temp.split(" ",2);
        String type = execution[0];
        String info = execution[1];
        switch (type) {
            case "delete":
                deleteFood(meals, storage, user, wallet, info);
                return "add";
            case "breakfast":
                addBreakfast(meals, storage, user, wallet, info);
                return "delete";
            case "lunch":
                addLunch(meals, storage, user, wallet, info);
                return "delete";
            case "dinner":
                addDinner(meals, storage, user, wallet, info);
                return "delete";
            case "edit":
                try {
                    edit(meals, storage, user, wallet, info);
                    break;
                } catch (ProgramException e) {
                    throw new ProgramException(e.getMessage());
                }
            case "update":
                update(meals, storage, user, wallet, info);
                break;
            case "transaction":
                try {
                    transaction(meals, storage, user, wallet, info);
                    break;
                } catch (ProgramException e) {
                    throw new ProgramException(e.getMessage());
                }
            case "clear":
                clear(meals, storage, user, wallet, info);
                break;
            case "markDone":
                markDone(meals, storage, user, wallet, info);
                break;
            case "defaultValues":
                defaultValues(meals, storage, user, wallet, info);
                break;
            case "delDefault":
                delDefault(meals, storage, user, wallet, info);
                break;
            case "setGoal":
                setGoal(meals, storage, user, wallet, info);
                break;
            case "suggestExercise":
                suggestExercise(meals, storage, user, wallet, info);
                break;
            case "addExercise":
                addExercise(meals, storage, user, wallet, info);
                break;
            case "deleteExercise":
                deleteExercise(meals, storage, user, wallet, info);
                break;
            default:
                break;
        }
        return type;
    }

    /**
     * Generates the inverse instruction of an Add command.
     * @param meal The meal object to be added that encapsulates the information
     * @param meals The ArrayList of meals to be pushed to mealListHistory
     */

    public void undoAdd(Meal meal, ArrayList<Meal> meals) {
        String temp = "delete ";
        temp += meal.getDate().format(dateFormat);
        this.history.push(temp);
        this.mealListHistory.push(meals);
    }

    /**
     * Generates the inverse of a Delete Command.
     * @param meal The meal object to be removed that encapsulates the information
     */

    public void undoDelete(Meal meal) {
        String temp = "";
        if (meal.getType().equals("B")) {
            temp += "breakfast ";
        } else if (meal.getType().equals("L")) {
            temp += "lunch ";
        } else if (meal.getType().equals("D")) {
            temp += "dinner ";
        }
        temp += meal.getDescription() + " ";
        HashMap<String, Integer> nutrition = meal.getNutritionalValue();
        for (String nutritionName : nutrition.keySet()) {
            temp += "/" + nutritionName + " " + nutrition.get(nutritionName) + " ";
        }
        temp += "/date " + meal.getDate().format(dateFormat);
        if (!meal.getCostStr().equals("0")) {
            temp += "/cost " + meal.getCostStr();
        }
        if (meal.getIsDone()) {
            temp += "," + 1;
        } else {
            temp += "," + 0;
        }
        history.push(temp);
    }

    /**
     * Generates the inverse of an Edit Command.
     * @param mealIndex index of the meal that was edited
     * @param meal The meal object to be edited that encapsulates the information
     */

    public void undoEdit(int mealIndex, Meal meal) {
        String temp = "edit " + mealIndex + " ";
        if (meal.getType().equals("B")) {
            temp += "breakfast ";
        } else if (meal.getType().equals("L")) {
            temp += "lunch ";
        } else if (meal.getType().equals("D")) {
            temp += "dinner ";
        }
        temp += meal.getDescription() + " ";
        HashMap<String, Integer> nutrition = meal.getNutritionalValue();
        for (String nutritionName : nutrition.keySet()) {
            temp += "/" + nutritionName + " " + nutrition.get(nutritionName) + " ";
        }
        temp += "/date " + meal.getDate().format(dateFormat);
        if (!meal.getCostStr().equals("0")) {
            temp += " /cost " + meal.getCostStr();
        }
        history.push(temp);
    }

    /**
     * Generates the inverse of an Update Command.
     * @param date date of the weight edited
     * @param user The user object to be edited that encapsulates the information
     */

    public void undoUpdate(String date, User user) {
        String temp = "update ";
        temp += "/name " + user.getName() + " ";
        temp += "/age " + user.getAge() + " ";
        temp += "/height " + user.getHeight() + " ";
        temp += "/activity " + user.getActivityLevel() + " ";
        temp += "/weight " + (int)user.getWeight() + " ";
        temp += "/date " + date + " ";
        history.push(temp);
    }

    /**
     * Generates the inverse of a Transaction Command.
     * @param transaction The transaction object that encapsulates the information
     */

    public void undoTransaction(Transaction transaction) {
        String temp = "transaction ";
        if (transaction.getType().equals("PAY")) {
            temp += "DEP ";
        } else {
            temp += "PAY ";
        }
        temp += transaction.getDate().format(dateFormat);
        history.push(temp);
    }

    /**
     * Generates the inverse of a clear command.
     * Stage 1 creates a new empty ArrayList
     */

    public void undoClearStage1() {
        clearHolder = new ArrayList();
    }

    /**Generates the inverse of a clear command.
     * Stage 2 adds all of the cleared ArrayList of Meal into its ArrayList
     * @param temp ArrayList of the meals of a cleared date
     */

    public void undoClearStage2(ArrayList<Meal> temp) {
        clearHolder.add(temp);
    }

    /**Generates the inverse of a clear command.
     * Compile the cleared information together and create a string
     * to be pushed to history
     */

    public void undoClearStage3() {
        String temp = "clear ";
        for (int i = 0; i < clearHolder.size(); i += 1) {
            ArrayList<Meal> mealList = clearHolder.get(i);
            for (int j = 0; j < mealList.size(); j += 1) {
                Meal meal = mealList.get(j);
                String mealInfo = "";
                if (meal.getType().equals("B")) {
                    mealInfo += "breakfast ";
                } else if (meal.getType().equals("L")) {
                    mealInfo += "lunch ";
                } else if (meal.getType().equals("D")) {
                    mealInfo += "dinner ";
                }
                mealInfo += meal.getDescription() + " ";
                HashMap<String, Integer> nutrition = meal.getNutritionalValue();
                for (String nutritionName : nutrition.keySet()) {
                    mealInfo += "/" + nutritionName + " " + nutrition.get(nutritionName) + " ";
                }
                mealInfo += "/date " + meal.getDate().format(dateFormat);
                if (!meal.getCostStr().equals("0")) {
                    mealInfo += "/cost " + meal.getCostStr();
                }
                if (meal.getIsDone()) {
                    mealInfo += "," + 1;
                } else {
                    mealInfo += "," + 0;
                }
                temp += mealInfo + "|";
            }
        }
        history.push(temp);
    }

    /**
     * Generates the inverse of a MarkDone Command.
     * @param date date of the meal that was marked done
     * @param index index of the meal that was marked done on that date
     */

    public void undoMarkDone(LocalDate date, int index) {
        String temp = "markDone ";
        temp += date.format(dateFormat);
        temp += " " + index;
        history.push(temp);
    }

    /**
     * Generates the inverse of a DefaultValues Command.
     * @param name name of the default value item to be added
     */

    public void undoDefaultValues(String name) {
        String temp = "defaultValues " + name;
        history.push(temp);
    }

    /**
     * Generates the inverse of a delDefaultValues command.
     * @param nutritionList nutritionList of the item to be removed
     * @param name name of the item to be removed
     */

    public void undoDelDefault(HashMap<String, Integer> nutritionList, String name) {
        String temp = "delDefault " + name;
        for (String nutrition: nutritionList.keySet()) {
            temp += " /" + nutrition + " " + nutritionList.get(nutrition);
        }
        history.push(temp);
    }

    /**
     * Generates the inverse of a setGoal command.
     */

    public void undoSetGoal() {
        history.push("setGoal ");
    }

    /**
     * Generates the inverse of a suggestexercise command.
     * @param date date of the exercise appended
     * @param selectedExercise the exercise that was replaced or
     *                         null(meaning there was no exercise initially)
     */
    public void undoSuggestExercise(LocalDate date, Pair selectedExercise) {
        String temp = "suggestExercise ";
        temp += date.format(dateFormat);
        if (selectedExercise == null) {
            temp += "|null";
        } else {
            temp += "|" + selectedExercise.getKey() + "|" + selectedExercise.getValue();
        }
        history.push(temp);
    }

    /**
     * Generates the inverse of an AddExercise Command.
     * @param name Name of the exercise regime added
     * @param met Value of the MET of the exercise regime added
     */

    public void undoAddExercise(String name, int met) {
        String temp = "addExercise ";
        temp += name + "|" + met;
        history.push(temp);
    }

    /**
     * Generates the inverse of a DeleteExercise command.
     * @param name Name of the exercise regime to be removed
     * @param met Value of the MET of the exercise regime to be removed
     */

    public void undoDeleteExercise(String name, int met) {
        String temp = "deleteExercise ";
        temp += name + " /value " + met;
        history.push(temp);
    }

    /**
     * Executes the inverse of an AddCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param toBeParsed information that is to be parsed
     */

    public void deleteFood(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {
        LocalDate date = LocalDate.parse(toBeParsed, dateFormat);
        ArrayList<Meal> temp = mealListHistory.pop();
        DeleteCommand c = new DeleteCommand(date, temp);
        c.undo(meals, storage, user, wallet);
    }

    /**
     * Executes the inverse of a DeleteCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param toBeParsed information that is to be parsed
     */

    public void addBreakfast(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {
        String[] lineSplit = toBeParsed.split(",");
        AddCommand c = new AddBreakfastCommandParser().parse(lineSplit[0]);
        c.setIsDone(lineSplit[1]);
        c.undo(meals, storage, user, wallet);
    }

    /**
     * Executes the inverse of a DeleteCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param toBeParsed information that is to be parsed
     */

    public void addLunch(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {
        String[] lineSplit = toBeParsed.split(",");
        AddCommand c = new AddBreakfastCommandParser().parse(lineSplit[0]);
        c.setIsDone(lineSplit[1]);
        c.undo(meals, storage, user, wallet);
    }

    /**
     * Executes the inverse of a DeleteCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param toBeParsed information that is to be parsed
     */

    public void addDinner(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {
        String[] lineSplit = toBeParsed.split(",");
        AddCommand c = new AddBreakfastCommandParser().parse(lineSplit[0]);
        c.setIsDone(lineSplit[1]);
        c.undo(meals, storage, user, wallet);
    }

    /**
     * Executes the inverse of an EditCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param toBeParsed information that is to be parsed
     */

    public void edit(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed)
            throws ProgramException {
        AddCommand c = new AddCommand();
        String[] lineSplit = toBeParsed.split(" ", 3);
        int index = 0;
        try {
            index = Integer.parseInt(lineSplit[0]);
        } catch (NumberFormatException e) {
            index = 0;
        }
        String type = lineSplit[1];
        String info = lineSplit[2];
        if (type.equals("breakfast")) {
            c = new AddBreakfastCommandParser().parse(info);
        } else if (type.equals("lunch")) {
            c = new AddLunchCommandParser().parse(info);
        } else if (type.equals("dinner")) {
            c = new AddDinnerCommandParser().parse(info);
        }
        Meal meal = c.getMeal();
        meals.updateMeal(index, meal);
    }

    /**
     * Executes the inverse of an UpdateCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param toBeParsed information that is to be parsed
     */

    public void update(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {
        UpdateCommand c = new UpdateCommandParser().parse(toBeParsed);
        c.undo(meals, storage, user, wallet);
    }

    /**
     * Executes the inverse of a TransactionCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param toBeParsed information that is to be parsed
     */

    public void transaction(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed)
            throws ProgramException {
        String[] lineSplit = toBeParsed.split(" ");
        String type = lineSplit[0];
        LocalDate date = LocalDate.parse(lineSplit[1], dateFormat);
        Transaction temp = wallet.getTransactions().deleteTransaction(date);
        temp.changeType();
        AddTransactionCommand c = new AddTransactionCommand(temp);
        c.undo(meals, storage, user, wallet);
    }

    /**
     * Executes the inverse of a ClearCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param toBeParsed information that is to be parsed
     */

    public void clear(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {
        String[] lineSplit = toBeParsed.split("\\|");
        for (int i = 0; i < lineSplit.length; i += 1) {
            String[] mealTypeAndInfo = lineSplit[i].split(" ",2);
            String mealType = mealTypeAndInfo[0];
            String mealInfo = mealTypeAndInfo[1];
            if (mealType.equals("breakfast")) {
                addBreakfast(meals, storage, user, wallet, mealInfo);
            } else if (mealType.equals("lunch")) {
                addLunch(meals, storage, user, wallet, mealInfo);
            } else if (mealType.equals("dinner")) {
                addDinner(meals, storage, user, wallet, mealInfo);
            }
        }
    }

    /**
     * Executes the inverse of a MarkDoneCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param toBeParsed information that is to be parsed
     */

    public void markDone(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {
        String[] lineSplit = toBeParsed.split(" ");
        String date = lineSplit[0];
        String index = lineSplit[1];
        MarkDoneCommand c = new MarkDoneCommand(index, date);
        c.undo(meals, storage, user, wallet);
    }

    /**
     * Executes the inverse of a defaultValuesCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param toBeParsed information that is to be parsed
     */

    public void defaultValues(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {
        DeleteDefaultValueCommand c = new DeleteDefaultValueCommand(toBeParsed);
        c.undo(meals, storage, user, wallet);
    }

    /**
     * Executes the inverse of a delDefaultCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param toBeParsed information that is to be parsed
     */

    public void delDefault(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {
        AddDefaultValueCommand c = new AddDefaultValueCommandParser().parse(toBeParsed);
        c.undo(meals, storage, user, wallet);
    }

    /**
     * Executes the inverse of a SetGoalCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param toBeParsed information that is to be parsed
     */

    public void setGoal(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {
        AddGoalCommand c = new AddGoalCommand();
        c.undo(meals, storage, user, wallet);
    }

    /**
     * Executes the inverse of a suggestExerciseCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param toBeParsed information that is to be parsed
     * @throws ProgramException throws exception when the information cannot be parsed into Int
     */

    public void suggestExercise(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed)
            throws ProgramException {
        SuggestExerciseCommand c;
        String[] lineSplit = toBeParsed.split("\\|");
        LocalDate date = LocalDate.parse(lineSplit[0], dateFormat);
        String type = lineSplit[1];
        if (type.equals("null")) {
            c = new SuggestExerciseCommand(date);
        } else {
            int rep = 0;
            try {
                rep = Integer.parseInt(lineSplit[2]);
            } catch (NumberFormatException e) {
                throw new ProgramException(e.getMessage());
            }
            c = new SuggestExerciseCommand(date, new Pair(type, rep), true);
        }
        c.undo(meals, storage, user, wallet);
    }

    /**
     * Executes the inverse of an AddExerciseCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param toBeParsed information that is to be parsed
     * @throws ProgramException throws exception when the information cannot be parsed into Int
     */

    public void addExercise(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed)
            throws ProgramException {
        String[] lineSplit = toBeParsed.split("\\|");
        String name = lineSplit[0];
        int met = -1;
        try {
            met = Integer.parseInt(lineSplit[1]);
        } catch (NumberFormatException e) {
            throw new ProgramException(e.getMessage());
        }
        AddExerciseCommand c = new AddExerciseCommand(name, met);
        c.undo(meals, storage, user, wallet);
    }

    /**
     * Executes the inverse of a deleteExerciseCommand.
     * @param meals the MealList object in which the meals are supposed to be added
     * @param storage the storage object that handles all reading and writing to files
     * @param user the object that handles all user data
     * @param wallet the wallet object that stores transaction information
     * @param toBeParsed information that is to be parsed
     */

    public void deleteExercise(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {
        AddExerciseCommand c = new AddExerciseCommandParser().parse(toBeParsed);
        c.undoForDelete(meals, storage, user, wallet);
    }
}
