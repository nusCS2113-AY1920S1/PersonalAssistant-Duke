package duke.models;

import duke.data.Storage;
import duke.view.CliView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;
import java.util.Comparator;
import java.util.Collections;

/**
 * Loads a training plan from a txt file, create new plan, or edit a plan.
 */

public class MyPlan {

    /**
     * The ui object responsible for showing things to the user.
     */
    private CliView cliView;

    /**
     * The scanner object for user input.
     */
    private Scanner sc = new Scanner(System.in);

    /**
     * Represents the list for the current loaded plan to be viewed or edited.
     */
    private ArrayList<MyTraining> list = new ArrayList<>();
    /**
     * Represents the map of all lists loaded from the text file.
     */
    private Map<String, ArrayList<MyTraining>> map = new HashMap<>();

    /**
     * The constructor for MyPlan.
     * @param mapOfPlans map of plans
     */
    public MyPlan(final Map<String, ArrayList<MyTraining>> mapOfPlans) {
        cliView = new CliView();
        this.map = mapOfPlans;
    }

    /**
     * A getter to retrieve the list of current plan loaded.
     *
     * @return the list of current plan loaded.
     */
    public ArrayList<MyTraining> getList() {
        return this.list;
    }

    /**
     * A setter to set the private list to a specified list.
     *
     * @param newList List to be loaded as into the private list
     */
    public void setList(final ArrayList<MyTraining> newList) {
        this.list = newList;
    }

    /**
     * A getter to retrieve the map of plans.
     *
     * @return the list of current plan loaded.
     */
    public Map<String, ArrayList<MyTraining>> getMap() {
        return this.map;
    }

    /**
     * Creates a key for the map for the corresponding intensity & plan number.
     *
     * @param intensity intensity level of current plan
     * @param num       plan number
     * @return the key in variable type Integer.
     */
    public String createKey(final String intensity, final int num) {
        return intensity + num;
    }

    /**
     * Retrieves an arraylist of keys from the map.
     *
     * @return the arraylist of keys, sorted by intensity and plan number
     */
    public ArrayList<String> keyList() {
        Set<String> keys = getMap().keySet();
        ArrayList<String> kl = new ArrayList<>(keys);
        Collections.sort(kl);
        return kl;
    }

    /**
     * To show the plans available for user to view.
     */
    public void showPlanList() {
        ArrayList<String> planList = keyList();
        int index = 1;
        int option = 1;

        StringBuilder message = new StringBuilder();

        for (String s : planList) {
            String[] num = s.split("(?<=\\D)(?=\\d)");

            if (s.contains("high")) {
                if (num[1].equals("1")) {
                    index = 1;
                    message.append("High intensity:\n");
                    message.append(option).append(". Plan ").append(index);
                    message.append("\n");
                } else {
                    message.append(option).append(". Plan ").append(index);
                    message.append("\n");
                }
            } else if (s.contains("moderate")) {
                if (num[1].equals("1")) {
                    index = 1;
                    message.append("Moderate intensity:\n");
                    message.append(option).append(". Plan ").append(index);
                    message.append("\n");
                } else {
                    message.append(option).append(". Plan ").append(index);
                    message.append("\n");
                }
            } else {
                if (num[1].equals("1")) {
                    index = 1;
                    message.append("Relaxed intensity:\n");
                    message.append(option).append(". Plan ").append(index);
                    message.append("\n");
                } else {
                    message.append(option).append(". Plan ").append(index);
                    message.append("\n");
                }
            }
            index++;
            option++;
        }
        System.out.println(message);
    }

    /**
     * Clear the plan currently loaded in the list.
     */
    public void clearPlanInList() {
        getList().clear();
    }

    /**
     * Switch activity positions for current plan in the list.
     *
     * @param initial initial position of activity
     * @param end     final position of activity
     */
    public void switchPos(final int initial, final int end) {
        if (initial <= getList().size() || end <= getList().size()) {
            MyTraining s = getList().get(initial - 1);
            getList().add(end, s);
            if (initial > end) {
                getList().remove(initial);
            } else {
                getList().remove(initial - 1);
            }
            cliView.showSuccessfulSwitch(initial, end);
        } else {
            cliView.showInputCorrectPositionNumber();
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
         *
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
        private static final Map<Integer, Intensity> MAP = new HashMap<>();

        static {
            for (Intensity i : Intensity.values()) {
                SET.add(i.name());
                MAP.put(i.val, i);
            }
        }

        /**
         * Check if a certain string is present in the enum.
         *
         * @param value string to be checked
         * @return a boolean, true if string is present in enum and vice versa
         */
        private static boolean contains(final String value) {
            return SET.contains(value);
        }

        /**
         * Get the enum from the attached number.
         *
         * @param value number attached to enum value
         * @return enum value(high,moderate,relaxed)
         */
        public static Intensity valueOf(final int value) {
            return MAP.get(value);
        }
    }

    /**
     * View the plan loaded into the current list.
     *
     * @return the activities in the list.
     */
    public String viewPlan() {
        StringBuilder message = new StringBuilder();
        int x = 1;
        if (!getList().isEmpty()) {
            for (MyTraining i : getList()) {
                message.append("Activity ");
                message.append(x).append(": ").append(i.toString());
                if (x <= getList().size() - 1) {
                    message.append("\n");
                }
                x++;
            }
            return message.toString();
        } else {
            return "List is empty";
        }
    }

    /**
     * Edit a plan from the map.
     * @param intensity intensity level
     * @param key key of plan
     * @throws IOException IO
     */
    public void editPlan(final String intensity,
                         final String key) throws IOException {
        cliView.printLine();
        System.out.println(viewPlan());
        cliView.printLine();
        while (true) {
            cliView.showEditPlanPrompt1();
            if (sc.hasNextLine()) {
                String input = sc.nextLine();
                if (input.equals("switch")) {
                    cliView.printLine();
                    cliView.showEditPlanPrompt2();
                    cliView.printLine();
                    String[] pos = sc.nextLine().split(" ");
                    if (Integer.parseInt(pos[0])
                            <= getList().size()
                            && Integer.parseInt(pos[1])
                            <= getList().size()) {
                        cliView.printLine();
                        switchPos(Integer.parseInt(pos[0]),
                                Integer.parseInt(pos[1]));
                        cliView.showPlanPrompt2();
                        cliView.printLine();
                    }
                } else if (input.equals("add")) {
                    cliView.showAddActivityPrompt();
                    String[] details = sc.nextLine().split(" ");
                    MyTraining a = new MyTraining(details[0],
                            Integer.parseInt(details[1]),
                            Integer.parseInt(details[2]));
                    getList().add(a);
                    int lastAdded = getList().size() - 1;
                    cliView.printLine();
                    cliView.showActivityAdded();
                    System.out.println("     "
                            + getList().get(lastAdded).toString());
                    cliView.printLine();
                } else if (input.equals("show")) {
                    if (getList().isEmpty()) {
                        cliView.showNoActivity();
                    } else {
                        cliView.printLine();
                        cliView.showViewPlan(viewPlan());
                        cliView.printLine();
                    }
                } else if (input.equals("finalize")) {
                    saveToMap(getList(), intensity, key);
                    cliView.printLine();
                    cliView.showEditPlanSuccessful();
                    System.out.println(viewPlan());
                    cliView.printLine();
                    break;
                }
            }
        }
    }

    /**
     * load the plan of specified intensity and value into the list.
     * @param key key for a plan in the map.
     */
    public void loadPlanToList(final String key) {
        clearPlanInList();
        ArrayList<MyTraining> temp = getMap().get(key);
        setList(temp);
    }

    /**
     * Save the plan in the list to the map if it is edited or newly created.
     *
     * @param newList   List to be saved to map
     * @param intensity intensity value associated with the plan
     * @param key       key associated with the plan
     * @throws IOException IO
     */
    private void saveToMap(final ArrayList<MyTraining> newList,
                           final String intensity,
                           final String key) throws IOException {
        if (key.equals("0")) {
            int planNum = 1;
            Set<String> keys = getMap().keySet();
            for (String k : keys) {
                if (k.contains(intensity)) {
                    planNum++;
                }
            }
            String k = createKey(intensity, planNum);
            getMap().put(k, newList);
        } else {
            getMap().put(key, newList);
        }
        savePlansToFile();
        clearPlanInList();
    }

    /**
     * Create a plan of specified intensity.
     *
     * @param intensity intensity of plan to be created.
     */
    public void createPlan(final String intensity) {
        try {
            clearPlanInList();
            boolean inCreation = true;
            if (Intensity.contains(intensity)) {
                while (inCreation) {
                    if (sc.hasNextLine()) {
                        String input = sc.nextLine();
                        if (input.equals("finalize")) {
                            cliView.printLine();
                            cliView.showPlanCreated();
                            System.out.println(viewPlan());
                            cliView.printLine();
                            inCreation = false;
                        } else if (input.equals("cancel")) {
                            clearPlanInList();
                            inCreation = false;
                        } else if (input.equals("show")) {
                            if (getList().isEmpty()) {
                                cliView.showNoActivity();
                            } else {
                                cliView.printLine();
                                cliView.showViewPlan(viewPlan());
                                cliView.showPlanPrompt1();
                                cliView.printLine();
                            }
                        } else if (input.equals("switch")) {
                            if (getList().size() >= 2) {
                                try {
                                    cliView.printLine();
                                    cliView.showEditPlanPrompt2();
                                    cliView.showViewPlan(viewPlan());
                                    cliView.printLine();
                                    String[] pos = sc.nextLine().split(" ");
                                    if (Integer.parseInt(pos[0])
                                            <= getList().size()
                                            && Integer.parseInt(pos[1])
                                            <= getList().size()) {
                                        cliView.printLine();
                                        switchPos(Integer.parseInt(pos[0]),
                                                Integer.parseInt(pos[1]));
                                        cliView.showPlanPrompt2();
                                        cliView.printLine();
                                    } else {
                                        System.out.println("Input correct "
                                                + "position numbers.");
                                    }
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    System.out.println("Please input "
                                            + "the proper format.");
                                }
                            } else {
                                cliView.showNotEnoughActivitiesForSwitch();
                            }
                        } else {
                            String[] details = input.split(" ");
                            MyTraining a = new MyTraining(details[0],
                                    Integer.parseInt(details[1]),
                                    Integer.parseInt(details[2]));
                            getList().add(a);
                            int lastAdded = getList().size() - 1;
                            cliView.printLine();
                            cliView.showActivityAdded();
                            System.out.println("     "
                                    + getList().get(lastAdded).toString());
                            cliView.showPlanPrompt2();
                            cliView.printLine();
                        }
                    }
                }
                saveToMap(getList(), intensity, "0");
            } else {
                cliView.showIntensityLevel();
            }
        } catch (ArrayIndexOutOfBoundsException | IOException e) {
            System.out.println("Incorrect Format.");
        }
    }

    /**
     * Delete a plan from the map.
     * @param key key for the plan
     * @throws IOException IO
     */
    public void deletePlan(final String key) throws IOException {
        if (!getMap().containsKey(key)) {
            cliView.showIntensityAndNumber();
        } else {
            getMap().remove(key);
            cliView.showPlanRemoved();
        }
        savePlansToFile();
    }

    /**
     * Save the map of plans into the text file.
     * @throws IOException IO
     */
    public void savePlansToFile() throws IOException {
        new Storage(
                ".\\src\\main\\java\\duke\\data\\plan.txt"
                ).savePlans(getMap(), keyList());
    }
}
