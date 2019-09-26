package duke.Sports;

import duke.Task.Item;

import java.util.ArrayList;

/**
 * Create a class for all the sports classes.
 */
public class MyClass extends Item {

    /**
     * The day of the week it will be held. (i.e. Monday, Tuesday)
     */
    private String day;

    /**
     * A list of students attending this sports class.
     */
    private ArrayList<MyStudents> students = new ArrayList<>();

    /**
     * Constructor method for MyClass.
     * @param info   This parameter is the name of the sports class.
     * @param status This shows whether the sports class has been held this week or not.
     * @param day  When it will be held. (i.e. Monday, Tuesday)
     */
    public MyClass(final String info,
                    final Boolean status,
                    final String day) {
        super(info, status);
        super.setType("C");     //Has its own item type "C"
        this.day = day;
    }

}
