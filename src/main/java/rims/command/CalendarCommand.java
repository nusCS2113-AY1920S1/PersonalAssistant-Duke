package rims.command;

import rims.core.ResourceList;
import rims.core.Storage;
import rims.core.Ui;


public abstract class CalendarCommand extends Command{


    public CalendarCommand(){
        printCal();


    }

  // @Override
    //public void execute(Ui ui, Storage storage, ResourceList resources) throws Exception {

    public static String nw =  "┌";
    public static String n = "─";
    public static String ne = "┐";
    public static String e = "│";
    public static String se = "┘";
    public static String s = "─";
    public static String sw = "└";
    public static String w = "|";
    public static String b =" ";

    public static void printCal() {
        System.out.print(nw);
        for (int i = 0; i < 10; i++) {
            System.out.print(n);
        }
        System.out.print(ne);
        System.out.print("\n");

        for (int i = 0; i < 10; i++) {
            System.out.print(e);

            for (int j = 0; j < 10; j++) {
                System.out.print(" ");
            }
            System.out.print(w);
            System.out.print("\n");
        }

        System.out.print(sw);
        for (int i = 0; i < 10; i++) {
            System.out.print(s);
        }
        System.out.print(se);
        System.out.print("\n");
    }

    }



