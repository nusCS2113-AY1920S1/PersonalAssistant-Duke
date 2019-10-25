package rims.command;

public abstract class CalendarCommand extends Command{
    protected static int cellLength = 15;
    protected static int cellHeight = 6;
    protected static int CalHeight = 5;
    protected static int CalWidth = 7;

    public CalendarCommand(){
        printCal();
    }
  // @Override
    //public void execute(Ui ui, Storage storage, ResourceList resources) throws Exception {
    public static String Vert = "║";
    //public static String Horz;
    public static String TopLeft = "╔";
    public static String TopRight = "╗";
    //public static String BotRight;
    public static String BotLeft = "╚";
    public static String Centre = "╬";
    public static String TopCentre = "╦";
    public static String BotCentre = "╩";
    public static String MidRight = "╣";
    public static String MidLeft = "╠";

    public static void printCal() {
        int days = 29;
        // todo: check and align days of the week / offset from first box (mon)
        // todo: add in items and reservation status
        printTopCells(days);
        for (int row = 2; row < CalHeight; row++) {
           printMidCells(row);
        }
        printBotCells(CalHeight, days);
    }

    public static void printTopCells(int days) {
        int cellRow = 1;
        for (int i = 0; i <= (cellLength * CalWidth); i++) {
            if (i == 0) {
                System.out.print(TopLeft);
            } else if (i == (cellLength * CalWidth)) {
                System.out.print(TopRight);
            } else if ((i % cellLength) == 0) {
                System.out.print(TopCentre);
            } else {
                //System.out.print(Horz)
                ;
            }
        }
        System.out.print("\n");
       // for (int row = 2; row < cellHeight; row++) {
            //for (int i = 0; i <= cellLength * CalWidth; i++) {
                printCellRow(cellRow, days);
                /*int day = ((i+1) / cellLength) + 1;
                if ((i % cellLength) == 0){
                    System.out.print(Vert);
                }
                else if (i == (cellLength * CalWidth)) {
                    System.out.print(Vert);
                }
                else if ((row == 2) && ((i+2) % cellLength == 0)){
                    System.out.print(day) ;
                }
                else{
                    System.out.print(" ");
                }
            }
            System.out.print("\n");*/
            //}
        //}
    }

    public static void printMidCells(int row){

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
                //System.out.print(Horz)
                ;
            }
        }
        System.out.print("\n");
        for (int r = 2; r < cellHeight; r++) {
            for (int i = 0; i <= cellLength * CalWidth; i++) {
                int day = ((i+1) / cellLength) + 1 + ((row-1) * 7);
                if ((i % cellLength) == 0){
                    System.out.print(Vert);
                }
                else if (i == (cellLength * CalWidth)) {
                    System.out.print(Vert);
                }
                else if ((r == 2) && ((i+2) % cellLength == 0)){
                    System.out.print(day);
                }
                else if ((r == 2) && ((i+3) % cellLength == 0) && (day > 9) ){
                    System.out.print("");
                }
                else{
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
    }

    public static void printBotCells(int row, int DaysInMonth ){
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
                //System.out.print(Horz)
                ;
            }
        }
        System.out.print("\n");
        for (int r = 2; r < cellHeight; r++) {
            for (int i = 0; i <= cellLength * CalWidth; i++) {
                int day = ((i+1) / cellLength) + 1 + ((row-1) * 7);
                if ((i % cellLength) == 0){
                    System.out.print(Vert);
                }
                else if (i == (cellLength * CalWidth)) {
                    System.out.print(Vert);
                }

                else if ((r == 2) && ((i+2) % cellLength == 0) && (day<= DaysInMonth)){
                System.out.print(day);
                 }
                else if ((r == 2) && ((i+3) % cellLength == 0) && (day > 9) && (day <= DaysInMonth) ){
                System.out.print("");
                }
                else{
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
        for (int i = 0; i <= (cellLength * CalWidth); i++) {
            if (i == 0){
                System.out.print(BotLeft);
            }
            else if (i == (cellLength * CalWidth)){
                //System.out.print(BotRight)
                ;
            }
            else if((i % cellLength) == 0){
                System.out.print(BotCentre);
            }
            else {
                //System.out.print(Horz)
                ;
            }
        }
        System.out.print("\n");
    }

    public static int getLength (String phrase){
        return phrase.length();
    }

    public static void printCellRow(int cellRow, int DaysInMonth) {
        String phrase = "testing12345678";
        for (int row = 2; row < cellHeight; row++) {
            if (row == 2) {
                printDateRow(cellRow, DaysInMonth);
            } else if (getLength(phrase) == 0) {
                for (int i = 0; i <= (cellLength * CalWidth); i++) {
                    int day = ((i + 1) / cellLength) + 1;
                    if ((i % cellLength) == 0) {
                        System.out.print(Vert);
                    } else if (i == (cellLength * CalWidth)) {
                        System.out.print(Vert);
                    } else if ((row == 2) && ((i + 2) % cellLength == 0)) {
                        System.out.print(day);
                    } else {
                        System.out.print(" ");
                    }
                }
                System.out.print("\n");
            } else { // assert there is an entry
                int length = getLength(phrase);
                String phraseToPrint = shortenPhrase(phrase);

                for (int i = 0; i <= cellLength * CalWidth; i++) {

                    int day = ((i + 1) / cellLength) + 1 + ((row - 1) * 7);

                    if ((i % cellLength) == 0) {
                        System.out.print(Vert);
                    } else if (i == (cellLength * CalWidth)) {
                        System.out.print(Vert);

                    } else {
                        System.out.print(" ");
                        System.out.print(phraseToPrint);
                        System.out.print(" " + Vert);
                        i += cellLength;
                    }
                }
            }
            System.out.print("\n");

        }
    }

    public static String shortenPhrase(String Phrase){
        String result = "";
        String padding ="";
        int length = getLength(Phrase);
        if (length <= cellLength - 2){
            result += Phrase;
            for (int i = 0; i < cellLength - length - 3; i ++){
                padding += " ";
            }
            result += padding;
        }

        else{ //assert need to truncate phrase
            result = Phrase.substring(0, (cellLength-6) );
            result += "...";
        }
        return result;
    }

    public static void printDateRow(int cellRow, int DaysInMonth){
        for (int i = 0; i <= CalWidth * cellLength; i++) {

            int day = ((i+1) / cellLength) + 1 + ((cellRow-1) * 7);

            if ((i % cellLength) == 0){
                System.out.print(Vert);
            }
            else if (i == (cellLength * CalWidth)) {
                System.out.print(Vert);
            }

            else if (((i + 2) % cellLength == 0) && (day <= DaysInMonth)) {
                System.out.print(day);
            } else if (((i + 3) % cellLength == 0) && (day > 9) && (day <= DaysInMonth)) {
                System.out.print("");
            }
            else {
                System.out.print(" ");
            }
        }
    }
}



