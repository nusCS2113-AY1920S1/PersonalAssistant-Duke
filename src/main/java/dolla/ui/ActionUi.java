package dolla.ui;

public class ActionUi extends Ui{
    public static void printEmptyStackError(String type) {
        System.out.println(line);
        if(type.equals("undo")) {
            System.out.println("\tSorry, you do not have any command to undo.");
        } else if(type.equals("redo")) {
            System.out.println("\tSorry, you do not have any command to redo.");
        }
        System.out.println(line);
    }

    public static void printEmptyRepeatError() {
        System.out.println(line);
        System.out.println("\tSorry, you do not have any command to repeat.");
        System.out.println(line);
    }

    public static String emptyMessage() {
        return "empty stack";
    }
}
