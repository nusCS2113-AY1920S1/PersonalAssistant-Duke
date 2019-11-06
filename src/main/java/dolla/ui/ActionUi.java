package dolla.ui;

public class ActionUi extends Ui {

    //@@author yetong1895
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
     * This method will print the message of successfully executed an action command.
     * @param type the type of action command.
     */
    public static void printActionMessage(String type) {
        System.out.println(line);
        if (type != null && type.equals("undo")) {
            System.out.println("\tI have undone the command for you!");
        } else if (type != null && type.equals("redo")) {
            System.out.println("\tI have redone the command for you!");
        }
        System.out.println(line);
    }
}
