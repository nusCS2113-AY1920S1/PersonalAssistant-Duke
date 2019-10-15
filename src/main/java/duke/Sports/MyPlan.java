package duke.Sports;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Loads a training plan from a txt file, or create a new plan, where we can do whatever with the plan
 */

public class MyPlan {

    private String filePath;
    private Scanner fileInput;

    private ArrayList<MyTraining> list = new ArrayList<>();

    private String name;
    private int sets;
    private int reps;
    private int activity_num;

    public MyPlan() {

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

    public MyPlan(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
        File f = new File(filePath);
        fileInput = new Scanner(f);
    }

    public String loadPlan(String planNum, String plan_num) {
        String l1 = fileInput.nextLine();
        while (fileInput.hasNextLine()) {
            if (l1.equals("plan_num")) {
                String[] l2 = fileInput.nextLine().split(" ");
                MyTraining activity = new MyTraining(l2[0],Integer.parseInt(l2[1]),Integer.parseInt(l2[2]));
                getList().add(activity);
            }
        }
        return "You have loaded plan " + plan_num + " into the list";
    }

    public String viewPlan() {
        String message = "";
        for (MyTraining i : getList()) {
            message += i.toString() + "\n";
        }
        return message;
    }

    public String clearPlan() {
        //getList().clear();
        return "All your training plans are cleared.";
    }

    public String addActivity(String newName, int newSets, int newReps, int newActivity_num) {
        this.activity_num = newActivity_num;
        //getList().add(name);
        MyTraining t = new MyTraining(newName,newSets,newReps);
        //Store t in somewhere
        return "You have added this activity, " + t.toString();
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

    public String deletePlan(String newName, int newSets, int newReps, int newActivity_num) {
        this.activity_num = newActivity_num;
        return "You have deleted the training plan: " + newName + " with " + newSets + " sets of " + newReps + "reps.";
    }
}
