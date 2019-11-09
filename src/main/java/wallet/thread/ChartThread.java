//@@author matthewng1996

package wallet.thread;

import wallet.model.record.Category;
import wallet.model.record.Expense;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class ChartThread implements Runnable {
    private Thread thread;
    private ArrayList<Expense> expenseList;

    /**
     * Custom thread.
     */
    public ChartThread(ArrayList<Expense> expenseArrayList) {
        thread = new Thread(this);
        thread.start();
        expenseList = expenseArrayList;
    }

    /**
     * Runs the threat and stops when pie chart is completed.
     */
    @Override
    public void run() {
        printPieChart(expenseList);
        System.out.print("\n");
        printBarGraph(expenseList);
        stop();
    }

    /**
     * Prints the pie chart given specified stats.
     */
    public static void printPieChart(ArrayList<Expense> expenseList) {

        char[] fill = new char[5];
        float[] percentage = new float[5];
        float radius = 8;

        fill[0] = '%';
        fill[1] = '-';
        fill[2] = '@';
        fill[3] = '*';
        fill[4] = '.';

        percentage[0] = 0f;
        percentage[1] = 0f;
        percentage[2] = 0f;
        percentage[3] = 0f;
        percentage[4] = 0f;

        int lastSymbolToBeDrawn = -1;

        float totalExpensesAmount = 0;
        double[] categoryAmount = new double[5];

        for (Expense e : expenseList) {
            totalExpensesAmount += e.getAmount();
        }

        for (Expense e : expenseList) {
            Category category = e.getCategory();
            switch (category) {
            case FOOD:
                percentage[0] += e.getAmount() / totalExpensesAmount;
                if (percentage[0] != 0) {
                    lastSymbolToBeDrawn = 0;
                }
                categoryAmount[0] += e.getAmount();
                break;

            case BILLS:
                percentage[1] += e.getAmount() / totalExpensesAmount;
                if (percentage[1] != 0) {
                    lastSymbolToBeDrawn = 1;
                }
                categoryAmount[1] += e.getAmount();
                break;

            case SHOPPING:
                percentage[2] += e.getAmount() / totalExpensesAmount;
                if (percentage[2] != 0) {
                    lastSymbolToBeDrawn = 2;
                }
                categoryAmount[2] += e.getAmount();
                break;

            case TRANSPORT:
                percentage[3] += e.getAmount() / totalExpensesAmount;
                if (percentage[3] == 0) {
                    lastSymbolToBeDrawn = 3;
                }
                categoryAmount[3] += e.getAmount();
                break;

            case OTHERS:
                percentage[4] += e.getAmount() / totalExpensesAmount;
                if (percentage[4] != 0) {
                    lastSymbolToBeDrawn = 4;
                }
                categoryAmount[4] += e.getAmount();
                break;

            default:
            }
        }
        if (!hasEmptyExpense(percentage)) {
            drawTable(categoryAmount, percentage);
            for (float y = -radius; y < radius; y++) {
                char character = '-';
                for (float x = -radius; x < radius; x++) {
                    if (x * x + y * y < radius * radius) {
                        double angle = Math.atan2(y, x) / Math.PI / 2 + .5f;
                        if (angle == 1) {
                            System.out.print(fill[lastSymbolToBeDrawn]);
                        } else {
                            character = set(fill, percentage, angle);
                            System.out.print(character); //Prints circle content
                        }
                    } else {
                        character = ' ';
                        System.out.print(character); //Prints spaces
                    }
                }
                System.out.println(character); //Form new line
            }
        } else {
            System.out.println("No expenses, no pie chart!");
        }
    }

    /**
     * This method fills the pie chart.
     *
     * @param fill       Symbols to fill pie chart
     * @param percentage Array of percentages for each category
     * @param angle      Calculated angle for the pie chart
     * @return could return a space, a symbol or recursively run function for the next symbol.
     */
    public static char set(char[] fill, float[] percentage, double angle) {
        if (angle <= percentage[0]) {
            return fill[0];
        }

        char[] fillArray = new char[fill.length - 1];
        float[] percentageArray = new float[percentage.length - 1];
        angle -= percentage[0];

        for (int i = 0; i < fillArray.length; i++) {
            fillArray[i] = fill[i + 1];
            percentageArray[i] = percentage[i + 1];
        }
        return set(fillArray, percentageArray, angle);
    }

    /**
     * This method draws the table as a summary for the pie chart before drawing the pie chart.
     *
     * @param categoryAmount amount of expenses in each category.
     * @param percentage     Percentage of total expenses in each category
     */
    public static void drawTable(double[] categoryAmount, float[] percentage) {
        String[] category = new String[5];
        String[] categorySymbols = new String[5];

        category[0] = "FOOD";
        category[1] = "BILLS";
        category[2] = "SHOPPING";
        category[3] = "TRANSPORT";
        category[4] = "OTHERS";

        categorySymbols[0] = "%";
        categorySymbols[1] = "-";
        categorySymbols[2] = "@";
        categorySymbols[3] = "*";
        categorySymbols[4] = ".";

        int index = 0;
        System.out.println("--------------------------------------------------------"
                + "------\n"
                + "|  #  |   Category  |   Expense Amount     |     %   |Symbol|\n"
                + "|-------------------------------------------------------------");
        for (int i = 0; i < percentage.length; i++) {
            if (percentage[i] != 0) {
                System.out.printf("| %-4d | %-10s | %-20s | %-7.2f |   %s  |\n",
                        ++index, category[i], categoryAmount[i], percentage[i] * 100, categorySymbols[i]);
            }
        }
        System.out.println("--------------------------------------------------------"
                + "------\n");
    }

    /**
     * Stops the threat from running.
     */
    public void stop() {
        thread.interrupt();
    }

    /**
     * This method checks if expenses is empty before it decides to draw the pie chart.
     *
     * @param percentage Percentage of total expenses
     * @return true if empty, false if an existing expense is found
     */
    public static boolean hasEmptyExpense(float[] percentage) {
        for (float f : percentage) {
            if (f > 0) {
                return false;
            }
        }
        return true;
    }

    //@@author kyang96

    /**
     * Displays a bar graph showing how much money is spent for each day.
     */
    public static void printBarGraph(ArrayList<Expense> expenseList) {
        int maxLength = 20;
        TreeMap<LocalDate, Double> expenseMap = generateExpenseMap(expenseList);
        double maxValue = 0;
        for (Map.Entry<LocalDate, Double> entry : expenseMap.entrySet()) {
            maxValue = Math.max(maxValue, entry.getValue());
        }
        double barIncrement = maxValue / maxLength;
        System.out.println("Here is a bar graph of money spent per day:");
        for (Map.Entry<LocalDate, Double> entry : expenseMap.entrySet()) {
            System.out.print(DateTimeFormatter.ofPattern("dd/MM/yyyy").format(entry.getKey()) + " ");
            System.out.printf("| $%-6.2f ", entry.getValue());
            int numOfBar = (int) Math.ceil(entry.getValue() / barIncrement);
            String bar = "";
            for (int i = 0; i < numOfBar; i++) {
                bar += '#';
            }
            System.out.println("|" + bar + "|");
        }
    }

    /**
     * Generate a TreeMap of expenses for total amount of money spent for each date.
     */
    public static TreeMap<LocalDate, Double> generateExpenseMap(ArrayList<Expense> expenseList) {
        TreeMap<LocalDate, Double> expenseMap = new TreeMap<>();
        for (Expense expense : expenseList) {
            LocalDate date = expense.getDate();
            Double total = 0.0;
            if (expenseMap.get(date) != null) {
                total = expenseMap.get(date);
            }
            total += expense.getAmount();
            expenseMap.put(date, total);
        }
        return expenseMap;
    }
}
