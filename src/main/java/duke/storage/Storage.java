package duke.storage;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import duke.exceptions.DukeException;
import duke.tasks.dinner;
import duke.tasks.lunch;
import duke.tasks.meal;
import duke.tasks.breakfast;

/**
 * Storage is a public class, a storage class encapsulates the filePath to read from and write to
 * @author Ivan Andika Lie
 */
public class Storage {
    private String line = null;
    private File file = null;
    private BufferedReader bufferedReader = null;
    private BufferedWriter bufferedWriter = null;


    /**
     * The function will act to load txt file specified by the filepath, parse it and store it in a new task ArrayList
     *     to be added in that TaskList
     * @return the ArrayList of task loaded from the file
     * @throws DukeException if either the object is unable to open file or it is unable to read the file
     */
    public ArrayList<meal> load() throws DukeException {
        ArrayList<meal> meals = new ArrayList<>();
        String sep = System.getProperty("file.separator");
        file = new File("src" + sep + "main" + sep + "java" + sep + "duke"
                            + sep + "Data" + sep + "duke.txt");
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            throw new DukeException("Unable to access file");
        }
        try {
            while((line = bufferedReader.readLine()) != null) {
                //TODO: Parse the line
                loadFile(line, meals);
        }
        bufferedReader.close();
        } catch(FileNotFoundException e) {
            throw new DukeException("Unable to open file");
        } catch (IOException e) {
            throw new DukeException("Error reading file");
        }
        return meals;
    }

    /**
     * This function acts as a line by line parser from the text file which is used to load a particular type of task
     * @param line the line input from the input file
     * @param meals the task arraylist that will store the tasks from the input file
     */
    private static void loadFile(String line, ArrayList<meal> meals) {
        String[] splitLine = line.split("\\|",4);
        String taskType = splitLine[0];
        boolean isDone = splitLine[1].equals("1");
        String description = splitLine[2];
        if (taskType.equals("B")) {
            loadBreakfast(meals, description, isDone, splitLine[3]);
        } else if (taskType.equals("L")) {
            loadBreakfast(meals, description, isDone, splitLine[3]);
        } else if (taskType.equals("D")) {
            loadBreakfast(meals, description, isDone, splitLine[3]);
        }

    }

    /**
     * This function will load a breakfast item and push it to the meal arraylist
     * @param meals the meal arraylist that will store the meals from the input file
     * @param description the meal specified
     * @param isDone whether the meal is completed
     */
    //TODO: make such that the loadFile only need to call one function only
    private static void loadBreakfast(ArrayList<meal> meals, String description, boolean isDone, String data) {
        String[] nutritionalValue = data.split("\\|");
        breakfast newBreakfast = new breakfast(description, nutritionalValue);
        if (isDone) {
            newBreakfast.markAsDone();
        }
        meals.add(newBreakfast);
    }

    /** This function will load a deadline line and push it to the task arraylist
     * @param meals the task arraylist that will store the tasks from the input file
     * @param description the task specified
     * @param data the deadline of the deadline task
     * @param isDone whether the deadline task is done
     */
    private static void loadLunch(ArrayList<meal> meals, String description, boolean isDone, String data) {
        String[] nutritionalValue = data.split("\\|");
        lunch newLunch = new lunch(description, nutritionalValue);
        if (isDone) {
            newLunch.markAsDone();
        }
        meals.add(newLunch);
    }

    /**
     * This function will load a event line and push it to the task arraylist
     * @param meals the task arraylist that will store the tasks from the input file
     * @param description the event specified
     * @param data the duration of the event
     * @param isDone
     */
    private static void loadDinner(ArrayList<meal> meals, String description, boolean isDone, String data) {
        String[] nutritionalValue = data.split("\\|");
        dinner newDinner = new dinner(description, nutritionalValue);
        if (isDone) {
            newDinner.markAsDone();
        }
        meals.add(newDinner);
    }

    /**
     * This is a function that will update the input/output file from the current arraylisto of tasks
     * @param meals the task arraylist that will store the tasks from the input file
     */
    //TODO: maybe we can put the errors in the ui file
    public void updateFile(ArrayList<meal> meals) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
        } catch (Exception e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
        try {
            for (int i = 0; i < meals.size(); i++) {
                meal currentMeal = meals.get(i);
                String currentLine = currentMeal.toString();
                if (i > 0) {
                    bufferedWriter.newLine();
                }
                String status = "0";
                if (currentMeal.getIsDone()) {
                    status = "1";
                }
                String toWrite = currentMeal.getType() + "|" + status + "|" + currentMeal.getDescription();
                HashMap<String, Integer> nutritionData = currentMeal.getNutritionalValue();
                if (nutritionData.size() != 0) {
                    toWrite += "|";
                    for (String j : nutritionData.keySet()) {
                        toWrite += j + "|" + nutritionData.get(j) + "|";
                    }
                    toWrite = toWrite.substring(0, toWrite.length() - 1);
                }
                bufferedWriter.write(toWrite);
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }
}
