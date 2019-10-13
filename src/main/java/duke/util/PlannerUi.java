package duke.util;

import java.util.Scanner;

/**
 * Mod Planner inherits functionality from Original Duke Ui.
 */
public class PlannerUi extends Ui {

    /**
     * Default constructor for Ui.
     */
    public PlannerUi() {
        super();
    }

    @Override
    public String readCommand() {
        return super.readCommand().strip();
    }

    /**
     * Helper function to print any object.
     * @param object to be printed.
     */
    public void showObject(Object object) {
        System.out.println(object);
    }

    /**
     * Start up message upon running mod planner.
     */
    @Override
    public void helloMsg() {
        showLine();
        System.out.println(
                "Welcome to ModPlanner, your one stop solution to module planning!\n"
                + "Begin typing get started!\n"
        );
        showLine();
    }

    @Override
    public void goodbyeMsg() {
        showLine();
        System.out.println(
                "Thanks for using ModPlanner!\n"
                 + "Your data will be stored in file shortly!\n"
        );
        showLine();
    }
}
