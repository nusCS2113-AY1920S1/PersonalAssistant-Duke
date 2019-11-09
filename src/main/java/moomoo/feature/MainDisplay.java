package moomoo.feature;

import moomoo.feature.category.CategoryList;
import moomoo.command.DetectOsCommand;

public class MainDisplay {

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_BLACK = "\u001B[30m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";


    //private final String TOP_BORDER_NEW_COLUMN = "-------------.";
    private static final String TOP_BORDERLEFT = ".-------------------.";
    private static final String TOP_BORDERRIGHT = "-----------------------------------------------------------------"
            + "-------------------------.-------------.-------------.\n";
    private static final String BORDER_LEFT = "|                   |";
    private static final String BORDER_RIGHT = "              |                        |                |"
            + "                   |\n";
    private static final String MONTH_LEFT = "|Month: ";
    private static final String YEAR_LEFT = "|Year: ";
    private static final String BUDGET_LEFT = "|Budget: ";
    private static final String SAVINGS_LEFT = "|Savings: ";
    private static DetectOsCommand getOS = new DetectOsCommand();
    private static String os = getOS.osName;
    private static final String COLUMN_TITLES_COLOUR = " <" + ANSI_BLUE + "Categories" + ANSI_RESET
            + "> |                         " + "       <" + ANSI_CYAN + "Amount" + ANSI_RESET + ">                    "
            + "               |   <" + ANSI_PURPLE + "Budget" + ANSI_RESET + ">  |  <"
            + ANSI_YELLOW + "Savings" + ANSI_RESET + ">  |\n";
    private static final String COLUMN_TITLES = " <Categories > |                         "
            + "       <Amount>                    "
            + "               |   <Budget>  |  <Savings>  |\n";

    public MainDisplay() {

    }

    public int getCatListSize(CategoryList categoryList) {
        int catNum = categoryList.size();
        return catNum;
    }

    /**
     * Returns the current maximum category size in the categoryList.
     *
     * @param categoryList The CategoryList containing all the categories.
     * @return the maximum category size.
     */
    public int getMaxCatSize(CategoryList categoryList) throws MooMooException {
        int maxCatSize = 0;
        for (int i = 0; i < categoryList.size(); i++) {
            int newCatSize = 0;
            for (int j = 0; j < categoryList.get(i).size(); j++) {
                newCatSize += 1;
            }
            if (newCatSize >= maxCatSize) {
                maxCatSize = newCatSize;
            }
        }
        return maxCatSize;
    }

    private String[] monthsInYear = {
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    };


    private String blankSpace = "";
    private String blankSpaceCat = "";
    private String blankSpaceExp = "";
    private String blankSpaceBud = "";
    private String blankSpaceSav = "";

    /**
     * Function to that takes in all the inputs and converts them into output to be displayed on the main display panel.
     *
     * @param month        Month to be shown.
     * @param year         Year to be shown.
     * @param rows         Number of rows in the panel.
     * @param cols         Number of columns in the panel.
     * @param categoryList CategoryList to be passed into the function.
     * @param budget       Budget to be passed into the function.
     * @return a string corresponding to the final output that is being displayed.
     */
    public String toPrint(int month, int year, int rows, int cols, CategoryList categoryList, Budget budget)
            throws MooMooException {

        int expGap = 76;
        if (cols != 0) {
            expGap = (76 / cols) - 1;
        }
        String output = TOP_BORDERLEFT + TOP_BORDERRIGHT;
        for (int i = 0; i <= 11 - monthsInYear[month - 1].length(); i++) {
            blankSpace += " ";
        }
        if (!os.contains("win")) { //check OS version to print colour
            output += MONTH_LEFT + monthsInYear[month - 1] + blankSpace + "|" + COLUMN_TITLES_COLOUR;
        } else {
            output += MONTH_LEFT + monthsInYear[month - 1] + blankSpace + "|" + COLUMN_TITLES;
        }
        blankSpace = "";
        output += YEAR_LEFT + year + "         |" + TOP_BORDERRIGHT;

        for (int row = 0; row < rows; row++) {             // Run this loop once for each Category
            blankSpaceCat = "";
            for (int j = 0; j < 14 - categoryList.get(row).name().length(); j++) {
                blankSpaceCat += " ";
            }
            if (row == 0) {
                //output += BUDGET_LEFT + "$" + "budget" + "    " + "|";
                output += BORDER_LEFT;
            } else {
                output += BORDER_LEFT;
            }


            output += categoryList.get(row).name() + blankSpaceCat + "|";

            for (int col = 0; col < categoryList.get(row).size(); col++) {   // Run this loop once for each expenditure
                blankSpaceExp = "";
                for (int k = 0; k < (expGap - categoryList.get(row).get(col).getLength() - 1); k++) {
                    blankSpaceExp += " ";
                }
                output += "$" + categoryList.get(row).get(col).getCostString() + blankSpaceExp + "|";
            }


            for (int col1 = 0; col1 < cols - categoryList.get(row).size(); col1++) {
                blankSpaceExp = "";
                for (int l = 0; l < expGap; l++) {
                    blankSpaceExp += " ";
                }
                output += blankSpaceExp + "|";
            }

            if (cols == 0) {
                blankSpaceExp = "";
                for (int l = 0; l < (expGap - 1); l++) {
                    blankSpaceExp += " ";
                }
                output += blankSpaceExp + "|";
            }

            double bud = 0.0;
            if (budget.getBudgetFromCategory(categoryList.get(row).name()) != 0.0) {
                bud = budget.getBudgetFromCategory(categoryList.get(row).name());
                blankSpaceBud = "";
                String budgetStr = Double.toString(budget.getBudgetFromCategory(categoryList.get(row).name()));
                for (int k = 0; k < 13 - budgetStr.length(); k++) {
                    blankSpaceBud += " ";
                }
                output += budgetStr + blankSpaceBud + "|";
            } else {
                output += "             |";
            }

            double tot = categoryList.get(row).getTotal();
            double sav = bud - tot;
            if (sav != 0.0) {
                blankSpaceSav = "";
                String savingStr = Double.toString(sav);
                for (int h = 0; h < 13 - savingStr.length(); h++) {
                    blankSpaceSav += " ";
                }
                output += savingStr + blankSpaceSav + "|";
            } else {
                output += "             |";
            }

            if (row == rows - 1) {
                output += "\n" + TOP_BORDERLEFT + TOP_BORDERRIGHT;
            } else if (row == 0) {
                //output += "\n" + SAVINGS_LEFT + "$" + "savings" + "  " + "|" + TOP_BORDER_RIGHT;
                output += "\n" + BORDER_LEFT + TOP_BORDERRIGHT;
            } else {
                output += "\n" + BORDER_LEFT + TOP_BORDERRIGHT;
            }

        }
        //output += BORDER_LEFT + BORDER_RIGHT;               // Testing
        return output;
    }

    public void monthSummaryDisplay() {

    }

    private static final String DEFAULT =
            ".------------------.--------------------------------------------------------------------------.\n"
                    + "|Month:            | <Categories> |        <Amount>        |    <Budget>   |     <Total>     |\n"
                    + "|Year:             |-------------------------------------------------------------------------.\n"
                    + "|Budget:           |Food          |     |      |     |     |     $50.0     |      $150.0     |\n"
                    + "|Savings:          |-------------------------------------------------------------------------.\n"
                    + "|                  |Transport     |     |      |     |     |               |                 |\n"
                    + "|                  |-------------------------------------------------------------------------.\n"
                    + "|                  |Entertainment |     |      |     |     |               |                 |\n"
                    + "|                  |-------------------------------------------------------------------------.\n"
                    + "|                  |              |     |      |     |     |               |                 |\n"
                    + "|                  |-------------------------------------------------------------------------.\n"
                    + "|                  |              |     |      |     |     |               |                 |\n"
                    + "|                  |-------------------------------------------------------------------------.\n"
                    + "|                  |              |     |      |     |     |               |                 |\n"
                    + "|                  |-------------------------------------------------------------------------.\n"
                    + "|                  |              |     |      |     |     |               |                 |\n"
                    + ".------------------.-------------------------------------------------------------------------.\n"
                    + "\n";         // Testing Final Output Appearance AND default table
}
