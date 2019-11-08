package rims.command;
import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;
import rims.exception.RimsException;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import static rims.command.ListCommand.getListForSpecificDay;
import static rims.command.ListCommand.stringToDate;


// todo: java docs
// todo: add a resize function V
// todo: make headings - month and year V
// todo: add "X more..." for when cell cannot display all rows V
// todo: print days of the week


//@@author danielcyc
public abstract class CalendarCommand extends Command {
    private static int cellLength = 18;
    private static int cellHeight = 6;
    private static int CalHeight = 5;
    private static int CalWidth = 7;
    private static int minCellHeight = 5;
    private static int scaleFactor = cellLength/cellHeight;

    private static LocalDateTime date = LocalDateTime.now();
    private static int month = getMonthInt(date);
    private static int DaysInMonth = getDaysInMonth(date);

    private static String[][] data = {};

    private static String Vert = "|";
    private static String Horz = "-";
    private static String TopLeft = "+";
    private static String TopRight = "+";
    private static String BotRight = "+";
    private static String BotLeft = "+";
    private static String Centre = "+";
    private static String TopCentre = "+";
    private static String BotCentre = "+";
    private static String MidRight = "+";
    private static String MidLeft = "+";

/*
    private static String Vert = "║";
    private static String Horz = "=";
    private static String TopLeft = "╔";
    private static String TopRight = "╗";
    private static String BotRight = "*";
    private static String BotLeft = "╚";
    private static String Centre = "╬";
    private static String TopCentre = "╦";
    private static String BotCentre = "╩";
    private static String MidRight = "╣";
    private static String MidLeft = "╠";

  private static String[][] data =
            {{ "frisbee (2)", "rugby ball", "SR2","frisbee (2)", "SR2","frisbee (2)", "rugby ball", "SR2", "rugby ball", "SR2"  }, { "Table tennis table" },
            { "frisbee (1)", "SR2", "SR3","SR4" }, { "ball", "SR2","SR3" }, {}, {}, { "MPSH" }, { "markers (5)" }, { "pen (1)" },
            { "Basketball court" }, { "frisbee (2)", "ball", "SR2","frisbee (2)", "rugby ball", "SR2", "SR4" }, { "SR1", "SR3" }, {}, {},
            { "MPSH", "Volleyball (1)" }, { "MPSH", "Dodgeball(6)" }, { "MPSH" },
            { "Badminton Racket (4)", "Shuttlecock (10)", "SR2","frisbee (2)", "rugby ball", "SR2", "Badminton Court 1" }, {}, { "MPSH" }, { "Games Room" }, {},
            { "soccer ball (1)", "SR2","frisbee (2)", "rugby ball", "SR2", "basketball (3)", "SR1" }, {}, {}, { "MPSH", "Dodgeball(6)" }, { "MPSH" }, {},
            { "MPSH" }, {"123456789012345"} };

*/


    public CalendarCommand(ResourceList resources, Ui ui) throws ParseException, RimsException {
        getData(resources,ui);
        printCal(resources, ui);
    }
    @Override
    public void execute(Ui ui, Storage storage, ResourceList resources) {}








        public static void printCal (ResourceList resources, Ui ui) throws ParseException, RimsException {
            getData(resources, ui);

            printHeadings();
            printTopCells();
            for (int row = 2; row < CalHeight; row++) {
                printMidCells(row);
            }
            printBotCells();
        }

        private static void printTopCells () {
            int cellRow = 1;
            for (int i = 0; i <= (cellLength * CalWidth); i++) {
                if (i == 0) {
                    System.out.print(MidLeft);
                } else if (i == (cellLength * CalWidth)) {
                    System.out.print(MidRight);
                } else if ((i % cellLength) == 0) {
                    System.out.print(TopCentre);
                } else {
                    System.out.print(Horz);
                }
            }
            System.out.print("\n");
            printCellRow(cellRow, DaysInMonth);
        }

        private static void printMidCells ( int CellRow){

            for (int i = 0; i <= (cellLength * CalWidth); i++) {
                if (i == 0) {
                    System.out.print(MidLeft);
                } else if (i == (cellLength * CalWidth)) {
                    System.out.print(MidRight);
                } else if ((i % cellLength) == 0) {
                    System.out.print(Centre);
                } else {
                    System.out.print(Horz);
                }
            }
            System.out.print("\n");
            printCellRow(CellRow, DaysInMonth);
        }

        private static void printBotCells () {
            int CellRow = CalHeight;
            for (int i = 0; i <= (cellLength * CalWidth); i++) {
                if (i == 0) {
                    System.out.print(MidLeft);
                } else if (i == (cellLength * CalWidth)) {
                    System.out.print(MidRight);
                } else if ((i % cellLength) == 0) {
                    System.out.print(Centre);
                } else {
                    System.out.print(Horz);
                }
            }
            System.out.print("\n");

            printCellRow(CellRow, DaysInMonth);

            for (int i = 0; i <= (cellLength * CalWidth); i++) {
                if (i == 0) {
                    System.out.print(BotLeft);
                } else if (i == (cellLength * CalWidth)) {
                    System.out.print(BotRight);
                } else if ((i % cellLength) == 0) {
                    System.out.print(BotCentre);
                } else {
                    System.out.print(Horz);
                }
            }
            System.out.print("\n");
        }

        private static int getLength (String phrase){
            return phrase.length();
        }

        private static void printCellRow ( int cellRow, int DaysInMonth){
            for (int row = 2; row < cellHeight; row++) {
                for (int i = 0; i <= cellLength * CalWidth; i++) {
                    int day = getDay(i, cellRow);
                    String phraseToPrint;
                    if ((i % cellLength) == 0) {
                        System.out.print(Vert);
                    } else if (day > DaysInMonth) {
                        phraseToPrint = shortenPhrase("");
                        System.out.print(phraseToPrint);
                        System.out.print(Vert);
                        i += cellLength;
                    } else if (i == (cellLength * CalWidth)) {
                        System.out.print(Vert);
                    } else if (row == 2) {
                        phraseToPrint = shortenPhrase(day);
                        System.out.print(phraseToPrint);
                        System.out.print(Vert);
                        i += cellLength;
                    } else if ((day - 1 >= data.length) || (row - 3 > data[day - 1].length - 1)) {
                        phraseToPrint = shortenPhrase("");
                        System.out.print(phraseToPrint);
                        System.out.print(Vert);
                        i += cellLength;
                    } else if ((row == cellHeight - 1) && (data[day - 1].length > cellHeight - 3)) {
                        int missing = getMissingTerms(day, row);
                        phraseToPrint = shortenPhrase( missing +" more...");
                        System.out.print(phraseToPrint);
                        System.out.print(Vert);
                        i += cellLength;
                    } else {
                        String phrase = data[day - 1][row - 3];
                        phraseToPrint = shortenPhrase(phrase);
                        System.out.print(phraseToPrint);
                        System.out.print(Vert);
                        i += cellLength;
                    }
                }
                System.out.print("\n");
            }
        }

        private static String shortenPhrase (String Phrase){
            String result = " ";
            String padding = "";
            int length = getLength(Phrase);
            if (length <= cellLength - 3) {
                result += Phrase;
                for (int i = 0; i < cellLength - length - 3; i++) {
                    padding += " ";
                }
                result += padding;
            } else { // assert need to truncate phrase
                result += Phrase.substring(0, (cellLength - 6));
                result += "...";
            }
            result += " ";
            return result;
        }

        private static String shortenPhrase ( int day){
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

        private static int getDay ( int i, int cellRow){
            int result = i / cellLength + 1 + ((cellRow - 1) * 7);
            return result;
        }

        private static void getData (ResourceList resources, Ui ui) throws ParseException, RimsException {
            ArrayList<ArrayList<String>> tempData = new ArrayList<ArrayList<String>>();
            for (int day = 1; day <= DaysInMonth; day++) {

                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/yyyy HHmm");
                String now = dtf.format(LocalDateTime.now());

                String strDate = day + "/" + now;
                //System.out.print("\n date is \n" + strDate + '\n');

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

        public static String getMonthName(int month) {
            return new DateFormatSymbols().getMonths()[month-1];
        }

        private static int getMonthInt(LocalDateTime date) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String strDate = dtf.format(date);
        String[] ymd = strDate.split("/");
        int year = Integer.parseInt(ymd[2]);
        int month = Integer.parseInt(ymd[1]);
        int day = Integer.parseInt(ymd[0]);
        return month;
    }
        private static int getDaysInMonth (LocalDateTime date){
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
        private static int getYear (LocalDateTime date){
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String strDate = dtf.format(date);
            String[] ymd = strDate.split("/");
            int year = Integer.parseInt(ymd[2]);
            int month = Integer.parseInt(ymd[1]);
            int day = Integer.parseInt(ymd[0]);
            return year;
        }
        public static void increaseSize (ResourceList resources, Ui ui) throws ParseException, RimsException {

            cellLength += scaleFactor;
            cellHeight++;

            printCal(resources, ui);
        }

        public static void decreaseSize (ResourceList resources, Ui ui) throws ParseException, RimsException {
            if (!(cellHeight <= minCellHeight)) {
                cellLength -= scaleFactor;
                cellHeight--;

            } else {
                System.out.println("###################\n"+
                        "You have reached the minimum calendar size!\n" +
                        "The calender will be printed at this minimum size.\n"+
                        "###################\n");
            }
            printCal(resources, ui);
        }
        public static void printHeadings(){
            for (int i = 0; i <= (cellLength * CalWidth); i++) {
                if (i == 0) {
                    System.out.print(TopLeft);
                } else if (i == (cellLength * CalWidth)) {
                    System.out.print(TopRight);
                } else {
                    System.out.print(Horz);
                }
            }
            System.out.print("\n");

            System.out.print(Vert);
            String toPrint = "   " + getMonthName(month) + " " + getYear(date);
            System.out.print(toPrint);
            for(int i = 0; i < ((cellLength * CalWidth) - toPrint.length() - 1); i++ ){
                System.out.print(" ");
            }
            System.out.print(Vert);
            System.out.print("\n");

    }

    public static int getMissingTerms(int day, int row){
        int result = data[day - 1].length - row + 3;
        return result;
    }
        }

