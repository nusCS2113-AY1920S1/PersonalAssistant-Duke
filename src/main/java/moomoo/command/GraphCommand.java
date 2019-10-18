package moomoo.command;

import moomoo.task.Category;
import moomoo.task.ScheduleList;
import moomoo.task.Budget;
import moomoo.task.MooMooException;
import moomoo.task.CategoryList;
import moomoo.task.Storage;
import moomoo.task.Ui;

import java.util.ArrayList;

public class GraphCommand extends Command {
    private ArrayList<String> verticalAxis;
    private final String fullBlock = "@";
    private final String halfBlock = "#";
    private final String topBorder = "$";
    private final String bottomBorder = "%";
    private String horizontalAxisTop = "";
    private String horizontalAxisBottom = "";
    private String output = "";
    
    public GraphCommand(String input) {
        super(false, input);
        verticalAxis = new ArrayList<String>();
    }
    
    public static double roundToTwoDp(double d) {
        return Math.floor(d * 100) / 100;
    }
    
    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList catList,
                        Category category, Ui ui, Storage storage)
            throws MooMooException {
        if (input.length() < 7) {
            throw new MooMooException("OOPS!!! Please use the total/[CATEGORY} sub-command");
        }
        input = input.substring(6);
        if ("total".equals(input)) {
            if (catList.size() == 0) {
                throw new MooMooException("OOPS!!! MooMoo cannot find any expenditure data :(");
            }
            for (int i = 0; i < catList.size(); i += 1) {
                String categoryName = catList.get(i).toString();
                if (categoryName.length() > 14) {
                    categoryName = categoryName.substring(0, 11) + "...";
                }
                verticalAxis.add(categoryName);
            }

            double grandTotal = catList.getGrandMonthTotal(1);
            int maxAxisUnit = (int) ((catList.getLargestExpenditure(1) / grandTotal) * 100) + 1;
            for (int i = 0; i < maxAxisUnit; i += 1) {
                horizontalAxisTop += topBorder;
                horizontalAxisBottom += bottomBorder;
            }

            String topSpace = "";
            for (int i = 0; i < catList.getLongestCategory(); i += 1) {
                topSpace += " ";
            }
            output += topSpace + horizontalAxisTop + "\n";

            for (int i = 0; i < catList.size(); i += 1) {
                Category cat = catList.get(i);
                double percentage = 100 * (cat.getMonthlyTotal(1) / grandTotal);
                percentage = roundToTwoDp(percentage);
                
                int noOfFullBars = (int) percentage;
                output = output + verticalAxis.get(i);
                for (int j = 0; j < (catList.getLongestCategory() - verticalAxis.get(i).length() + 1); j += 1) {
                    output += " ";
                }
                for (int j = 0; j < noOfFullBars; j += 1) {
                    output = output + fullBlock;
                }
                int noOfHalfBars = (int) Math.round(percentage % 1);
                if (noOfHalfBars == 1) {
                    output = output + halfBlock;
                }
                output = output + "  " + percentage + "%\n";
            }
            output += topSpace + horizontalAxisBottom + "\n";
            ui.setOutput(output);
        } else {
            throw new MooMooException("OOPS!!! Wrong sub-command! Please use the total/[CATEGORY} sub-command");
        }
    }
}
