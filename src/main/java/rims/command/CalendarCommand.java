package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;

import static rims.command.CalendarCommand.printTopCells;


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
    public static String Horz = "═";
    public static String TopLeft = "╔";
    public static String TopRight = "╗";
    public static String BotRight = "╝";
    public static String BotLeft = "╚";
    public static String Centre = "╬";
    public static String TopCentre = "╦";
    public static String BotCentre = "╩";
    public static String MidRight = "╣";
    public static String MidLeft = "╠";



    public static void printCal() {
        int month = 31;
        printTopCells();
        for (int row = 2; row < CalHeight; row++) {
           printMidCells(row);
        }
        printBotCells(CalHeight, month);
    }

    public static void printTopCells() {

        for (int i = 0; i <= (cellLength * CalWidth); i++) {
            if (i == 0){
                System.out.print(TopLeft);
            }
            else if (i == (cellLength * CalWidth)){
                System.out.print(TopRight);
            }
            else if((i % cellLength) == 0){
                System.out.print(TopCentre);
            }
            else {
                    System.out.print(Horz);
            }
        }
        System.out.print("\n");
        for (int row = 2; row < cellHeight; row++) {
            for (int i = 0; i <=cellLength * CalWidth; i++) {
                if ((i % cellLength) == 0){
                    System.out.print(Vert);
                }
                else if (i == (cellLength * CalWidth)) {
                    System.out.print(Vert);
                }
                else if ((row == 2) && ((i+2) % cellLength == 0)){
                    System.out.print( ((i+1) / cellLength) + 1);
                }
                else{
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
    }

    public static void printMidCells( int row){
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

        for (int r = 2; r < cellHeight; r++) {
            for (int i = 0; i <= cellLength * CalWidth; i++) {
                if ((i % cellLength) == 0){
                    System.out.print(Vert);
                }
                else if (i == (cellLength * CalWidth)) {
                    System.out.print(Vert);
                }
                else if ((r == 2) && ((i+2) % cellLength == 0)){
                    System.out.print( ((i+1) / cellLength) + 1 + ((row-1) * 7));
                }
                else if ((r == 2) && ((i+3) % cellLength == 0) && ((((i+1) / cellLength) + 1 + ((row-1) * 7)) > 9) ){
                    System.out.print("");
                }
                else{
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
    }

    public static void printBotCells(int row, int month ){
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

        for (int r = 2; r < cellHeight; r++) {
            for (int i = 0; i <= cellLength * CalWidth; i++) {
                if ((i % cellLength) == 0){
                    System.out.print(Vert);
                }
                else if (i == (cellLength * CalWidth)) {
                    System.out.print(Vert);
                }

                else if ((r == 2) && ((i+2) % cellLength == 0) && (((i+1) / cellLength) + 1 + ((row-1) * 7) <= month)){
                System.out.print( ((i+1) / cellLength) + 1 + ((row-1) * 7));
                 }
                else if ((r == 2) && ((i+3) % cellLength == 0) && ((((i+1) / cellLength) + 1 + ((row-1) * 7)) > 9) && ((i+1) / cellLength) + 1 + ((row-1) * 7) <= month ){
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
}



