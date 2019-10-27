package duke.storage;

import duke.commons.exceptions.DukeException;
import duke.logic.autocorrect.Autocorrect;
import duke.model.MealList;
import duke.model.TransactionList;
import duke.model.user.User;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import static duke.commons.FilePaths.AUTOCORRECT_FILE;
import static duke.commons.FilePaths.GOAL_FILE;
import static duke.commons.FilePaths.TRANSACTION_FILE;
import static duke.commons.FilePaths.USER_FILE;

/**
 * This object is in charge of all reading from save operations.
 */
public class Load {
    private BufferedReader bufferedReader = null;
    private String line;

    /**
     * The function will act to load txt file specified by the filepath, parse it and store it in a new task ArrayList
     * to be added in that MealList.
     * @throws DukeException if either the object is unable to open file or it is unable to read the file
     */
    public void loadMeals(MealList meals, File directory) throws DukeException {
        validateFile(directory);
        try {
            while ((line = bufferedReader.readLine()) != null) {
                LoadLineParser.parse(meals, line);
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            throw new DukeException("Unable to open file");
        } catch (IOException e) {
            throw new DukeException("Error reading file");
        }
    }

    public void loadGoals(MealList meals) throws DukeException {
        validateFile(GOAL_FILE);
        try {
            line = bufferedReader.readLine();
            LoadLineParser.parseGoal(meals, line);
        } catch (IOException e) {
            throw new DukeException("Error reading file");
        }
    }

    public void loadTransactions(TransactionList transactions, User user) throws DukeException {
        validateFile(TRANSACTION_FILE);
        try {
            while ((line = bufferedReader.readLine()) != null) {
                LoadLineParser.parseTransactions(transactions, line, user);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User loadUser() throws DukeException {
        User tempUser;
        if (USER_FILE.length() == 0) { //user.txt has nothing
            return new User();
        } else {
            try {
                bufferedReader = new BufferedReader(new FileReader(USER_FILE));
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
            bufferedReader = new BufferedReader(new FileReader(AUTOCORRECT_FILE));
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

    private void validateFile(File directory) throws DukeException {
        try {
            bufferedReader = new BufferedReader(new FileReader(directory));
        } catch (FileNotFoundException e) {
            try {
                BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(directory));
                bufferedReader = new BufferedReader(new FileReader(directory));
            } catch (Exception f) {
                throw new DukeException("Failed to load file");
            }
        }
    }
}
