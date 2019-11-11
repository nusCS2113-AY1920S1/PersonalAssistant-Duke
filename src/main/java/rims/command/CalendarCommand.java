package rims.command;

import rims.core.ResourceList;
import rims.core.Ui;
import rims.core.Storage;

import rims.exception.RimsException;

import static rims.command.ListCommand.getListForSpecificDay;
import static rims.command.ListCommand.stringToDate;

import java.text.ParseException;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Date;

//@@author danielcyc
public class CalendarCommand extends Command {
    private static int cellLength = 15;
    private static int cellHeight = 6;

    private static int calHeight = 5;
    private static int calWidth = 7;

    private static int minHeight = 5;

    private int daysInMonth = getDaysInMonth(LocalDateTime.now());

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
    protected String operator;

    public CalendarCommand(ResourceList resources, Ui ui) {
        this.resources = resources;
        this.ui = ui;
        operator = null;
    }

    public CalendarCommand(ResourceList resources, Ui ui, String operator) {
        this.resources = resources;
        this.ui = ui;
        this.operator = operator;
    }

    public void printCal() throws ParseException, RimsException {
        getData();
        printTopCells();
        for (int row = 2; row < calHeight; row++) {
            printMidCells(row);
        }
        printBotCells();
    }

    private void printTopCells() {
        int cellRow = 1;
        for (int i = 0; i <= (cellLength * calWidth); i++) {
            if (i == 0) {
                System.out.print(topLeft);
            } else if (i == (cellLength * calWidth)) {
                System.out.print(topRight);
            } else if ((i % cellLength) == 0) {
                System.out.print(topCentre);
            } else {
                System.out.print(horz);
            }
        }
        System.out.print("\n");
        printCellRow(cellRow, daysInMonth);
    }

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

    private int getLength(String phrase) {
        return phrase.length();
    }

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
                    phraseToPrint = shortenPhrase("more...");
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

    private String shortenPhrase(String phrase) {
        String result = " ";
        String padding = "";
        int length = getLength(phrase);
        if (length <= cellLength - 2) {
            result += phrase;
            for (int i = 0; i < cellLength - length - 3; i++) {
                padding += " ";
            }
            result += padding;
        } else { // assert need to truncate phrase
            result += phrase.substring(0, (cellLength - 6));
            result += "...";
        }
        result += " ";
        return result;
    }

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

    private int getDay(int i, int cellRow) {
        int result = i / cellLength + 1 + ((cellRow - 1) * 7);
        return result;
    }

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
            array[i] = row.toArray(new String[row.size()]);
        }
        data = array;
    }

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

    public void increaseSize() throws ParseException, RimsException {
        cellHeight ++;
        cellLength += 3;
        printCal();
    }

    public void decreaseSize() throws ParseException, RimsException {
        if (!(cellHeight <= minHeight)) {
            cellHeight--;
            cellLength -= 3;
        } else {
            ui.formattedPrint("You have reached the minimum calendar size! \n" +
                    "The calender will be printed at this minimum size.");
        }
        printCal();
    }

    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) throws RimsException {
        ui.formattedPrint("ITEMS LOANED OR RESERVED FOR THIS MONTH:");
        try {
            if (operator == null) {
                printCal();
            } else if (operator.equals("+")) {
                increaseSize();
            } else if (operator.equals("-")) {
                decreaseSize();
            }
        } catch (ParseException e) {
            throw new RimsException("Invalid calendar size!");
        }
    }

}
