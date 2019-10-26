package dolla.ui;

public class ActionUi extends Ui {

    /**
     * This method will print the error message for empty stack.
     * @param type the type of action command.
     */
    public static void printEmptyStackError(String type) {
        System.out.println(line);
        if (type != null && type.equals("undo")) {
            System.out.println("\tSorry, you do not have any command to undo.");
        } else if (type != null && type.equals("redo")) {
            System.out.println("\tSorry, you do not have any command to redo.");
        }
        System.out.println(line);
    }

    /**
     * This method will print the empty repeat message.
     */
    public static void printEmptyRepeatError() {
        System.out.println(line);
        System.out.println("\tSorry, you do not have any command to repeat.");
        System.out.println(line);
    }
}
