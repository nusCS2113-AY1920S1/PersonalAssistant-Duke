package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.exception.RimsException;
import static rims.command.ListCommand.getListForSpecificDay;
import static rims.command.ListCommand.stringToDate;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
//@@author danielcyc

/**
 * This class enables data to be displayed in a the form of a calendar.
 */

public class CalendarCommand extends Command {
    private static int cellLength = 18;
    private static int cellHeight = 6;
    private int calHeight = 5;
    private int calWidth = 7;
    private int minCellHeight = 5;
    private int scaleFactor = cellLength / cellHeight;
    private LocalDateTime date = LocalDateTime.now();
    private int month = getMonthInt(date);
    private int daysInMonth = getDaysInMonth(date);
    private String vert = "|";
    private String horz = "-";
    private String topLeft = "+";
    private String topRight = "+";
    private String botRight = "+";
    private String botLeft = "+";
    private String centre = "+";
    private String topCentre = "+";
    private String botCentre = "+";
    private String midRight = "+";
    private String midLeft = "+";
    private String[][] data;

    protected ResourceList resources;
    protected Ui ui;
    private String operator;

    /**
     * Constructor for a CalendarCommand.
     *
     * @param resources The ResourceList, containing all the created Resources thus far.
     * @param ui An instance of the user interface.
     */
    public CalendarCommand(ResourceList resources, Ui ui) {
        this.resources = resources;
        this.ui = ui;
        operator = null;
    }

    /**
     * Constructor for a CalendarCommand for changing calendar size.
     * "+" is added to the back to increase the size.
     * "-" is added to the back to decrease the size.
     *
     * @param resources The ResourceList, containing all the created Resources thus far.
     * @param ui An instance of the user interface.
     * @param operator The instruction to increase of decrease the size of calendar.
     */
    public CalendarCommand(ResourceList resources, Ui ui, String operator) {
        this.resources = resources;
        this.ui = ui;
        this.operator = operator;
    }

    /**
     * Depending on the command entered, the calendar will be printed at the current set size.
     * Calendar will increase and decrease in size proportionately depending on the symbol added to the
     * back of the command (if any).
     * Minimum size of calendar is set at 5 lines per cell.
     *
     * @param ui        An instance of the user interface.
     * @param storage   An instance of the Storage class.
     * @param resources The ResourceList, containing all the created Resources thus
     *                  far.
     * @throws RimsException  Any other unexpected error
     */
    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) throws RimsException {
        ui.formattedPrint("HERE ARE THE ITEMS LOANED OR RESERVED FOR THIS MONTH:");
        try {
            if (operator == null) {
                printCal();
            } else if (operator.equals("+")) {
                increaseSize();
                printCal();
            } else if (operator.equals("-")) {
                decreaseSize();
                printCal();
            }
        } catch (ParseException e) {
            throw new RimsException("Invalid calendar size!");
        }
    }

    /**
     * Prints to screen the calendar.
     * Function will obtain data for the current month and print calendar.
     *
     * @throws ParseException Invalid format of date
     * @throws RimsException Any other unexpected error
     */
    public void printCal() throws RimsException, ParseException {
        getData();
        printHeadings();
        printTopCells();
        for (int row = 2; row < calHeight; row++) {
            printMidCells(row);
        }
        printBotCells();
    }

    /**
     * Prints top cells of calendar.
     */

    private void printTopCells() {
        int cellRow = 1;
        for (int i = 0; i <= (cellLength * calWidth); i++) {
            if (i == 0) {
                System.out.print(midLeft);
            } else if (i == (cellLength * calWidth)) {
                System.out.print(midRight);
            } else if ((i % cellLength) == 0) {
                System.out.print(topCentre);
            } else {
                System.out.print(horz);
            }
        }
        System.out.print("\n");
        printCellRow(cellRow, daysInMonth);
    }

    /**
     * Prints middle rows of calendar.
     *
     * @param cellRow The row number in the calendar.
     */
    private void printMidCells(int cellRow) {
        for (int i = 0; i <= (cellLength * calWidth); i++) {
            if (i == 0) {
                System.out.print(midLeft);
            } else if (i == (cellLength * calWidth)) {
                System.out.print(midRight);
            } else if ((i % cellLength) == 0) {
                System.out.print(centre);
            } else {
                System.out.print(horz);
            }
        }
        System.out.print("\n");
        printCellRow(cellRow, daysInMonth);
    }

    /**
     * Prints bottom row of calendar.
     */
    private void printBotCells() {
        int cellRow = calHeight;
        for (int i = 0; i <= (cellLength * calWidth); i++) {
            if (i == 0) {
                System.out.print(midLeft);
            } else if (i == (cellLength * calWidth)) {
                System.out.print(midRight);
            } else if ((i % cellLength) == 0) {
                System.out.print(centre);
            } else {
                System.out.print(horz);
            }
        }
        System.out.print("\n");
        printCellRow(cellRow, daysInMonth);
        for (int i = 0; i <= (cellLength * calWidth); i++) {
            if (i == 0) {
                System.out.print(botLeft);
            } else if (i == (cellLength * calWidth)) {
                System.out.print(botRight);
            } else if ((i % cellLength) == 0) {
                System.out.print(botCentre);
            } else {
                System.out.print(horz);
            }
        }
        System.out.print("\n");
    }

    /**
     * Returns the length of a string.
     *
     * @param phrase The string whose length is to be determined.
     * @return An integer value of the length of the string.
     */
    private int getLength(String phrase) {
        return phrase.length();
    }

    /**
     * Prints the contents of the row of the calendar.
     *
     * @param cellRow The row number of the calendar.
     * @param daysInMonth The number of days there are in the particular month
     */
    private void printCellRow(int cellRow, int daysInMonth) {
        for (int row = 2; row < cellHeight; row++) {
            for (int i = 0; i <= cellLength * calWidth; i++) {
                int day = getDay(i, cellRow);

                String phraseToPrint;

                if ((i % cellLength) == 0) {
                    System.out.print(vert);
                } else if (day > daysInMonth) {
                    phraseToPrint = shortenPhrase("");
                    System.out.print(phraseToPrint);
                    System.out.print(vert);
                    i += cellLength;
                } else if (i == (cellLength * calWidth)) {
                    System.out.print(vert);
                } else if (row == 2) {
                    phraseToPrint = shortenPhrase(day);
                    System.out.print(phraseToPrint);
                    System.out.print(vert);
                    i += cellLength;
                } else if ((day - 1 >= data.length) || (row - 3 > data[day - 1].length - 1)) {
                    phraseToPrint = shortenPhrase("");
                    System.out.print(phraseToPrint);
                    System.out.print(vert);
                    i += cellLength;
                } else if ((row == cellHeight - 1) && (data[day - 1].length > cellHeight - 3)) {
                    int missing = getMissingTerms(day, row);
                    phraseToPrint = shortenPhrase(missing + " more...");
                    System.out.print(phraseToPrint);
                    System.out.print(vert);
                    i += cellLength;
                } else {
                    String phrase = data[day - 1][row - 3];
                    phraseToPrint = shortenPhrase(phrase);
                    System.out.print(phraseToPrint);
                    System.out.print(vert);
                    i += cellLength;
                }
            }
            System.out.print("\n");
        }
    }

    /**
     * Returns a string formatted for printing in the cells of the calendar.
     * If the phrase is too long, the string will be truncated to fit the cell.
     * Any missing information will be represented be a "..."
     *
     * @param phrase String to be formatted.
     * @return A string that is formatted to be printed in the calendar cell.
     */
    private String shortenPhrase(String phrase) {
        String result = " ";
        String padding = "";
        int length = getLength(phrase);
        if (length <= cellLength - 3) {
            result += phrase;
            for (int i = 0; i < cellLength - length - 3; i++) {
                padding += " ";
            }
            result += padding;
        } else { // phrase is too long and needs to be shortened.
            result += phrase.substring(0, (cellLength - 6));
            result += "...";
        }
        result += " ";
        return result;
    }

    /**
     * Returns a string formatted for printing in the cells of the calendar.
     * This only applies to the first row of every cell where the dates are being printed.
     * The integer value of the date is taken in and formatted to a string suitable for printing.
     *
     * @param day Integer value of the day of the specific month
     * @return A string that is formatted to be printed in the calendar cell.
     */
    private String shortenPhrase(int day) {
        String result = "";
        String padding = "";
        if (day <= 9) {
            for (int i = 0; i < cellLength - 3; i++) {
                padding += " ";
            }
            result += padding + day;
        } else { // assert that day is double digit
            for (int i = 0; i < cellLength - 4; i++) {
                padding += " ";
            }
            result += padding + day;
        }
        result += " ";
        return result;
    }

    /**
     * Returns the day in the month of the calendar based on the position.
     * Uses the horizontal and vertical positions to determine what day it is.
     *
     * @param i Horizontal position in the 2D grid.
     * @param cellRow Vertical position in the 2D grid.
     * @return Interger value of what day it is in the calendar based on the position
     */
    private int getDay(int i, int cellRow) {
        return i / cellLength + 1 + ((cellRow - 1) * 7);
    }

    /**
     * Takes in data from the resources and populates the 2D array that CalendarCommand stores.
     * This will give the function the contents of the calendar for every day.
     * Iterates through all the days in the current month to obtain all loans in the current month.
     * This data is then stored for printing.
     *
     * @throws ParseException Invalid format of date
     * @throws RimsException Any other error
     */
    private void getData() throws ParseException, RimsException {
        ArrayList<ArrayList<String>> tempData = new ArrayList<ArrayList<String>>();
        for (int day = 1; day <= daysInMonth; day++) {
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yyyy HHmm");
            String now = dtf.format(LocalDateTime.now());
            String strDate = day + "/" + now;
            Date date = stringToDate(strDate);
            tempData.add(getListForSpecificDay(date, resources, ui));
        }
        String[][] array = new String[tempData.size()][];
        for (int i = 0; i < tempData.size(); i++) {
            ArrayList<String> row = tempData.get(i);
            array[i] = row.toArray(new String[0]);
        }
        data = array;
    }

    /**
     * Takes in the integer value of the month and returns a string of the month spelt in full.
     *
     * @param month Interger value of the month.
     * @return String of the month name spelt out in full.
     */
    private static String getMonthName(int month) {
        return new DateFormatSymbols().getMonths()[month - 1];
    }

    /**
     * Returns the integer value of the month.
     *
     * @param date Date of which month needs to be returned.
     * @return Integer value of the month.
     */
    private static int getMonthInt(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String strDate = dtf.format(date);
        String[] ymd = strDate.split("/");
        int year = Integer.parseInt(ymd[2]);
        int month = Integer.parseInt(ymd[1]);
        int day = Integer.parseInt(ymd[0]);
        return month;
    }

    /**
     * Returns the integer value of the number of days in the particular month.
     *
     * @param date Date of which the number of days in that month is being queried.
     * @return Integer value of the number of days in the queried month.
     */
    private int getDaysInMonth(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String strDate = dtf.format(date);
        String[] ymd = strDate.split("/");
        int year = Integer.parseInt(ymd[2]);
        int month = Integer.parseInt(ymd[1]);
        int day = Integer.parseInt(ymd[0]);
        YearMonth yearMonthObject = YearMonth.of(year, month);
        int daysInMonth = yearMonthObject.lengthOfMonth();
        return daysInMonth;
    }

    /**
     * Returns the integer value of the year the date is in.
     *
     * @param date Date of which the year is being queried.
     * @return The integer value of the year of the queried date.
     */
    private int getYear(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String strDate = dtf.format(date);
        String[] ymd = strDate.split("/");
        int year = Integer.parseInt(ymd[2]);
        int month = Integer.parseInt(ymd[1]);
        int day = Integer.parseInt(ymd[0]);
        return year;
    }

    /**
     * Increases calendar size.
     * Scales up both cellLength and cellHeight proportionately.
     */
    public void increaseSize() {
        cellLength += scaleFactor;
        cellHeight++;
    }

    /**
     * Decreases calendar size.
     * Scales down both cellLength and cellHeight proportionately.
     * If calendar size has reached the set minimum size, there will be no change in the cellLength
     * and cellHeight.
     * A message will tell the user that the minimum size has been reached. Size cannot further decrease.
     */
    public void decreaseSize() {
        if (!(cellHeight <= minCellHeight)) {
            cellLength -= scaleFactor;
            cellHeight--;
        } else {
            System.out.println("\n"
                    + "###################\n"
                    + "You have reached the minimum calendar size!\n"
                    + "The calender will be printed at this minimum size.\n"
                    + "###################\n");
        }
    }

    /**
     * Prints headings of the calendar. The month and year will be printed out.
     */
    public void printHeadings() {
        for (int i = 0; i <= (cellLength * calWidth); i++) {
            if (i == 0) {
                System.out.print(topLeft);
            } else if (i == (cellLength * calWidth)) {
                System.out.print(topRight);
            } else {
                System.out.print(horz);
            }
        }
        System.out.print("\n");
        System.out.print(vert);
        String toPrint = "   " + getMonthName(month) + " " + getYear(date);
        System.out.print(toPrint);
        for (int i = 0; i < ((cellLength * calWidth) - toPrint.length() - 1); i++) {
            System.out.print(" ");
        }
        System.out.print(vert);
        System.out.print("\n");
    }

    /**
     * Returns the number of missing terms not displayed in the cell.
     * This is due to the limited cellHeight making it not possible to display all items.
     *
     * @param day Day of the month the cell is in.
     * @param row Row of the calendar.
     * @return Integer of the number of undisplayed terms in the cell.
     */
    public int getMissingTerms(int day, int row) {
        int result = data[day - 1].length - row + 3;
        return result;
    }
}