package Task;

/**
 * Represents the todo task class
 */

public class ToDo extends item {

    /**
     * This method is the constructor used to create the todo class
     * @param info This is the information about the task being added
     * @param status This determines if whether the item added is completed or uncompleted
     */
    public ToDo(String info, Boolean status) {
        super(info, status);
        super.setType("T");
    }


    /**
     * This method is used to print out the type info and status of the item
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

}
