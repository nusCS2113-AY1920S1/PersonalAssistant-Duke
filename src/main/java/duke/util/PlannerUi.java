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
}
