package project;

import payment.Payee;

import java.util.HashMap;



public class Project {
    private static final double NOSPENDING = 0.0;
    private static final double NOBUDGET = 0.0;

    HashMap<String, Payee> managermap;
    double budget;
    double spending;

    /**
     * Instantiates Project object without a budget allocated.
     */
    public Project(){
        this.managermap = new HashMap<>();
        this.budget = NOBUDGET;
        this.spending = NOSPENDING;
    }

    /**
     * Overload function to Instantiate Project object with budget allocated.
     * @param budget Budget allocated to project.
     */
    public Project(double budget){
        this.managermap = new HashMap<>();
        this.budget = budget;
        this.spending = NOSPENDING;
    }
}
