package duke.Sports;

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

    public int createKey(String intensity, int num) {
        Intensity i = Intensity.valueOf(intensity);
        double key = Math.pow(10, i.getValue()) + num;
        return (int) key;
    }

    public MyPlan(String filePath) throws FileNotFoundException {
        this.filePath = filePath;
        File f = new File(filePath);
        fileInput = new Scanner(f);
    }

    public String loadPlan(String plan_num) {
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

    enum Intensity {
        high(3), moderate(2), relaxed(1);

        private final static Set<String> en = new HashSet<String>(Intensity.values().length);

        static {
            for (Intensity i: Intensity.values()) {
                en.add(i.name());
            }
        }

        public static boolean contains(String value) {
            return en.contains(value);
        }

        private int value;

        public int getValue() {
            return this.value;
        }

        Intensity(int value) {
            this.value = value;
        }
    }

    public void loadPlan(String intensity, String plan_num) throws FileNotFoundException {
        if (!Intensity.contains(intensity)) {
            System.out.println("Please input a proper intensity level: high, moderate, relaxed");
        } else {
            String[] num = plan_num.split("/");
            String filepath = ".\\src\\main\\java\\duke\\Sports\\plan.txt";
            Scanner file = new Scanner(new File(filepath));
            String line = file.nextLine();
            while (file.hasNextLine()) {
                if (line.equals("num[1]")) {
                    String[] l1 = file.nextLine().split(" ");
                    MyTraining activity = new MyTraining(l1[0], Integer.parseInt(l1[1]), Integer.parseInt(l1[2]));
                    getList().add(activity);
                }
            }
            System.out.println("You have loaded plan " + num[1] + " of " + intensity + " intensity " + " into the list");
        }
    }

    public void savePlan() {
    }

    public void createPlan(String intensity) {
        try {
            Scanner sc = new Scanner(System.in);
            String input = sc.nextLine();
            while (!input.equals("finalize")) {
                if (!input.equals("show")) {
                    viewPlan();
                }
                String[] details = input.split(" ");
                MyTraining a = new MyTraining(details[0], Integer.parseInt(details[1]), Integer.parseInt(details[2]));
                getList().add(a);
                System.out.println("Successfully added activity: " + a.toString());
            }

        } catch (Exception e) {
            System.out.println("Please enter a valid intensity level (high,moderate,relaxed)");
        }
    }

    public String deletePlan(String newName, int newSets, int newReps, int newActivity_num) {
        this.activity_num = newActivity_num;
        return "You have deleted the training plan: " + newName + " with " + newSets + " sets of " + newReps + "reps.";
    }
}
