package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.Category;
import moomoo.task.CategoryList;
import moomoo.task.MooMooException;
import moomoo.task.Storage;
import moomoo.task.TransactionList;
import moomoo.task.Ui;

import java.util.ArrayList;

public class GraphCommand extends Command {
    private ArrayList<String> verticalAxis;
    private final String fullBlock = "█";
    private final String halfBlock = "▌";
    private final String topBorder = "┬";
    private final String bottomBorder = "┴";
    private String horizontalAxisTop = "";
    private String horizontalAxisBottom = "";
    private String output = "";
    
    public GraphCommand(String input) {
        super(false, input);
        verticalAxis = new ArrayList<String>();
    }
    
    public static double roundToHalf(double d) {
        return Math.round(d * 2) / 2.0;
    }
    
    @Override
    public void execute(Budget budget, CategoryList catList, TransactionList transList, Ui ui, Storage storage)
            throws MooMooException {
        if (input.length() < 7) {
            throw new MooMooException("OOPS!!! Please use the total/[CATEGORY} sub-command");
        }
        input = input.substring(6);
        if (input.equals("total")) {
            if (catList.size() == 0) {
                throw new MooMooException("OOPS!!! MooMoo cannot find any expenditure data :(");
            }
            for (int i = 0; i < catList.size(); i += 1) {
                String categoryName = catList.get(i).toString();
                if (categoryName.length() > 14) {
                    categoryName = categoryName.substring(0, 10) + "...";
                }
                verticalAxis.add(categoryName);
            }
            double grandTotal = catList.getGrandMonthTotal();
            int maxAxisUnit = (int) (grandTotal / catList.getLargestExpenditure()) + 1;
            for (int i = 0; i < maxAxisUnit; i += 1) {
                horizontalAxisTop += topBorder;
                horizontalAxisBottom += bottomBorder;
            }
            //15 spaces before xAxisTop
            output = "               " + horizontalAxisTop + "\n";
            System.out.println(catList.size());
            for (int i = 0; i < catList.size(); i += 1) {
                Category category = catList.get(i);
                double percentage = 10 * (category.getCategoryMonthTotal() / grandTotal);
                percentage = roundToHalf(percentage);
                int noOfFullBars = (int) percentage;
                int noOfHalfBars = (int) Math.round(percentage % 1);
                System.out.println(verticalAxis.get(i));
                output = output + verticalAxis.get(i) + " \n";
                for (int j = 0; j < noOfFullBars; j += 1) {
                    output = output + fullBlock;
                }
                if (noOfHalfBars == 1) {
                    output = output + halfBlock + "\n";
                }
            }
            output = "               " + horizontalAxisBottom + "\n";
            ui.setOutput(output);
        } else {
            throw new MooMooException("OOPS!!! Wrong sub-command! Please use the total/[CATEGORY} sub-command");
        }
    }
}
