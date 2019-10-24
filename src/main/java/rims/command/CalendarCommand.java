package rims.command;

public abstract class CalendarCommand extends Command{
    private static int cellLength = 15;
    private static int cellHeight = 6;
    private static int CalHeight = 5;
    private static int CalWidth = 7;

    private static int DaysInMonth = 31;

    public CalendarCommand(){
        printCal();
    }
  // @Override
    //public void execute(Ui ui, Storage storage, ResourceList resources) throws Exception {
    private static String Vert = "║";
    private static String Horz = "═";
    private static String TopLeft = "╔";
    private static String TopRight = "╗";
    private static String BotRight = "╝";
    private static String BotLeft = "╚";
    private static String Centre = "╬";
    private static String TopCentre = "╦";
    private static String BotCentre = "╩";
    private static String MidRight = "╣";
    private static String MidLeft = "╠";

    private static String[][] array = {
            {"mon12345678910111213", "a", "h"},
            {"tue", "b" , "i"},
            {"wed", "c" , "j", "fl;kj", ";idfolhf"},
            {"thur", "k"},
            {"fri", "e", "l"},
            {"sat"},
            {"sun", "g", "n"}
    };


    // todo: check and align days of the week / offset from first box (mon)
    // todo: add in items and reservation status
    // todo: add "..." for when cell cannot display all rows

    public static void printCal() {
        printTopCells();
        for (int row = 2; row < CalHeight; row++) {
           printMidCells(row);
        }
        printBotCells();
    }

    private static void printTopCells() {
        int cellRow = 1;
        for (int i = 0; i <= (cellLength * CalWidth); i++) {
            if (i == 0) {
                System.out.print(TopLeft);
            } else if (i == (cellLength * CalWidth)) {
                System.out.print(TopRight);
            } else if ((i % cellLength) == 0) {
                System.out.print(TopCentre);
            } else {
                System.out.print(Horz);
            }
        }
        System.out.print("\n");
                  printCellRow(cellRow, DaysInMonth);
    }

    private static void printMidCells(int CellRow){

        for (int i = 0; i <= (cellLength * CalWidth); i++) {
            if (i == 0){
                System.out.print(MidLeft);
            }
            else if (i == (cellLength * CalWidth)){
                System.out.print(MidRight);
            }
            else if((i % cellLength) == 0){
                System.out.print(Centre);
            }
            else {
                System.out.print(Horz);
            }
        }
        System.out.print("\n");
        printCellRow(CellRow, DaysInMonth);
    }

    private static void printBotCells(){
        int CellRow = CalHeight;
        for (int i = 0; i <= (cellLength * CalWidth); i++) {
            if (i == 0){
                System.out.print(MidLeft);
            }
            else if (i == (cellLength * CalWidth)){
                System.out.print(MidRight);
            }
            else if((i % cellLength) == 0){
                System.out.print(Centre);
            }
            else {
                System.out.print(Horz);
            }
        }
        System.out.print("\n");

        printCellRow(CellRow, DaysInMonth);

        for (int i = 0; i <= (cellLength * CalWidth); i++) {
            if (i == 0){
                System.out.print(BotLeft);
            }
            else if (i == (cellLength * CalWidth)){
                System.out.print(BotRight);
            }
            else if((i % cellLength) == 0){
                System.out.print(BotCentre);
            }
            else {
                System.out.print(Horz);
            }
        }
        System.out.print("\n");
    }

    private static int getLength(String phrase){
        return phrase.length();
    }

    private static void printCellRow(int cellRow, int DaysInMonth) {
        for (int row = 2; row < cellHeight; row++) {
                for (int i = 0; i <= cellLength * CalWidth; i++) {
                    int day = getDay(i, cellRow);

                    String phraseToPrint;

                    if ((i % cellLength) == 0) {
                        System.out.print(Vert);
                    }
                    else if (day > DaysInMonth){
                        phraseToPrint = shortenPhrase("");
                        System.out.print(phraseToPrint);
                        System.out.print(Vert);
                        i += cellLength;
                    }
                    else if (i == (cellLength * CalWidth)) {
                        System.out.print(Vert);
                    }
                    else if (row == 2){
                        phraseToPrint = shortenPhrase(day);
                        System.out.print(phraseToPrint);
                        System.out.print(Vert);
                        i += cellLength;
                    }

                    else if ( row-3 > array[(day-1)%7].length-1) {
                        phraseToPrint = shortenPhrase("");
                        System.out.print(phraseToPrint);
                        System.out.print(Vert);
                        i += cellLength;
                    }
                    else {
                        String phrase = array[(day-1)%7][row-3];
                        phraseToPrint = shortenPhrase(phrase);
                        System.out.print(phraseToPrint);
                        System.out.print(Vert);
                        i += cellLength;
                    }
                }
            System.out.print("\n");
        }
    }

    private static String shortenPhrase(String Phrase){
        String result = " ";
        String padding = "";
        int length = getLength(Phrase);
        if (length <= cellLength - 2){
            result += Phrase;
            for (int i = 0; i < cellLength - length - 3; i ++){
                padding += " ";
            }
            result += padding;
        }
        else{ //assert need to truncate phrase
            result += Phrase.substring(0, (cellLength-6) );
            result += "...";
        }
        result += " ";
        return result;
    }

    private static String shortenPhrase(int day){
        String result = "";
        String padding = "";
        if (day <= 9){
            for(int i = 0; i < cellLength - 3; i++){
                padding += " ";
            }
            result += padding + day;

        }
        else { // assert that day is double digit
            for(int i = 0; i < cellLength - 4; i++){
                padding += " ";
            }
            result += padding + day;
        }
        result += " ";
        return result;
    }

    private static int getDay(int i, int cellRow){
        int result = i/cellLength + 1 + ((cellRow-1)*7);
        return result;
    }

    private static void getData(){
        for(int day = 1; day < DaysInMonth; day ++){
            String [] list;
            /*
            list[1];

            iterate all days
            make a list for per day
            list for that day and get the first item
            append item to list
            do the same for the rest of the items on that same day
            append list to data
            return data
        */
        }
    }
}



