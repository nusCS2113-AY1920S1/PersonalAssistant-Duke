//@@author matthewng1996

package wallet.thread;

import wallet.logic.LogicManager;
import wallet.model.Wallet;
import wallet.model.record.Category;
import wallet.model.record.Expense;

public class ChartThread implements Runnable {
    private Wallet wallet;
    private Thread thread;

    /**
     * Custom thread.
     */
    public ChartThread() {
        thread = new Thread(this);
        thread.start();
    }

    /**
     * Runs the threat and stops when pie chart is completed.
     */
    @Override
    public void run() {
        printPieChart();
        stop();
    }

    /**
     * Prints the pie chart given specified stats.
     */
    public static void printPieChart() {

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

        float totalExpensesAmount = 0;
        double[] categoryAmount = new double[5];

        for (Expense e : LogicManager.getWallet().getExpenseList().getExpenseList()) {
            totalExpensesAmount += e.getAmount();
        }

        for (Expense e : LogicManager.getWallet().getExpenseList().getExpenseList()) {
            Category category = e.getCategory();
            switch (category) {
                case FOOD:
                    percentage[0] += e.getAmount() / totalExpensesAmount;
                    categoryAmount[0] += e.getAmount();
                    break;

                case BILLS:
                    percentage[1] += e.getAmount() / totalExpensesAmount;
                    categoryAmount[1] += e.getAmount();
                    break;

                case SHOPPING:
                    percentage[2] += e.getAmount() / totalExpensesAmount;
                    categoryAmount[2] += e.getAmount();
                    break;

                case TRANSPORT:
                    percentage[3] += e.getAmount() / totalExpensesAmount;
                    categoryAmount[3] += e.getAmount();
                    break;

                case OTHERS:
                    percentage[4] += e.getAmount() / totalExpensesAmount;
                    categoryAmount[4] += e.getAmount();
                    break;
            }
        }

        drawTable(categoryAmount, percentage);

        for (float y = -radius; y < radius; y++) {
            char character = '-';
            for (float x = -radius; x < radius; x++) {
                if (x * x + y * y < radius * radius) {
                    double angle = Math.atan2(y, x) / Math.PI / 2 + .5f;
                    character = set(fill, percentage, angle);
                    System.out.print(character); //Prints circle content
                } else {
                    character = ' ';
                    System.out.print(character); //Prints spaces
                }
            }
            System.out.println(character); //Form new line
        }
    }

    /**
     * This method fills the pie chart.
     * @param fill Symbols to fill pie chart
     * @param percentage Array of percentages for each category
     * @param angle Calculated angle for the pie chart
     * @return could return a space, a symbol or recursively run function for the next symbol.
     */
    public static char set(char[] fill, float[] percentage, double angle) {

        if (percentage.length == 0) {
            return ' ';
        }

        if (angle < percentage[0]) {
            return fill[0];
        }

        char[] fillArray = new char [fill.length - 1];
        float[] percentageArray = new float [percentage.length - 1];
        angle -= percentage[0];

        for (int i = 0; i < fillArray.length; i++) {
            fillArray[i] = fill[i + 1];
            percentageArray[i] = percentage[i + 1];
        }
        return set(fillArray, percentageArray, angle);
    }

    public static void drawTable(double[] categoryAmount, float[] percentage) {
        String[] category = new String[5];
        category[0] = "FOOD";
        category[1] = "BILLS";
        category[2] = "SHOPPING";
        category[3] = "TRANSPORT";
        category[4] = "OTHERS";

        int index = 0;
        System.out.println("------------------------------------------------------"
                + "------\n"
                + "|  #  |   Category     |   Expense Amount     |     %     |\n"
                + "|----------------------------------------------------------");
        for (int i = 0; i < percentage.length; i++) {
            if (percentage[i] != 0) {
                System.out.printf("| %-4d | %-13s | %-20s |  %-8.2f |\n", ++index, category[i], categoryAmount[i], percentage[i] * 100);
            }
        }
        System.out.println("------------------------------------------------------"
                + "------\n");
    }

    /**
     * TODO: MAKE SURE THE FIRST INDEX OF PERCENTAGE CANNOT BE THE SMALLEST.
     */
    public static void rearrangePercentageTable() {

    }

    /**
     * Stops the threat from running.
     */
    public void stop() {
        thread.interrupt();
    }
}
