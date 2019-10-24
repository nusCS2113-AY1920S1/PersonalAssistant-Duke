package duke.model.user;

import duke.commons.exceptions.DukeException;
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
    private int height = 0;
    private int age;
    private Gender sex;
    private boolean isSetup;
    private String name;
    private int activityLevel;
    private double[] factor = {1.2, 1.375, 1.55, 1.725, 1.9};
    private boolean loseWeight;
    private Account account;

    /**
     * This is a contructor to create an empty user profile.
     * @Author Foo Chi Hen
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
     * @param sex biological gender of user
     * @param activityLevel how active the user is
     * @param loseWeight if they would like to lose weight or maintain
     */

    public User(String name, int age, int height, Gender sex, int activityLevel, boolean loseWeight,
                BigDecimal accountBalance) {
        this.name = name;
        this.height = height;
        this.age = age;
        this.sex = sex;
        this.isSetup = true;
        this.activityLevel = activityLevel;
        this.loseWeight = loseWeight;
        this.account = new Account(accountBalance);
    }

    /**
     * This is a contructor to update an empty user profile with all the info.
     * Used during startup.
     */

    public void setup() throws DukeException {
        Scanner in = new Scanner(System.in);
        String name;
        int weight = 0;
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
            System.out.println("     Input weight");
            weight = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            throw new DukeException(e.getMessage());
        }
        try {
            System.out.println("     Input height");
            height = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            throw new DukeException(e.getMessage());
        }
        System.out.println("     Input gender(Male/Female)");
        String sex = in.nextLine();
        if (sex.charAt(0) == 'M') {
            this.sex = Gender.MALE;
        } else {
            this.sex = Gender.FEMALE;
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
        System.out.println("     Would you like to lose weight?(Y/N)");
        String choice = in.nextLine();
        if (choice.charAt(0) == 'Y') {
            this.loseWeight = true;
        } else {
            this.loseWeight = false;
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

    public void setLoseWeight() {
        this.loseWeight = true;
    }

    public void setMaintainWeight() {
        this.loseWeight = false;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public int getWeight() {
        return this.weight.get(this.weight.size() - 1).weight;
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
        if (this.sex == Gender.MALE) {
            calorie = 10 * getWeight() + 6.25 * getHeight() + 5 * getAge() + 5;
        } else {
            calorie = 10 * getWeight() + 6.25 * getHeight() + 5 * getAge() - 161;
        }
        return (int)(((this.loseWeight) ? 0.8 : 1) * this.factor[this.activityLevel] * calorie);
    }

    public boolean getLoseWeight() {
        return this.loseWeight;
    }

    public Gender getSex() {
        return this.sex;
    }

    /**
     * This is a function to check if it's an empty profile.
     */

    public boolean getIsSetup() {
        return this.isSetup;
    }
}
