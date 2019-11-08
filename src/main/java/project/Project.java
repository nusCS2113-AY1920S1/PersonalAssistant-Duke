package project;

import payment.Payee;

import java.util.HashMap;
import java.util.LinkedHashMap;


public class Project {
    private static final double NOSPENDING = 0.0;
    private static final double NOREMAINING = 0.0;
    private static final double NOBUDGET = 0.0;

    public HashMap<String, Payee> managermap;
    public double budget;
    public double spending;
    public double remaining;
    public String projectname;

    /**
     * Instantiates Project object without a budget allocated.
     */
    public Project(String projectname) {
        this.managermap = new LinkedHashMap<>();
        this.budget = NOBUDGET;
        this.spending = NOSPENDING;
        this.remaining = NOREMAINING;
        this.projectname = projectname;
    }

    /**
     * Add the budget assigned to the project.
     * @param amount a double value indicates the assigned amount of budget
     */
    public void addBudget(Double amount) {
        this.budget += amount;
        this.remaining += amount;
    }

    /**
     * This function deccribes how the program works when a payment is made to a project
     * @param amount the amount of money for this payment
     */
    public void addPayment(Double amount) {
        this.spending += amount;
        this.remaining -= amount;
    }

    /**
     * @return total budget assigned to the project
     */
    public double getBudget() {
        return this.budget;
    }

    /**
     * @return budget spent.
     */
    public double getSpending() {
        return this.spending;
    }

    /**
     * @return budget left.
     */
    public double getRemaining() {
        return this.remaining;
    }

    /**
     * Returns a string contained Project name, budget and spending to print as message.
     */
    public String giveProject() {
        return "\t" + "Project name:  " + this.projectname + "\n" + "\t" 
            + "Allocated Budget:  " + this.budget + "\n" + "\t" 
            + "Total Spending: " + this.spending;
    }

    /**
     * Overload function to Instantiate Project object with budget allocated.
     * @param budget Budget allocated to project.
     */
    public Project(String projectname, double budget) {
        this.managermap = new HashMap<>();
        this.budget = budget;
        this.spending = NOSPENDING;
        this.remaining = budget;
        this.projectname = projectname;
    }
}
