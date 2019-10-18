package duke.sports;
import duke.Data.Storage;

import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

import java.lang.Math;

/**
 * Loads a training plan from a txt file, or create a new plan, where we can do whatever with the plan
 */

public class MyPlan {

    private String filePath;
    private Scanner fileInput;

    private ArrayList<MyTraining> list = new ArrayList<>();
    private Map<Integer, ArrayList<MyTraining>> map = new HashMap<>();

    private String name;
    private int sets;
    private int reps;

    public MyPlan() throws FileNotFoundException {
        filePath = ".\\src\\main\\java\\duke\\Sports\\plan.txt";
        File f = new File(filePath);
        fileInput = new Scanner(f);
        //<Integer, Integer, Integer> div = new Storage(filePath).loadPlans(getMap());
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSets() {
        return this.sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }

    public int getReps() {
        return this.reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public Map<Integer, ArrayList<MyTraining>> getMap() {
        return this.map;
    }

    public ArrayList<MyTraining> getList() {
        return this.list;
    }

    public int getDivision() {
        return this.division;
    }

    public int createKey(String intensity, int num) {
        Intensity i = Intensity.valueOf(intensity);
        double key = 10*i.getValue() + num;
        return (int) key;
    }

    public String clearPlan() {
        //getList().clear();
        return "Current training plan is cleared.";
    }

    public String addActivity(String newName, int newSets, int newReps, int activity_num) {
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

    /**
     * Creates an enum for intensity values to restrict it to the specified values.
     * A set of these values is created to enable easy checking of whether a word is contained in the enum.
     *
     */
    public enum Intensity {
        high(1), moderate(2), relaxed(3);

        private final int value;

        Intensity(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        private final static Set<String> set = new HashSet<String>(Intensity.values().length);
        private final static Map<Integer, Intensity> map = new HashMap<>();

        static {
            for (Intensity i: Intensity.values()) {
                set.add(i.name());
                map.put(i.value,i);
            }
        }

        public static boolean contains(String value) {
            return set.contains(value);
        }

        public static Intensity valueOf(int value) {
            return (Intensity) map.get(value);
        }
    }

    public String viewPlan() {
        String message = "";
        int x = 1;
        for (MyTraining i : getList()) {
            message += "Activity " + x + ": " + i.toString() + "\n";
            x++;
        }
        return message;
    }

    public void loadPlan(String intensity, String plan) throws FileNotFoundException {
        if (!Intensity.contains(intensity)) {
            System.out.println("Please input a proper intensity level: high, moderate, relaxed");
        } else {
            int plan_num = Integer.parseInt(plan.split("/")[1]);
            createKey(intensity,plan_num);
            System.out.println("You have loaded plan " + plan_num + " of " + intensity + " intensity " + " into the list");
        }
    }

    public void saveToMap(ArrayList<MyTraining> list, int key) {
    }

    public void createPlan(String intensity) {
        if (Intensity.contains(intensity)) {
            System.out.println("Creating plan of " + intensity + " intensity.");
            System.out.println("Please input activity to add in format of [activity] [number of sets] [number of reps].");
            while (true) {
                Scanner sc = new Scanner(System.in);
                if (sc.hasNextLine()) {
                    String input = sc.nextLine();
                    if (input.equals("finalize")) {
                        System.out.println("Plan created.");
                        System.out.println("Saving to map.");
                        //int key = createKey(intensity,....)
                        //saveToMap(getList(),key);
                        break;
                    } else if (input.equals("show")) {
                        if (getList().isEmpty()) {
                            System.out.println("No activity has been added.");
                        } else {
                            int x = 1;
                            System.out.println(viewPlan());
                            System.out.println("Continue adding activities, or finalize the plan.");
                        }
                    } else {
                        String[] details = input.split(" ");
                        MyTraining a = new MyTraining(details[0], Integer.parseInt(details[1]), Integer.parseInt(details[2]));
                        getList().add(a);
                        System.out.println("Successfully added activity: " + getList().get(getList().size() - 1).toString());
                        System.out.println("Please input new activity, finalize the plan or look at current list.");
                    }
                }
            }
        } else {
            System.out.println("Please enter the correct intensity level: high, moderate or relaxed.");
        }
    }

    public String deletePlan(String i, int plan_num) {
        int key = createKey(i, plan_num);
        if (!getMap().containsKey(key)) {
            System.out.println("Please input the correct intensity and plan number.");
        } else {
            getMap().remove(key);
            System.out.println("Plan successfully removed.");
        }
        return "0";
    }
}