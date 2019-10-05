package duke.Sports;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Loads a training plan from a txt file, or create a new plan, where we can do whatever with the plan
 */

public class MyPlan {

    private ArrayList<MyTraining> list = new ArrayList<>();

    private String name;
    private int sets;
    private int reps;
    private int activity_num;

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

    public void changeName(String name) {
        this.name = name;
    }

    public void changeSets(int sets) {
        this.sets = sets;
    }

    public void changeReps(int reps) {
        this.reps = reps;
    }

    public ArrayList<MyTraining> getList() {
        return this.list;
    }

    public void loadPlan(String plan_num) throws FileNotFoundException {
        String filepath = ".\\src\\main\\java\\duke\\Sports\\plan.txt";
        Scanner file = new Scanner(new File(filepath));
        String l1 = file.nextLine();
        while (file.hasNextLine()) {
            if (l1.equals("plan_num")) {
                String[] l2 = file.nextLine().split(" ");
                MyTraining activity = new MyTraining(l2[0],Integer.parseInt(l2[1]),Integer.parseInt(l2[2]));
                getList().add(activity);
            }
        }
        System.out.println("You have loaded plan " + plan_num + " into the list");
    }

    public void viewPlan(String intensity) {
        for (MyTraining i : getList()) {
            if (i.getIntensity().equals(intensity)) {
                System.out.println(i.toString());
            }
        }
    }

    public void clearPlan() {
        getList().clear();
    }

    public void addActivity(String newName, int newSets, int newReps, int newActivity_num) {
        this.activity_num = activity_num;
        //getList().add(name);
    }

    public void switchPos(int initial, int end) {
        MyTraining s = getList().get(initial);
        getList().add(end - 1, s);
        if (initial > end) {
            initial++;
        }
        getList().remove(initial);
    }

    enum Intensity {
        High, Moderate, Relaxed
    }

    public void createPlan(String intensity) {
        try {
            Scanner sc = new Scanner(System.in);
            String[] input = sc.nextLine().split(" ");
            ArrayList<MyTraining> temp = new ArrayList<>();
            while (!input.equals("finalize")) {
                MyTraining a = new MyTraining(input[0], Integer.parseInt(input[1]), Integer.parseInt(input[2]));
                temp.add(a);
                System.out.println("Successfully added activity: " + a.toString());
            }
        } catch (Exception e) {
            System.out.println("Please enter a valid intensity level (High,Moderate,Relaxed)");
        }
    }

    public void deletePlan(String newName, int newSets, int newReps, int newActivity_num) {
        this.activity_num = newActivity_num;
        //pass
    }
}
