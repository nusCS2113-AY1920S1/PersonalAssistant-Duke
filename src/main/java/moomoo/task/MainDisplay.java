package moomoo.task;

import moomoo.task.category.CategoryList;
import java.util.HashMap;
import java.util.ArrayList;



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
    private static final String TOP_BORDERRIGHT = "--------------------------";
    private static final String BORDER_LEFT = "|                   |";
    private static final String BORDER_RIGHT = "              |                        |                |"
            + "                   |\n";
    private static final String MONTH_LEFT = "|Month: ";
    private static final String YEAR_LEFT = "|Year: ";
    private static final String BUDGET_LEFT = "|" + ANSI_CYAN + "Budget" + ANSI_RESET + ":            |";
    private static final String SAVINGS_LEFT = "|" + ANSI_YELLOW + "Savings" + ANSI_RESET + ":           |";
    private static final String TOTAL_LEFT = "|" + ANSI_GREEN + "Total" + ANSI_RESET + ":             |";
    private static final String MISC_TITLES = "|" + TOP_BORDERRIGHT + ".\n"
            + BORDER_LEFT + "misc                      |\n"
            + BORDER_LEFT + TOP_BORDERRIGHT + ".\n"
            + BORDER_LEFT + "              |           |\n"
            + TOP_BORDERLEFT + TOP_BORDERRIGHT + ".\n"
            + TOTAL_LEFT + "                          |\n"
            + TOP_BORDERLEFT + TOP_BORDERRIGHT + ".\n"
            + BUDGET_LEFT + "                          |\n"
            + TOP_BORDERLEFT + TOP_BORDERRIGHT + ".\n"
            + SAVINGS_LEFT + "                          |\n"
            + TOP_BORDERLEFT + TOP_BORDERRIGHT + ".\n";

    public MainDisplay() {

    }

    /**
     * This function is called when user specifies a month and year.
     * We want to find out the amount of Categories that has expenditures as this will be the number of columns
     * in our table output.
     * @param categoryList  categoryList to be passed into the function.
     * @param month month that the user wants to see a summary of.
     * @param year  year that the user wants to see a summary of.
     * @return an Integer corresponding to the number of columns.
     */
    public int getMonthsCatListSize(CategoryList categoryList, int month, int year) {
        int monthsCatSize = 0;
        int catGotExp;
        for (int i = 0; i < categoryList.size(); i++) {
            catGotExp = 0;
            for (int j = 0; j < categoryList.get(i).size(); j++) {
                if (categoryList.get(i).get(j).getDate().getMonthValue() == month
                        && categoryList.get(i).get(j).getDate().getYear() == year) {
                    catGotExp += 1;
                }
            }
            if (catGotExp >= 1) {
                monthsCatSize += 1;      // if this Category has an expenditure, it is valid so count it
            }
        }
        return monthsCatSize;
    }

    /**
     * This function is called when user specifies a month and year.
     * We want to find out the maximum amount of Expenditures in a valid Category as this will be the number of rows
     * in our table output.
     * @param categoryList  categoryList to be passed into the function.
     * @param month month that the user wants to see a summary of.
     * @param year  year that the user wants to see a summary of.
     * @return an Integer corresponding to the number of rows.
     */
    public int getMonthsExpSize(CategoryList categoryList, int month, int year) {
        int monthsExpSize = 0;
        int expSize;
        for (int i = 0; i < categoryList.size(); i++) {
            expSize = 0;
            for (int j = 0; j < categoryList.get(i).size(); j++) {
                if (categoryList.get(i).get(j).getDate().getMonthValue() == month
                        && categoryList.get(i).get(j).getDate().getYear() == year) {
                    expSize += 1;
                }
            }
            if (expSize != 0 && expSize >= monthsExpSize) {
                monthsExpSize = expSize;
            }
        }
        return monthsExpSize;
    }

    /**
     * This function is called when user does not specify a month and a year.
     * We want to find out the amount of Categories that are in the CategoryList as this will be the number of columns
     * in our table output.
     * @param categoryList  categoryList to be passed into the function.
     * @return an Integer corresponding to the number of columns.
     */
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
    public int getMaxCatSize(CategoryList categoryList) {
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


    private String openCloseLines = "";
    private String blankSpaceMth = "";
    private String blankSpaceYr = "";
    private String blankSpaceCat = "";
    private String blankSpaceExp = "";
    private String blankSpaceBud = "";
    private String blankSpaceSav = "";
    private String blankSpaceCost = "";
    private String blankSpaceTot = "";


    /**
     * This function takes in several parameters in order to append together a final string to be printed out as a
     * table as final output.
     * @param month month that the user wants to view
     * @param year year that the user wants to view
     * @param rows rows corresponds to the max number of expenditures under a category
     * @param cols cols corresponds to the total number of categories in categoryList
     * @param categoryList categoryList is an array list that stores all the categories
     * @param budget budget stores all the individual budgets of each categories
     * @return returns a string to be printed out as the main display
     */
    public String newToPrint(int month, int year, int rows, int cols, CategoryList categoryList, Budget budget) {
        String output = "";

        for (int i = 0; i < ((27 * cols - 12) / 2); i++) {          // Condition for Category Space Set
            blankSpaceCat += " ";
        }
        for (int i = 0; i < (27 * cols - 1); i++) {                     // Condition for Top Header Set
            openCloseLines += "-";
        }
        openCloseLines += ".";

        if (cols == 0) {        // Scenario where there is no Category in the specified periods
            output = TOP_BORDERLEFT + TOP_BORDERRIGHT  + ".\n";
            blankSpaceCat = "       ";

            if (month == 0 && year == 0) {          //  Case 1: View All -> Default Misc
                for (int i = 0; i <= 8; i++) {
                    blankSpaceMth += " ";
                }
                for (int i = 0; i <= 9; i++) {
                    blankSpaceYr += " ";
                }
                output += MONTH_LEFT + "All" + blankSpaceMth + "|" + blankSpaceCat
                        + "<" + ANSI_BLUE + "Categories" + ANSI_RESET + ">" + blankSpaceCat + "|\n" + YEAR_LEFT
                        + "All" + blankSpaceYr + MISC_TITLES;
            } else {                                //  Case 2: View Specific Month -> Default Misc
                for (int i = 0; i <= 11 - monthsInYear[month - 1].length(); i++) {
                    blankSpaceMth += " ";
                }
                for (int i = 0; i <= 8; i++) {
                    blankSpaceYr += " ";
                }
                output += MONTH_LEFT + monthsInYear[month - 1] + blankSpaceMth + "|" + blankSpaceCat
                        + "<" + ANSI_BLUE + "Categories" + ANSI_RESET + ">" + blankSpaceCat + "|\n" + YEAR_LEFT
                        + year + blankSpaceYr + MISC_TITLES;
            }
        } else {            // Scenario where there are Categories in the specified periods
            output += TOP_BORDERLEFT + openCloseLines + "\n";

            if (month == 0 && year == 0) {          //  Case 1: View All
                for (int i = 0; i <= 8; i++) {
                    blankSpaceMth += " ";
                }
                for (int i = 0; i <= 9; i++) {
                    blankSpaceYr += " ";
                }
                output += MONTH_LEFT + "All" + blankSpaceMth + "|" + blankSpaceCat
                        + "<" + ANSI_BLUE + "Categories" + ANSI_RESET + ">";

                if (cols % 2 == 0) {
                    String blankSpaceTemp = blankSpaceCat;
                    blankSpaceTemp = blankSpaceTemp.substring(0, blankSpaceTemp.length() - 1);
                    output += blankSpaceTemp;
                } else {
                    output += blankSpaceCat;
                }

                output += "|\n" + YEAR_LEFT + "All" + blankSpaceYr + "|";
                for (int i = 0; i < cols; i++) {
                    output += TOP_BORDERRIGHT + ".";
                }

                output += "\n" + BORDER_LEFT;

                // Printing out the line with all the categories
                for (int i = 0; i < cols; i++) {
                    blankSpaceCat = "";
                    for (int j = 0; j < (26 - categoryList.get(i).toString().length()); j++) {
                        blankSpaceCat += " ";
                    }
                    output += ANSI_BLACK + categoryList.get(i).toString() + ANSI_RESET + blankSpaceCat + "|";
                }
                output += "\n" + BORDER_LEFT + openCloseLines + "\n";

                // Printing out the lines corresponding to number of rows with both expenditureName and Amount
                for (int i = 0; i < rows; i++) {
                    output += BORDER_LEFT;
                    for (int j = 0; j < cols; j++) {
                        if (i < categoryList.get(j).size()) {
                            // prints out each individual expenditureName if it exists
                            blankSpaceExp = "";
                            for (int h = 0; h < (17 - categoryList.get(j).get(i).toString().length()); h++) {
                                blankSpaceExp += " ";
                            }
                            blankSpaceCost = "";
                            // prints out each individual expenditure Cost if it exists
                            for (int k = 0; k < (7 - categoryList.get(j).get(i).getCostString().length()); k++) {
                                blankSpaceCost += " ";
                            }
                            output += categoryList.get(j).get(i).toString() + blankSpaceExp + "|$"
                                + categoryList.get(j).get(i).getCostString() + blankSpaceCost + "|";
                        } else {
                            // if expenditure dosen't exist, print out the filler space
                            output += "                 |        |";
                        }
                    }
                    output += "\n";
                }

                output += TOP_BORDERLEFT + openCloseLines + "\n";
                output += TOTAL_LEFT;
                for (int i = 0; i < categoryList.size(); i++) {
                    blankSpaceTot = "";
                    for (int j = 0; j < (25 - Double.toString(categoryList.get(i).getOverallAmount()).length()); j++) {
                        blankSpaceTot += " ";
                    }
                    output += "$" + categoryList.get(i).getOverallAmount() + blankSpaceTot + "|";
                }

                output += "\n" + TOP_BORDERLEFT + openCloseLines + "\n";
                output += BUDGET_LEFT;
                for (int i = 0; i < categoryList.size(); i++) {
                    blankSpaceBud = "";
                    String budName = Double.toString(budget.getBudgetFromCategory(categoryList.get(i).toString()));
                    int budLen = 25 - budName.length();
                    for (int j = 0; j < budLen; j++) {
                        blankSpaceBud += " ";
                    }
                    output += "$" + budName + blankSpaceBud + "|";
                }

                output += "\n" + TOP_BORDERLEFT + openCloseLines + "\n";
                output += SAVINGS_LEFT;

                for (int i = 0; i < categoryList.size(); i++) {
                    blankSpaceSav = "";
                    double tot = categoryList.get(i).getOverallAmount();
                    double bud = budget.getBudgetFromCategory(categoryList.get(i).toString());
                    double sav = bud - tot;
                    for (int j = 0; j < (25 - Double.toString(sav).length()); j++) {
                        blankSpaceSav += " ";
                    }
                    if (sav < 0) {
                        output += "$" + ANSI_RED + sav + ANSI_RESET + blankSpaceSav + "|";
                    } else {
                        output += "$" + sav + blankSpaceSav + "|";
                    }
                }
                output += "\n" + TOP_BORDERLEFT + openCloseLines + "\n";


            } else {                                //  Case 2: View Specific Month
                for (int i = 0; i <= 11 - monthsInYear[month - 1].length(); i++) {
                    blankSpaceMth += " ";
                }
                for (int i = 0; i <= 8; i++) {
                    blankSpaceYr += " ";
                }

                // Every time the main display command is called user specifies month/year create new temp hash map
                HashMap<String,ArrayList<String>> newCategoryList = new HashMap<>();

                // Initializes the HashMap for use later as newCategoryList
                for (int i = 0; i < categoryList.size(); i++) {
                    // If array list does not exist, create it
                    ArrayList<String> stringList = new ArrayList<>();
                    for (int j = 0; j < categoryList.get(i).size(); j++) {
                        // Add to array list only if it matches the month and year user is looking for
                        if (categoryList.get(i).get(j).getDate().getMonthValue() == month
                                && categoryList.get(i).get(j).getDate().getYear() == year) {
                            stringList.add(categoryList.get(i).get(j).toString());
                            stringList.add(categoryList.get(i).get(j).getCostString());
                        }
                    }
                    newCategoryList.put(categoryList.get(i).toString(), stringList);
                }

                output += MONTH_LEFT + monthsInYear[month - 1] + blankSpaceMth + "|" + blankSpaceCat
                        + "<" + ANSI_BLUE + "Categories" + ANSI_RESET + ">";

                if (cols % 2 == 0) {
                    String blankSpaceTemp = blankSpaceCat;
                    blankSpaceTemp = blankSpaceTemp.substring(0, blankSpaceTemp.length() - 1);
                    output += blankSpaceTemp;
                } else {
                    output += blankSpaceCat;
                }

                output += "|\n" + YEAR_LEFT + year + blankSpaceYr + "|";
                for (int i = 0; i < cols; i++) {
                    output += TOP_BORDERRIGHT + ".";
                }
                output += "\n" + BORDER_LEFT;

                // Printing out the line with all the categories
                for (int i = 0; i < cols; i++) {
                    blankSpaceCat = "";
                    for (int j = 0; j < (26 - categoryList.get(i).toString().length()); j++) {
                        blankSpaceCat += " ";
                    }
                    output += ANSI_BLACK + categoryList.get(i).toString() + ANSI_RESET + blankSpaceCat + "|";
                }
                output += "\n" + BORDER_LEFT + openCloseLines + "\n";

                System.out.println(rows);
                System.out.println(cols);

                // Printing out the lines corresponding to number of rows with both expenditureName and Amount
                for (int i = 0; i < rows; i++) {
                    output += BORDER_LEFT;
                    for (int j = 0; j < cols; j++) {
                        //if (i < categoryList.get(j).size()) {
                        String categoryName = categoryList.get(j).toString();
                        //if (i < (newCategoryList.get(categoryName).size() / 2)) {
                        if (i < categoryList.get(j).size()) {
                            String expenditureName = "";
                            String amountString = "";
                            if (!newCategoryList.get(categoryName).isEmpty()) {
                                // for nth row, nth category, extract the expenditure name (odd no in array list)
                                expenditureName = newCategoryList.get(categoryName).get(0);
                                // for nth row, nth category, extract the expenditure cost (even no in array list)
                                amountString = newCategoryList.get(categoryName).get(1);
                            }
                            for (int k = 0; k < 2; k++) {
                                if (!newCategoryList.get(categoryName).isEmpty()) {
                                    newCategoryList.get(categoryName).remove(0);
                                }
                            }
                            blankSpaceExp = "";
                            for (int h = 0; h < (17 - expenditureName.length()); h++) {
                                blankSpaceExp += " ";
                            }
                            blankSpaceCost = "";
                            // prints out each individual expenditure Cost if it exists
                            for (int g = 0; g < (7 - amountString.length()); g++) {
                                blankSpaceCost += " ";
                            }

                            output += expenditureName + blankSpaceExp + "|$" + amountString + blankSpaceCost + "|";
                        } else {
                            // if expenditure dosen't exist, print out the filler space
                            output += "                 |        |";
                        }
                    }
                    output += "\n";
                }

                // Prints out the line that contains all the total cost for each category
                output += TOP_BORDERLEFT + openCloseLines + "\n";
                output += TOTAL_LEFT;
                for (int i = 0; i < categoryList.size(); i++) {
                    blankSpaceTot = "";
                    int totalLen = Double.toString(categoryList.get(i).getTotal(month, year)).length();
                    for (int j = 0; j < (25 - totalLen); j++) {
                        blankSpaceTot += " ";
                    }
                    output += "$" + categoryList.get(i).getTotal(month, year) + blankSpaceTot + "|";
                }

                // Prints out the line that contains all the budgets for each category
                output += "\n" + TOP_BORDERLEFT + openCloseLines + "\n";
                output += BUDGET_LEFT;
                for (int i = 0; i < categoryList.size(); i++) {
                    blankSpaceBud = "";
                    String budName = Double.toString(budget.getBudgetFromCategory(categoryList.get(i).toString()));
                    int budLen = 25 - budName.length();
                    for (int j = 0; j < budLen; j++) {
                        blankSpaceBud += " ";
                    }
                    output += "$" + budName + blankSpaceBud + "|";
                }

                // Prints out the line that contains all the savings for each category
                output += "\n" + TOP_BORDERLEFT + openCloseLines + "\n";
                output += SAVINGS_LEFT;
                for (int i = 0; i < categoryList.size(); i++) {
                    blankSpaceSav = "";
                    double tot = categoryList.get(i).getTotal(month, year);
                    double bud = budget.getBudgetFromCategory(categoryList.get(i).toString());
                    double sav = bud - tot;
                    for (int j = 0; j < (25 - Double.toString(sav).length()); j++) {
                        blankSpaceSav += " ";
                    }
                    if (sav < 0) {
                        output += "$" + ANSI_RED + sav + ANSI_RESET + blankSpaceSav + "|";
                    } else {
                        output += "$" + sav + blankSpaceSav + "|";
                    }
                }
                output += "\n" + TOP_BORDERLEFT + openCloseLines + "\n";
            }
        }
        return output;
    }


    private static final String DEFAULT =
                      ".------------------.------------------------------------------------------------------------.\n"
                    + "|Month: All        |                              <Categories>                              |\n"
                    + "|Year: All         |------------------------------------------------------------------------.\n"
                    + "|Budget:           |Food                   |Transport              |Logistics               |\n"
                    + "|Savings:          |------------------------------------------------------------------------.\n"
                    + "|                  |Laska         |$5.00   |Bus           |$2.90   |Props          |$24.50  |\n"
                    + "|                  |------------------------------------------------------------------------.\n"
                    + "|                  |Pizza         |$20.00  |              |        |               |        |\n"
                    + "|                  |------------------------------------------------------------------------.\n"
                    + "|                  |KFC           |$3.50   |              |        |               |        |\n"
                    + "|                  |------------------------------------------------------------------------.\n"
                    + "|                  |              |        |              |        |               |        |\n"
                    + "|                  |------------------------------------------------------------------------.\n"
                    + "|                  |              |        |              |        |               |        |\n"
                    + "|                  |------------------------------------------------------------------------.\n"
                    + "|                  |              |        |              |        |               |        |\n"
                    + ".------------------.------------------------------------------------------------------------.\n"
                    + "|Total:            |                       |                       |                        |\n"
                    + ".------------------.------------------------------------------------------------------------.\n"
                    + "|Budget:           |                       |                       |                        |\n"
                    + ".------------------.------------------------------------------------------------------------.\n"
                    + "|Savings:          |                       |                       |                        |\n"
                    + ".------------------.------------------------------------------------------------------------.\n"
                    + "\n";         // Testing Final Output Appearance AND default table
}
