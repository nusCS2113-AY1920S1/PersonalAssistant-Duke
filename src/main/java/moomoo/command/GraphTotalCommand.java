package moomoo.command;

import moomoo.feature.category.Category;
import moomoo.feature.ScheduleList;
import moomoo.feature.Budget;
import moomoo.feature.MooMooException;
import moomoo.feature.category.CategoryList;
import moomoo.feature.storage.Storage;
import moomoo.feature.Ui;

import java.time.LocalDate;

public class GraphTotalCommand extends Command {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    
    private final String fullBlock = "\u2588"; //"H";
    private final String halfBlock = "\u258c"; //"l";
    private final String topBorder = "\u252c";//"v";
    private final String bottomBorder = "\u2534";//"^";
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
    public void execute(ScheduleList calendar, Budget budget, CategoryList categoryList,
                        Ui ui, Storage storage)
            throws MooMooException {

        if (categoryList.size() == 0) {
            throw new MooMooException("OOPS!!! MooMoo cannot find any category data :(");
        }
        
        double grandTotal = categoryList.getTotal();
        if (grandTotal == 0) {
            grandTotal = 1;
        }
        int maxAxisUnit = (int) ((categoryList.getLargestExpenditure(LocalDate.now().getMonthValue()) / grandTotal)
                * 100);
        for (int i = 0; i < maxAxisUnit; i += 1) {
            horizontalAxisTop += topBorder;
            horizontalAxisBottom += bottomBorder;
        }
        
        horizontalAxisTop = ANSI_YELLOW + horizontalAxisTop + ANSI_RESET;
        horizontalAxisBottom = ANSI_YELLOW + horizontalAxisBottom + ANSI_RESET;
        
        String topSpace = "";
        for (int i = 0; i < categoryList.getLongestCategory(); i += 1) {
            topSpace += " ";
        }
        output += topSpace + horizontalAxisTop + "\n";
        
        for (int i = 0; i < categoryList.size(); i += 1) {
            Category cat = categoryList.get(i);
            double percentage = 100 * (cat.getTotal() / grandTotal);
            percentage = roundToTwoDp(percentage);
            
            String categoryName = categoryList.get(i).name();
            if (categoryName.length() > 14) {
                categoryName = categoryName.substring(0, 11) + "...";
            }
            
            if (i % 2 == 0) {
                output = output + ANSI_CYAN + categoryName;
            } else {
                output = output + categoryName;
            }
            
            for (int j = 0; j < (categoryList.getLongestCategory() - categoryName.length() + 1); j += 1) {
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
    
            if (i % 2 == 0) {
                output = output + ANSI_RESET;
            }
        }
        output += topSpace + horizontalAxisBottom + "\n";
        ui.setOutput(output);
    }
}
