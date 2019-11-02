package duke.logic.commands;

import duke.model.meal.Meal;
import duke.model.meal.MealList;
import duke.model.user.User;
import duke.model.wallet.Transaction;
import duke.model.wallet.Wallet;
import duke.storage.Storage;
import duke.ui.GraphUi;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

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
        Calendar date = Calendar.getInstance();
        date.set(Calendar.MONTH, this.month);
        date.set(Calendar.YEAR, this.year);
        String[][] graph = new String[21][62];
        for (int i = 0; i < 21; i += 1) {
            for (int j = 0; j < 62; j += 1) {
                graph[i][j] = " ";
            }
        }
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        if (this.type.equals("weight")) {
            HashMap<String, Double> weight = user.getAllWeight();
            for (int i = 0; i < daysInMonth; i += 1) {
                date.set(Calendar.DAY_OF_MONTH, i);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String currentDate = dateFormat.format(date.getTime());
                if (weight.containsKey(currentDate)) {
                    intHolder.add((int)Math.round(weight.get(currentDate)));
                    if (highest < weight.get(currentDate)) {
                        highest = weight.get(currentDate);
                    }
                } else {
                    intHolder.add(0);
                }
            }
        } else if (this.type.equals("wallet")) {
            HashMap<String, ArrayList<Transaction>> transactions = wallet.getTransactions().getTransactionList();
            for (int i = 0; i < daysInMonth; i += 1) {
                date.set(Calendar.DAY_OF_MONTH, i);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String currentDate = dateFormat.format(date.getTime());
                int totalSpent = 0;
                if (transactions.containsKey(currentDate)) {
                    ArrayList<Transaction> transactionOnTheDay = transactions.get(currentDate);
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
            for (int i = 0; i < daysInMonth; i += 1) {
                date.set(Calendar.DAY_OF_MONTH, i);
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                LocalDate currentDate = LocalDate.parse(dateFormat.format(date.getTime()), this.dateFormat);
                int totalConsumed = 0;
                if (meal.containsKey(currentDate)) {
                    ArrayList<Meal> mealOnTheDay = meal.get(currentDate);
                    for (int j = 0; j < mealOnTheDay.size(); j += 1) {
                        if (mealOnTheDay.get(j).getNutritionalValue().containsKey(this.type)) {
                            totalConsumed += mealOnTheDay.get(j).getNutritionalValue().get(this.type);
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
        for (int i = 0; i < daysInMonth; i += 1) {
            pos = (int)(((float)intHolder.get(i) / (float)highest) * 20);
            graph[20 - pos][i * 2] = "*";
        }
        for (int i = 0; i < daysInMonth - 1; i += 1) {
            pos = (int)(((float)((intHolder.get(i)
                    + intHolder.get(i + 1)) / 2) / (float)highest) * 20);
            graph[20 - pos][i * 2 + 1] = "*";
        }
        graphUi.show(graph, month, type);
    }
}
