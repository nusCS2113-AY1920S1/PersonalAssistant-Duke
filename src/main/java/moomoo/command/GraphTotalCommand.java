package moomoo.command;

import moomoo.task.Category;
import moomoo.task.ScheduleList;
import moomoo.task.Budget;
import moomoo.task.MooMooException;
import moomoo.task.CategoryList;
import moomoo.task.Storage;
import moomoo.task.Ui;

import java.util.ArrayList;

public class GraphTotalCommand extends Command {
    private final String fullBlock = "H";
    private final String halfBlock = "l";
    private final String topBorder = "v";
    private final String bottomBorder = "^";
    private String horizontalAxisTop = "";
    private String horizontalAxisBottom = "";
    private String output = "";
    
    public GraphTotalCommand() {
        super(false, "");
    }
    
    public static double roundToTwoDp(double d) {
        return Math.floor(d * 100) / 100;
    }
    
    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList catList,
                        Category category, Ui ui, Storage storage)
            throws MooMooException {
        catList.testPopulate();
        if (catList.size() == 0) {
            throw new MooMooException("OOPS!!! MooMoo cannot find any category data :(");
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
            
            String categoryName = catList.get(i).toString();
            if (categoryName.length() > 14) {
                categoryName = categoryName.substring(0, 11) + "...";
            }
            output = output + categoryName;
            
            for (int j = 0; j < (catList.getLongestCategory() - categoryName.length() + 1); j += 1) {
                output += " ";
            }
    
            int noOfFullBars = (int) percentage;
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
    }
}
