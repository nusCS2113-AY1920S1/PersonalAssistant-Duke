package duke.sports;
//import duke.data.Storage;
import java.io.File;
import java.io.FileNotFoundException;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;

 /**
 * Loads a training plan from a txt file, create new plan, or edit a plan.
 */

public class MyPlan {

     /**
      * Represents the file path of the plans.
      */
    private String filePath;
    /**
     * Represents a scanner to take in the user input.
     */
    private Scanner fileInput;
     /**
      * Represents the list for the current loaded plan to be viewed or edited.
      */
    private ArrayList<MyTraining> list = new ArrayList<>();
     /**
      * Represents the list for the current number of plans saved.
      */
    private ArrayList<String> toc = new ArrayList<>();
     /**
      * Represents the map of all lists loaded from the text file.
      */
    private Map<Integer, ArrayList<MyTraining>> map = new HashMap<>();
     /**
      * Represents the name of the individual activity in a plan.
      */
    private String name;
     /**
      * Represents the number of sets of the activity in a plan.
      */
    private int sets;
     /**
      * Represents the number of repetitions of the activity in a plan.
      */
    private int reps;
     /**
      * The constructor for MyPlan.
      * @throws FileNotFoundException if file is not found.
      */
    public MyPlan() throws FileNotFoundException {
        filePath = ".\\src\\main\\java\\duke\\Sports\\plan.txt";
        File f = new File(filePath);
        fileInput = new Scanner(f);
        //ArrayList<String> toc = new Storage(filePath).loadPlans(getMap());
    }

     /**
      * A getter to retrieve the activity name in a plan.
      * @return name of activity
      */
    public String getName() {
        return this.name;
    }
     /**
      * A getter to retrieve the number of sets of an activity.
      * @return number of sets of an activity
      */
    public int getSets() {
        return this.sets;
    }

     /**
      * A getter to retrieve the number of repetitions in an activity.
      * @return number of repetitions in an activity
      */
    public int getReps() {
        return this.reps;
    }

     /**
      * A getter to retrieve the map of all plans loaded.
      * @return the map of all plans
      */
    public Map<Integer, ArrayList<MyTraining>> getMap() {
        return this.map;
    }

     /**
      * A getter to retrieve the list of current plan loaded.
      * @return the list of current plan loaded.
      */
    public ArrayList<MyTraining> getList() {
        return this.list;
    }

     /**
      * A getter to retrieve the list of the plans present in the map.
      * @return the list of present plans in the map
      */
    public ArrayList<String> getCont() {
        return this.toc;
    }

     /**
      * Creates a key for the map for the corresponding intensity & plan number.
      * @param intensity intensity level of current plan
      * @param num plan number
      * @return the key in variable type Integer.
      */
    public int createKey(final String intensity, final int num) {
        Intensity i = Intensity.valueOf(intensity);
        final int multiplier = 10;
        double key = multiplier * i.getVal() + num;
        return (int) key;
    }

     /**
      * Clear the plan currently loaded in the list.
      * @return A string to inform user of result.
      */
    public String clearPlan() {
        //getList().clear();
        return "Current training plan is cleared.";
    }

     /**
      * Add an activity to a plan in the current list.
      * @param newName name of new activity
      * @param newSets number of sets for the new activity
      * @param newReps number of reps for the new activity
      * @return A string to inform user of result
      */
    public String addActivity(final String newName, final int newSets,
                              final int newReps) {
        //getList().add(name);
        MyTraining t = new MyTraining(newName, newSets, newReps);
        //Store t in somewhere
        return "You have added this activity, " + t.toString();
    }

     /**
      * Switch activity positions for current plan in the list.
      * @param initial initial position of activity
      * @param end final position of activity
      */
    public void switchPos(final int initial, final int end) {
        MyTraining s = getList().get(initial);
        getList().add(end - 1, s);
        if (initial > end) {
            getList().remove(initial + 1);
        } else {
            getList().remove(initial);
        }
    }

     /**
      * Creates an enum for intensity values to restrict it to specified values.
      */
    public enum Intensity {
         /**
          * High intensity value.
          */
        high(1),
         /**
          * Moderate intensity value.
          */
        moderate(2),
         /**
          * Relaxed intensity value.
          */
        relaxed(3);
         /**
          * Represents the value attached to the enum strings.
          */
        private final int val;

        Intensity(final int number) {
            this.val = number;
        }

         /**
          * A getter to retrieve the number attached to each enum.
          * @return the attached number
          */
        public int getVal() {
            return val;
        }

         /**
          * Represents the set of enum values.
          */
        private static final Set<String> SET = new HashSet<String>(
                Intensity.values().length);
         /**
          * Represents the map of enum values.
          */
        private static final  Map<Integer, Intensity> MAP = new HashMap<>();

        static {
            for (Intensity i: Intensity.values()) {
                SET.add(i.name());
                MAP.put(i.val, i);
            }
        }

         /**
          * Check if a certain string is present in the enum.
          * @param value string to be checked
          * @return a boolean, true if string is present in enum and vice versa
          */
        public static boolean contains(final String value) {
            return SET.contains(value);
        }

         /**
          * Get the enum from the attached number.
          * @param value number attached to enum value
          * @return enum value(high,moderate,relaxed)
          */
        public static Intensity valueOf(final int value) {
            return MAP.get(value);
        }
    }

     /**
      * View the plan loaded into the current list.
      * @return the activities in the list.
      */
    public String viewPlan() {
        StringBuilder message = new StringBuilder();
        int x = 1;
        for (MyTraining i : getList()) {
            message.append("Activity ").append(x).append(": ").append(i.toString());
            if (x < getList().size() - 1) {
                message.append("\n");
            }
            x++;
        }
        return message.toString();
    }

     /**
      * load the plan of specified intensity and value into the list.
      * @param intensity intensity of plan to be loaded
      * @param plan plan number passed as a string
      */
    public void loadPlan(final String intensity, final String plan) {
        if (!Intensity.contains(intensity)) {
            System.out.println("Please input a proper "
                   + "intensity level: high, moderate, relaxed");
        } else {
            int planNum = Integer.parseInt(plan.split("/")[1]);
            createKey(intensity, planNum);
            System.out.println("You have loaded plan " + planNum + " of "
                    + intensity + " intensity " + " into the list");
        }
    }
/*    public void saveToMap(final ArrayList<MyTraining> newList, final int k) {
    }*/
     /**
      * Create a plan of specified intensity.
      * @param intensity intensity of plan to be created.
      */
    public void createPlan(final String intensity) {
        if (Intensity.contains(intensity)) {
            System.out.println("Creating plan of " + intensity + " intensity.");
            System.out.println("Please input activity to add in format of "
                    + "[activity] [number of sets] [number of reps].");
            while (true) {
                Scanner sc = new Scanner(System.in);
                if (sc.hasNextLine()) {
                    String input = sc.nextLine();
                    if (input.equals("finalize")) {
                        System.out.println("Plan created.");
                        System.out.println("Saving to map.");
                        //int key = createKey(intensity,....);
                        //saveToMap(getList(),key);
                        break;
                    } else if (input.equals("show")) {
                        if (getList().isEmpty()) {
                            System.out.println("No activity has been added.");
                        } else {
                            int x = 1;
                            System.out.println(viewPlan());
                            System.out.println("Continue adding activities, "
                                    + "or finalize plan.");
                        }
                    } else {
                        String[] details = input.split(" ");
                        MyTraining a = new MyTraining(details[0],
                                Integer.parseInt(details[1]),
                                Integer.parseInt(details[2]));
                        getList().add(a);
                        int temp = getList().size() - 1;
                        System.out.println("Successfully added activity: "
                                + getList().get(temp).toString());
                        System.out.println("Please input new activity,"
                                + "finalize the plan or look at current list.");
                    }
                }
            }
        } else {
            System.out.println("Please enter the correct intensity level:"
                    + " high, moderate or relaxed.");
        }
    }

     /**
      * Delete a plan from the map.
      * @param intensity intensity of plan to be deleted
      * @param planNum plan number
      * @return a string to inform user of result
      */
    public String deletePlan(final String intensity, final int planNum) {
        int key = createKey(intensity, planNum);
        if (!getMap().containsKey(key)) {
            System.out.println("Please input the correct"
                    + " intensity and plan number.");
        } else {
            getMap().remove(key);
            System.out.println("Plan successfully removed.");
        }
        return "0";
    }
}
