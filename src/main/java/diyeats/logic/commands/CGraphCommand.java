package diyeats.logic.commands;

import diyeats.model.meal.Meal;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Transaction;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;
import diyeats.ui.GraphUi;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashMap;

//@@author koushireo

/**
 * CGraphCommand is a public class that inherits from abstract class Command.
 * It calculates the data desired by user and visualised it on Command Line.
 */

public class CGraphCommand extends Command {
    private int month;
    private int year;
    private String type;
    private GraphUi graphUi = new GraphUi();

    /**
     * Constructor for CGraphCommand.
     * @param month month of the graph desired
     * @param year year of the graph desired
     * @param type type of the data to be graphed
     */

    public CGraphCommand(int month, int year, String type) {
        this.month = month;
        this.year = year;
        this.type = type;
    }

    public CGraphCommand(boolean flag, String message) {
        this.isFail = true;
        this.errorStr = message;
    }

    @Override
    public void execute(MealList meals, Storage storage, User user, Wallet wallet) {
        ArrayList<Integer> intHolder = new ArrayList();
        double highest = 1;
        LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        date = date.withMonth(month);
        //start of month :
        LocalDate firstDay = date.withDayOfMonth(1);
        LocalDate lastDay = date.with(TemporalAdjusters.lastDayOfMonth());
        lastDay.plusDays(1);
        String[][] graph = new String[21][62];
        for (int i = 0; i < 21; i += 1) {
            for (int j = 0; j < 62; j += 1) {
                graph[i][j] = " ";
            }
        }
        if (this.type.equals("weight")) {
            HashMap<LocalDate, Double> weight = user.getAllWeight();
            for (LocalDate dateItr = firstDay; dateItr.isBefore(lastDay); dateItr = dateItr.plusDays(1)) {
                if (weight.containsKey(dateItr)) {
                    intHolder.add((int)Math.round(weight.get(dateItr)));
                    if (highest < weight.get(dateItr)) {
                        highest = weight.get(dateItr);
                    }
                } else {
                    intHolder.add(0);
                }
            }
        } else if (this.type.equals("wallet")) {
            HashMap<LocalDate, ArrayList<Transaction>> transactions = wallet.getTransactions().getTransactionList();
            for (LocalDate dateItr = firstDay; dateItr.isBefore(lastDay); dateItr = dateItr.plusDays(1)) {
                String currentDate = dateItr.format(dateFormat);
                int totalSpent = 0;
                if (transactions.containsKey(dateItr)) {
                    ArrayList<Transaction> transactionOnTheDay = transactions.get(dateItr);
                    for (int j = 0; j < transactionOnTheDay.size(); j += 1) {
                        if (transactionOnTheDay.get(j).getType().equals("PAY")) {
                            totalSpent += transactionOnTheDay.get(j).getTransactionAmount().intValue();
                        }
                    }
                    if (highest < totalSpent) {
                        highest = totalSpent;
                    }
                }
                intHolder.add(totalSpent);
            }
        } else {
            HashMap<LocalDate, ArrayList<Meal>> meal = meals.getMealTracker();
            for (LocalDate dateItr = firstDay; dateItr.isBefore(lastDay); dateItr = dateItr.plusDays(1)) {
                int totalConsumed = 0;
                if (meal.containsKey(dateItr)) {
                    ArrayList<Meal> mealOnTheDay = meal.get(dateItr);
                    for (Meal value : mealOnTheDay) {
                        if (value.getNutritionalValue().containsKey(this.type)) {
                            totalConsumed += value.getNutritionalValue().get(this.type);
                        }
                    }
                }
                if (highest < totalConsumed) {
                    highest = totalConsumed;
                }
                intHolder.add(totalConsumed);
            }
        }
        int pos;
        for (int i = 0; i < intHolder.size(); i += 1) {
            pos = (int)(((float)intHolder.get(i) / (float)highest) * 20);
            graph[20 - pos][i * 2] = "*";
        }

        for (int i = 0; i < intHolder.size() - 1; i += 1) {
            pos = (int)(((float)((intHolder.get(i)
                    + intHolder.get(i + 1)) / 2) / (float)highest) * 20);
            graph[20 - pos][i * 2 + 1] = "*";
        }

        graphUi.show(graph, month, type);
    }
}
