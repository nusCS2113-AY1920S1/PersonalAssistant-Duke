package diyeats.storage;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import diyeats.commons.exceptions.ProgramException;
import diyeats.commons.file.LocalDateAdapter;
import diyeats.logic.autocorrect.Autocorrect;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;

import java.time.LocalDate;
import java.util.ArrayList;

//@@author Fractalisk

/**
 * Storage is a public class that handles all file I/O during application runtime.
 */
public class Storage {
    private Load loader;
    private Write writer;
    private int stage = 0;
    private boolean isMealDone = false;

    /**
     * Constructor for Storage.
     */
    public Storage() {
        //sets gson to generate json with indentation, and use custom typeAdapter for Localdate
        Gson gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        loader = new Load(gson);
        writer = new Write(gson);
    }

    /**
     * Load all info required to initialize a MealList object from input/output file.
     * @param meals the MealList object ot be initialized
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void loadMealInfo(MealList meals) throws ProgramException {
        switch (stage) {
            case 0:
                stage++;
                isMealDone = false;
                loader.loadMealListData(meals);
                break;
            case 1:
                stage++;
                loader.loadDefaultMealData(meals);
                break;
            case 2:
                isMealDone = true;
                loader.loadExercises(meals);
                break;
            default:
                isMealDone = true;
                throw new ProgramException("The storage function has entered an invalid state");
        }
    }

    /**
     * Load user info from the input/output file.
     * @return an instance of User
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public User loadUser() throws ProgramException {
        User user = loader.loadUser();
        loader.loadGoals(user);
        return user;
    }

    /**
     * Load all the words to be autocorrected to from the input/output file.
     * @param autocorrect autocorrect object to be loaded with correctable words
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void loadWord(Autocorrect autocorrect) throws ProgramException {
        loader.loadAutoCorrect(autocorrect);
    }

    /**
     * Load relevant help file from input/output file.
     * @param lines container used to store content from help file
     * @param specifiedHelp type of help requested
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void loadHelp(ArrayList<String> lines, String specifiedHelp) throws ProgramException {
        loader.loadHelp(lines, specifiedHelp);
    }

    /**
     * Load all transactions to wallet from input/output file.
     * @param wallet container to store transactions
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void loadTransactions(Wallet wallet) throws ProgramException {
        loader.loadTransactions(wallet);
    }

    /**
     * Write meal records to the input/output file.
     * @param meals container storing meal records
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void writeFile(MealList meals) throws ProgramException {
        writer.writeFile(meals);
    }

    /**
     * Write exercise records to the input/output file.
     * @param meals container storing exercise records
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void writeExercises(MealList meals) throws ProgramException {
        writer.writeExercises(meals);
    }

    /**
     * Write default meal values to the input/output file.
     * @param meals container storing default meal values
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void writeDefaults(MealList meals) throws ProgramException {
        writer.writeDefaults(meals);
    }

    /**
     * Write goal to the input/output file.
     * @param user container storing goal
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void writeGoal(User user) throws ProgramException {
        writer.writeGoal(user);
    }

    /**
     * Write user information to input/output file.
     * @param user container storing user information
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void writeUser(User user) throws ProgramException {
        writer.writeUser(user);
    }

    /**
     * Write transactions to the input/output file.
     * @param wallet container storing transactions
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    public void writeTransaction(Wallet wallet) throws ProgramException {
        writer.writeTransaction(wallet);
    }

    /**
     * Getter for isMealDone.
     * @return true if meallist object is completely loaded, false otherwise
     */
    public boolean isMealDone() {
        return this.isMealDone;
    }
}
