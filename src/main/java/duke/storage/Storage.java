package duke.storage;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import duke.exceptions.DukeException;
import duke.tasks.*;
import duke.user.User;
import duke.user.Gender;
import duke.user.Tuple;
import duke.autocorrect.Autocorrect;

/**
 * Storage is a public class, a storage class encapsulates the filePath to read from disk and write to disk.
 * @author Ivan Andika Lie
 */
public class Storage {
    private String line = null;
    private File file = null;
    private BufferedReader bufferedReader = null;
    private BufferedWriter bufferedWriter = null;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private File nameFile = null;
    private File wordFile = null;
    private File defaultFile = null;
    private File goalFile = null;

    /**
     * The function will act to load txt file specified by the filepath, parse it and store it in a new task ArrayList
     * to be added in that MealList.
     * @throws DukeException if either the object is unable to open file or it is unable to read the file
     */

    public void load(MealList meals) throws DukeException {
        HashMap<String, ArrayList<Meal>> mealTracker = new HashMap<>();
        String sep = System.getProperty("file.separator");
        file = new File("src" + sep + "main" + sep + "java" + sep + "duke"
                            + sep + "Data" + sep + "duke.txt");
        defaultFile = new File("src" + sep + "main" + sep + "java" + sep + "duke"
                + sep + "Data" + sep + "defaultItems.txt");
        goalFile = new File("src" + sep + "main" + sep + "java" + sep + "duke"
                + sep + "Data" + sep + "goal.txt");
        try { //read data file
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            throw new DukeException("Unable to access file");
        }
        try {
            while ((line = bufferedReader.readLine()) != null) {
                //TODO: Parse the line
                loadFile(line, mealTracker, meals);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            throw new DukeException("Unable to open file");
        } catch (IOException e) {
            throw new DukeException("Error reading file");
        }
        try { //read default values file
            bufferedReader = new BufferedReader(new FileReader(defaultFile));
        } catch (Exception e) {
            throw new DukeException("Unable to access file");
        }
        try {
            while ((line = bufferedReader.readLine()) != null) {
                //TODO: Parse the line
                loadFile(line, mealTracker, meals);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            throw new DukeException("Unable to open file");
        } catch (IOException e) {
            throw new DukeException("Error reading file");
        }
        try { //read goal file
            bufferedReader = new BufferedReader(new FileReader(goalFile));
        } catch (Exception e) {
            throw new DukeException("Unable to access file");
        }
        try {
            while ((line = bufferedReader.readLine()) != null) {
                //TODO: Parse the line
                loadFile(line, mealTracker, meals);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            throw new DukeException("Unable to open file");
        } catch (IOException e) {
            throw new DukeException("Error reading file");
        }
        meals.setMealTracker(mealTracker);
    }

    /**
     * This function acts as a parser from the text file which is used to store data from the previous session.
     * @param line the line input from the input file
     * @param mealTracker the meal arraylist that will store the meals from the input file
     * @param meals the structure that encapsulates the meal data for this session
     */
    private void loadFile(String line, HashMap<String, ArrayList<Meal>> mealTracker, MealList meals)
            throws DukeException {
        String[] splitLine = line.split("\\|",4);
        String taskType = splitLine[0];
        boolean isDone = splitLine[1].equals("1");
        String description = splitLine[2];
        String[] nutritionalValue = splitLine[3].split("\\|");
        Meal newMeal = null;
        if (taskType.equals("B")) {
            newMeal = new Breakfast(description, nutritionalValue);
        } else if (taskType.equals("L")) {
            newMeal = new Lunch(description, nutritionalValue);
        } else if (taskType.equals("D")) {
            newMeal = new Dinner(description, nutritionalValue);
        } else if (taskType.equals("S")) {
            newMeal = new Item(description, nutritionalValue);
        }
        if (taskType.equals("S") == false) {
            if (isDone) {
                newMeal.markAsDone();
            }
            String mealDate = newMeal.getDate();
            if (!mealTracker.containsKey(mealDate)) {
                mealTracker.put(mealDate, new ArrayList<Meal>());
                mealTracker.get(mealDate).add(newMeal);
            } else {
                mealTracker.get(mealDate).add(newMeal);
            }
        } else {
            meals.addStoredItem(newMeal);
        }
        if (taskType.equals("G")) {
            meals.addGoal(new Goal(description, nutritionalValue), true);
        }
    }

    /**
     * This is a function that will update the input/output file from the current arraylist of meals.
     * @param mealData the structure that will store the tasks from the input file
     */
    //TODO: maybe we can put the errors in the ui file
    public void updateFile(MealList mealData) {
        HashMap<String, ArrayList<Meal>> meals = mealData.getMealTracker();
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(file));
        } catch (Exception e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
        try {
            for (String i : meals.keySet()) { //write process for stored food entries
                ArrayList<Meal> mealsInDay = meals.get(i);
                for (int j = 0; j < meals.get(i).size(); j++) {
                    Meal currentMeal = mealsInDay.get(j);
                    String status = "0";
                    if (currentMeal.getIsDone()) {
                        status = "1";
                    }
                    String toWrite = currentMeal.getType() + "|" + status + "|" + currentMeal.getDescription()
                            + "|date|" + currentMeal.getDate();
                    HashMap<String, Integer> nutritionData = currentMeal.getNutritionalValue();
                    if (nutritionData.size() != 0) {
                        toWrite += "|";
                        for (String k : nutritionData.keySet()) {
                            toWrite += k + "|" + nutritionData.get(k) + "|";
                        }
                        toWrite = toWrite.substring(0, toWrite.length() - 1) + "\n";
                    }
                    bufferedWriter.write(toWrite);
                }
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }

    public void updateDefaults(MealList mealData) {
        HashMap<String, HashMap<String, Integer>> storedItems = mealData.getStoredList();
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(defaultFile));
        } catch (Exception e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
        try {
            for (String i : storedItems.keySet()) { //write process for stored default food values
                String toWrite = "";
                toWrite += "S|0|" + i;
                HashMap<String, Integer> nutritionData = storedItems.get(i);
                if (nutritionData.size() != 0) {
                    toWrite += "|";
                    for (String k : nutritionData.keySet()) {
                        toWrite += k + "|" + nutritionData.get(k) + "|";
                    }
                    toWrite = toWrite.substring(0, toWrite.length() - 1) + "\n";
                }
                bufferedWriter.write(toWrite);
            }
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }

    public void updateGoal(MealList mealData) {
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(goalFile));
        } catch (Exception e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
        try {
            String toWrite = "";
            toWrite += "G|0|";
            HashMap<String, Integer> nutritionData = storedItems.get(i);
            if (nutritionData.size() != 0) {
                toWrite += "|";
                for (String k : nutritionData.keySet()) {
                    toWrite += k + "|" + nutritionData.get(k) + "|";
                }
                toWrite = toWrite.substring(0, toWrite.length() - 1) + "\n";
            }
            bufferedWriter.write(toWrite);
            bufferedWriter.close();
        } catch (IOException e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }

    /**
     * This is a function that will load user info from user.txt.
     * @Author Foo Chi Hen
     */
    public User loadUser() throws DukeException {
        User tempUser;
        String sep = System.getProperty("file.separator");
        nameFile = new File("src" + sep + "main" + sep + "java" + sep + "duke"
                + sep + "Data" + sep + "user.txt");

        if (nameFile.length() == 0) { //user.txt has nothing
            return new User();
        }

        try {
            bufferedReader = new BufferedReader(new FileReader(nameFile));
            String line =  bufferedReader.readLine();
            String[] splitLine = line.split("\\|");
            String name = splitLine[0];
            int age = Integer.parseInt(splitLine[1]);
            int height = Integer.parseInt(splitLine[2]);
            int activityLevel = Integer.parseInt(splitLine[3]);
            boolean loseWeight = Boolean.parseBoolean(splitLine[4]);
            String sex = splitLine[5];
            if (sex.equals("M")) {
                tempUser = new User(name, age, height, Gender.MALE, activityLevel, loseWeight);
            } else {
                tempUser = new User(name, age, height, Gender.FEMALE, activityLevel, loseWeight);
            }
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitWeightInfo = line.split("\\|");
                tempUser.setWeight(Integer.parseInt(splitWeightInfo[1]), splitWeightInfo[0]);
            }
            bufferedReader.close();
            return tempUser;
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    /**
     * This is a function that will store the user information into a file.
     * @param user the user class that contains all personal information to be stored.
     * @Author Foo Chi Hen
     */

    public void saveUser(User user) throws DukeException {
        String toWrite = user.getName() + "|" + user.getAge() + "|"
                + user.getHeight() + "|" + user.getActivityLevel() + "|" + user.getLoseWeight() + "|";
        if (user.getSex() == Gender.MALE) {
            toWrite += "M";
        } else {
            toWrite += "F";
        }
        ArrayList<Tuple> allWeight = user.getAllWeight();
        for (int i = 0; i < user.getAllWeight().size(); i += 1) {
            toWrite += "\n";
            String date = allWeight.get(i).date;
            int weight = allWeight.get(i).weight;
            toWrite += date + "|" + weight;
        }
        try {
            bufferedWriter = new BufferedWriter(new FileWriter(nameFile));
            bufferedWriter.write(toWrite);
            bufferedWriter.close();
        } catch (Exception e) {
            System.out.println("Error writing to file");
            e.printStackTrace();
        }
    }

    /**
     * This is a function that will load all the words to be autocorrected to.
     * @Author Foo Chi Hen
     */

    public void loadWord(Autocorrect autocorrect) throws DukeException {
        String sep = System.getProperty("file.separator");
        wordFile = new File("src" + sep + "main" + sep + "java" + sep + "duke"
                + sep + "Data" + sep + "word.txt");
        try {
            bufferedReader = new BufferedReader(new FileReader(wordFile));
            while ((line = bufferedReader.readLine()) != null) {
                autocorrect.load(line.trim());
            }
            bufferedReader.close();
        } catch (Exception e) {
            throw new DukeException(e.getMessage());
        }
    }

    public void loadHelp(ArrayList<String> lines) throws DukeException {
        String line = "";
        String sep = System.getProperty("file.separator");
        file = new File("src" + sep + "main" + sep + "java" + sep + "duke"
                            + sep + "Data" + sep + "help.txt");
        try {
            bufferedReader = new BufferedReader(new FileReader(file));
        } catch (Exception e) {
            throw new DukeException("Unable to access help file");
        }
        try {
            while ((line = bufferedReader.readLine()) != null) {
                lines.add(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            throw new DukeException("Error reading help file");
        }
    }
}
