package duke.Sports;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

/**
 * Loads a training plan from a txt file, or create a new plan, where we can do whatever with the plan
 */

public class MyPlan {
    private String filepath = ".\\src\\main\\java\\duke\\Sports\\plan.txt";
    private ArrayList<String> plan = new ArrayList<>();

    private String name;
    private int sets;
    private int reps;
    private int activity_num;

    /**
     * dummy load until all features are finalised.
     * only plan 1 is custom made for now until all commands are finalised.
     */
    private int plan_num = 0;

    public MyPlan() throws FileNotFoundException {
        Scanner file = new Scanner(new File(filepath));
        String num = file.nextLine();
        while (file.hasNextLine()) {
            if (num.equals("Plan 1:")) {
                plan.add(file.nextLine());
            }
        }
        //System.out.println("You have loaded plan " + plan_num + " into the list);
    }

    public String getName() {
        return this.name;
    }

    public int getSets() {
        return this.sets;
    }

    public int getReps() {
        return this.reps;
    }

    public int getActivityNum() {
        return this.activity_num;
    }

    public void viewPlan() {
        for (String i : plan) {
            System.out.println(plan);
        }
    }

    public void clearPlan() {
        plan.clear();
    }

    public void addActivity(String newName, int newSets, int newReps, int newActivity_num) {
        this.name = newName;
        this.sets = newSets;
        this.reps = newReps;
        this.activity_num = newActivity_num;
        //plan.add(name);
    }

    public void switchPos(int initial, int end) {
        String s = plan.get(initial);
        plan.add(end - 1, s);
        if (initial > end) {
            initial++;
        }
        plan.remove(initial);
    }

    enum Intensity {
        High, Moderate, Relax
    }

    public void createPlan() {
        try {
            //Scanner sc = new Scanner(System.in);
            String sc = "";
            int index = 0;
            while (!sc.equals("finish plan")) {
                Random random = new Random();
                Intensity randomI = Intensity.values()[random.nextInt(Intensity.values().length)];
                System.out.println("Creating plan of " + randomI + " intensity");
                if (index == 2) {
                    break;
                }
                index++;
            }
        } catch (Exception e) {
            System.out.println("Please enter a valid intensity level (High,Moderate,Relax)");
        }
    }
}
