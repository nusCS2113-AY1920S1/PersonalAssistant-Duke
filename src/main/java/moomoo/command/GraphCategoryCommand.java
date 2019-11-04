package moomoo.command;

import moomoo.task.Budget;
import moomoo.task.category.Category;
import moomoo.task.category.CategoryList;
import moomoo.task.category.Expenditure;
import moomoo.task.MooMooException;
import moomoo.task.ScheduleList;
import moomoo.task.Storage;
import moomoo.task.Ui;

public class GraphCategoryCommand extends Command {
    private final String fullBlock = "\u2588"; //"H";
    private final String halfBlock = "\u258c"; //"l";
    private final String topBorder = "\u252c";//"v";
    private final String bottomBorder = "\u2534";//"^";
    private String horizontalAxisTop = "";
    private String horizontalAxisBottom = "";
    private String output = "";
    private String categoryName = "";
    private int month = 0;
    private int year = 0;
    
    /**
     * Constructor requires the category for which to display the graph, and the respective date.
     *
     * @param categoryName The name of the category that the user enters
     * @param month        The user's desired month
     */
    public GraphCategoryCommand(String categoryName, int month, int year) {
        super(false, "");
        this.categoryName = categoryName;
        this.month = month;
        this.year = year;
    }
    
    public static double roundToTwoDp(double d) {
        return Math.floor(d * 100) / 100;
    }
    
    @Override
    public void execute(ScheduleList calendar, Budget budget, CategoryList catList,
                        Category category, Ui ui, Storage storage)
            throws MooMooException {
        Category cat;
        try {
            cat = catList.get(categoryName);
        } catch (Exception e) {
            throw new MooMooException("OH NO! No such category exists!");
        }
        if (cat.size() == 0) {
            throw new MooMooException("OOPS!!! MooMoo cannot find any expenditure data :(");
        }
        
        double grandTotal = cat.getTotal(month, year);
        int maxAxisUnit = (int) ((cat.getLargestExpenditure() / grandTotal) * 100) + 1;
        for (int i = 0; i < maxAxisUnit; i += 1) {
            horizontalAxisTop += topBorder;
            horizontalAxisBottom += bottomBorder;
        }
        
        String topSpace = "";
        for (int i = 0; i < cat.getLongestExpenditure(); i += 1) {
            topSpace += " ";
        }
        output += topSpace + horizontalAxisTop + "\n";
        
        for (int i = 0; i < cat.size(); i += 1) {
            String expenditureName = cat.get(i).toString();
            if (expenditureName.length() > 14) {
                expenditureName = expenditureName.substring(0, 11) + "...";

            }
            output = output + expenditureName;
            
            for (int j = 0; j < (cat.getLongestExpenditure() - expenditureName.length() + 1); j += 1) {
                output += " ";
            }
            
            Expenditure exp = cat.get(i);
            double percentage = 100 * (exp.getCost() / grandTotal);
            int noOfFullBars = (int) percentage;
            for (int j = 0; j < noOfFullBars; j++) {
                output = output + fullBlock;
            }
            int noOfHalfBars = (int) Math.round(percentage % 1);
            if (noOfHalfBars == 1) {
                output = output + halfBlock;
            }
            output = output + "  " + roundToTwoDp(percentage) + "%\n";
        }
        output += topSpace + horizontalAxisBottom + "\n";
        ui.setOutput(output);
        
    }
}
