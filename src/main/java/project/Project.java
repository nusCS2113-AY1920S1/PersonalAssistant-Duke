package project;

import payment.Payee;

import java.util.HashMap;



public class Project {
    private static final double NOSPENDING = 0.0;
    private static final double NOBUDGET = 0.0;

    public HashMap<String, Payee> managermap;
    public double budget;
    public double spending;
    public String projectname;

    /**
     * Instantiates Project object without a budget allocated.
     */
    public Project(String projectname){
        this.managermap = new HashMap<>();
        this.budget = NOBUDGET;
        this.spending = NOSPENDING;
        this.projectname = projectname;
    }

    /**
     * Overload function to Instantiate Project object with budget allocated.
     * @param budget Budget allocated to project.
     */
    public Project(String projectname, double budget){
        this.managermap = new HashMap<>();
        this.budget = budget;
        this.spending = NOSPENDING;
        this.projectname = projectname;
    }
}
