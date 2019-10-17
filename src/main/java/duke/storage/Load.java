package duke.storage;

import duke.autocorrect.Autocorrect;
import duke.exceptions.DukeException;
import duke.tasks.Meal;
import duke.tasks.MealList;
import duke.user.User;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Load implements StorageInterface {
    private BufferedReader bufferedReader = null;
    private BufferedWriter bufferedWriter = null;
    private String line;
    private HashMap<String, ArrayList<Meal>> mealTracker = new HashMap<>();

    /**
     * The function will act to load txt file specified by the filepath, parse it and store it in a new task ArrayList
     * to be added in that MealList.
     * @throws DukeException if either the object is unable to open file or it is unable to read the file
     */
    public void loadFile(MealList meals, File directory) throws DukeException {
        try { //read data file
            bufferedReader = new BufferedReader(new FileReader(directory));
        } catch (FileNotFoundException e) {
            try {
                bufferedWriter = new BufferedWriter(new FileWriter(directory));
            } catch (Exception f) {
                throw new DukeException("Failed to load file");
            }
        }
        try {
            while ((line = bufferedReader.readLine()) != null) {
                //TODO: Parse the line
                LoadLineParser.parse(meals, line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            throw new DukeException("Unable to open file");
        } catch (IOException e) {
            throw new DukeException("Error reading file");
        }
    }

    public User loadUser() throws DukeException {
        User tempUser;
        if (userFile.length() == 0) { //user.txt has nothing
            return new User();
        } else {
            try {
                bufferedReader = new BufferedReader(new FileReader(userFile));
                String line = bufferedReader.readLine();
                tempUser = LoadLineParser.parseUser(line);
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
    }

    public void loadAutoCorrect(Autocorrect autocorrect) throws DukeException {
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

    public void loadHelp(ArrayList<String> lines, String specifiedHelp) throws DukeException {
        File helpFile = LoadHelpUtil.load(specifiedHelp);
        try {
            bufferedReader = new BufferedReader(new FileReader(helpFile));
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
