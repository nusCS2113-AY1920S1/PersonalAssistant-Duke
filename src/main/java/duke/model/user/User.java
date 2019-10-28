package duke.model.user;

import duke.commons.exceptions.DukeException;
import duke.model.Goal;
import duke.model.Transaction;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * This is a class that will store user information to be used for processing.
 */
public class User {
    private ArrayList<Tuple> weight = new ArrayList();
    private double originalWeight;
    private int height = 0;
    private int age;
    private Gender gender;
    private boolean isSetup;
    private String name;
    private int activityLevel;
    private double[] factor = {1.2, 1.375, 1.55, 1.725, 1.9};
    private Account account;
    private Goal goal = null;

    /**
     * This is a contructor to create an empty user profile.
     */
    public User() {
        this.isSetup = false;
    }

    /**
     * This is a contructor to create an user profile with all the info.
     * Used during loading.
     * @param name name of user
     * @param age age of user
     * @param height height of user
     * @param gender biological gender of user
     * @param activityLevel how active the user is
     * @param originalWeight their original weight
     */
    public User(String name, int age, int height, Gender gender, int activityLevel, int originalWeight,
                BigDecimal accountBalance) {
        this.name = name;
        this.height = height;
        this.age = age;
        this.gender = gender;
        this.isSetup = true;
        this.activityLevel = activityLevel;
        this.originalWeight = originalWeight;
        this.account = new Account(accountBalance);
    }

    /**
     * This is a contructor to update an empty user profile with all the info.
     * Used during startup.
     */

    public void setup() throws DukeException {
        Scanner in = new Scanner(System.in);
        String name;
        double weight = 0;
        int height = 0;
        System.out.println("     Input name");
        name = in.nextLine();
        try {
            System.out.println("     Input age");
            height = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            throw new DukeException(e.getMessage());
        }
        try {
            System.out.println("     Input weight(kg)");
            weight = Double.parseDouble(in.nextLine());
        } catch (NumberFormatException e) {
            throw new DukeException(e.getMessage());
        }
        try {
            System.out.println("     Input height(cm)");
            height = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            throw new DukeException(e.getMessage());
        }
        System.out.println("     Input gender(Male/Female)");
        String gender = in.nextLine();
        if (gender.charAt(0) == 'M') {
            this.gender = Gender.MALE;
        } else {
            this.gender = Gender.FEMALE;
        }
        int activityLevel = 5;
        while (activityLevel > 4 || activityLevel < 0) {
            System.out.println("     Input Activity Level");
            System.out.println("     1) Sedentary (Little or no exercise, desk job");
            System.out.println("     2) Lightly active (Light exercise/ sports 1-3 days/week");
            System.out.println("     3) Moderately active (Moderate exercise/ sports 6-7 days/week)");
            System.out.println("     4) Very active (Hard exercise every day, or exercising 2 xs/day) ");
            System.out.println("     5) Extra active (Hard exercise 2 or more times per day, or training for\n"
                    + "marathon, or triathlon, etc. )");
            try {
                activityLevel = Integer.parseInt(in.nextLine()) - 1;
            } catch (NumberFormatException e) {
                throw new DukeException(e.getMessage());
            }
        }
        System.out.println("      What is your initial account balance? (in SGD)");
        BigDecimal accountBalance = new BigDecimal(in.nextLine());
        this.account = new Account(accountBalance);
        this.name = name;
        setWeight(weight);
        this.height = height;
        this.activityLevel = activityLevel;
        this.isSetup = true;
    }

    /**
     * This is a function to update weight at time of input.
     * @param weight Weight at time of input
     */
    public void setWeight(int weight) {
        Calendar calendarDate = Calendar.getInstance();
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = dateFormat.format(calendarDate.getTime());
        this.weight.add(new Tuple(currentDate, weight));
    }

    /**
     * This is a function to update weight at input date.
     * @param weight Weight at time of input
     * @param date String of the date in DD/MM/YYYY format
     */
    public void setWeight(int weight, String date) throws DukeException {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date temp;
        try {
            temp = dateFormat.parse(date);
        } catch (ParseException e) {
            throw new DukeException(e.getMessage());
        }
        String currentDate = dateFormat.format(temp.getTime());
        this.weight.add(new Tuple(currentDate, weight));
    }

    public boolean setGoal(Goal goal, boolean override) {
        if (this.goal != null && override == false) {
            return false;
        } else {
            this.goal = goal;
            return true;
        }
    }

    //TODO: might want to refactor this to make it more cohesive (1 degree of separation only)
    public void depositToAccount(BigDecimal depositAmount) {
        this.account.deposit(depositAmount);
    }

    public BigDecimal getAccountBalance() {
        return this.account.getAmount();
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.account.setAmount(accountBalance);
    }

    public void updateAccountBalance(Transaction transaction) {
        BigDecimal transactionAmount = transaction.getTransactionAmount();
        if (transaction.getType().equals("PAY")) {
            this.account.withdraw(transactionAmount);
        } else if (transaction.getType().equals("DEP")) {
            this.account.deposit(transactionAmount);
        }
    }

    //TODO: might want to refactor (1 DoS)
    public String getCurrency() {
        return account.getCurrency();
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setActivityLevel(int activityLevel) {
        this.activityLevel = activityLevel;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public double getWeight() {
        return this.weight.get(this.weight.size() - 1).weight;
    }

    public double getOriginalWeight() {
        return this.originalWeight;
    }

    /**
     * This is a function to obtain all the weight at different date.
     */
    public ArrayList<Tuple> getAllWeight() {
        return this.weight;
    }

    public int getHeight() {
        return this.height;
    }

    public int getActivityLevel() {
        return this.activityLevel;
    }

    public int getDailyCalorie() {
        double calorie;
        if (this.gender == Gender.MALE) {
            calorie = 10 * getWeight() + 6.25 * getHeight() + 5 * getAge() + 5;
        } else {
            calorie = 10 * getWeight() + 6.25 * getHeight() + 5 * getAge() - 161;
        }
        return (int)( this.factor[this.activityLevel] * calorie);
    }

    public Goal getGoal() {
        return goal;
    }

    public double getCurrentToTargetWeightDiff() {
        double targetWeight = this.goal.getWeightTarget();
        double currentWeight = getWeight();
        return currentWeight - targetWeight;
    }

    public Gender getGender() {
        return this.gender;
    }

    /**
     * This is a function to check if it's an empty profile.
     */
    public boolean getIsSetup() {
        return this.isSetup;
    }
}
