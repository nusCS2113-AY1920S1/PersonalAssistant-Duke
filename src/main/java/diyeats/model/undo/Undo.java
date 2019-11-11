package diyeats.model.undo;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.*;
import diyeats.logic.parsers.*;
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

public class Undo {
    private Stack<String> history = new Stack();
    private DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("d/M/yyyy");
    private Stack<ArrayList<Meal>> mealListHistory = new Stack();
    private Stack<Meal> mealHistory = new Stack();
    private ArrayList<ArrayList<Meal>> clearHolder = new ArrayList();

    public int getSize() {
        return history.size();
    }

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
                break;
            case "breakfast":
                addBreakfast(meals, storage, user, wallet, info);
                break;
            case "lunch":
                addLunch(meals, storage, user, wallet, info);
                break;
            case "dinner":
                addDinner(meals, storage, user, wallet, info);
                break;
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
                //setgoal(meals, storage, user, wallet, info);
            default:
                break;
        }
        return type;
    }

    public void undoAdd(Meal meal, ArrayList<Meal> meals) {
        String temp = "delete ";
        temp += meal.getDate().format(dateFormat);
        this.history.push(temp);
        this.mealListHistory.push(meals);
    }

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
        history.push(temp);
    }

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

    public void undoClearStage1() {
        clearHolder = new ArrayList();
    }

    public void undoClearStage2(ArrayList<Meal> temp) {
        clearHolder.add(temp);
    }

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
                temp += mealInfo;
            }
            temp += "|";
        }
        System.out.println(temp);
        history.push(temp);
    }

    public void undoMarkDone(LocalDate date, int index) {
        String temp = "markDone ";
        temp += date.format(dateFormat);
        temp += " " + index;
        history.push(temp);
    }

    public void undoDefaultValues(String name) {
        String temp = "defaultValues " + name;
        history.push(temp);
    }

    public void undoDelDefault(HashMap<String, Integer> nutritionList, String name) {
        String temp = "delDefault " + name;
        for (String nutrition: nutritionList.keySet()) {
            temp += " /" + nutrition + " " + nutritionList.get(nutrition);
        }
        history.push(temp);
    }

    public void undoSetGoal() {
        history.push("setGoal ");
    }

    public void deleteFood(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {
        LocalDate date = LocalDate.parse(toBeParsed, dateFormat);
        ArrayList<Meal> temp = mealListHistory.pop();
        meals.setMealsList(date, temp);
    }

    public void addBreakfast(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {
        AddCommand c = new AddBreakfastCommandParser().parse(toBeParsed);
        c.undo(meals, storage, user, wallet);
    }

    public void addLunch(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {
        AddCommand c = new AddLunchCommandParser().parse(toBeParsed);
        c.undo(meals, storage, user, wallet);
    }

    public void addDinner(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {
        AddCommand c = new AddDinnerCommandParser().parse(toBeParsed);
        c.undo(meals, storage, user, wallet);
    }

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

    public void update(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {
        UpdateCommand c = new UpdateCommandParser().parse(toBeParsed);
        c.undo(meals, storage, user, wallet);
    }

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

    public void markDone(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {
        String[] lineSplit = toBeParsed.split(" ");
        String date = lineSplit[0];
        String index = lineSplit[1];
        MarkDoneCommand c = new MarkDoneCommand(index, date);
        c.undo(meals, storage, user, wallet);
    }

    public void defaultValues(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {
        DeleteDefaultValueCommand c = new DeleteDefaultValueCommand(toBeParsed);
        c.undo(meals, storage, user, wallet);
    }

    public void delDefault(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {
        AddDefaultValueCommand c = new AddDefaultValueCommandParser().parse(toBeParsed);
        c.undo(meals, storage, user, wallet);
    }

    public void setGoal(MealList meals, Storage storage, User user, Wallet wallet, String toBeParsed) {

    }
}
