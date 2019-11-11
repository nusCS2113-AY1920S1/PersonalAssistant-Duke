package diyeats.logic.dummy;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.autocorrect.Autocorrect;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.util.ArrayList;

public class DummyStorage extends Storage {

    /**
     * Constructor for Storage.
     */
    public DummyStorage() {
    }

    /**
     * Load all info required to initialize a MealList object from input/output file.
     * @param meals the MealList object ot be initialized
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    @Override
    public void loadMealInfo(MealList meals) throws ProgramException {
    }

    /**
     * Load user info from the input/output file.
     * @return an instance of User
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    @Override
    public User loadUser() throws ProgramException {
        return null;
    }

    /**
     * Load all the words to be autocorrected to from the input/output file.
     * @param autocorrect autocorrect object to be loaded with correctable words
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    @Override
    public void loadWord(Autocorrect autocorrect) throws ProgramException {
    }

    /**
     * Load relevant help file from input/output file.
     * @param lines container used to store content from help file
     * @param specifiedHelp type of help requested
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    @Override
    public void loadHelp(ArrayList<String> lines, String specifiedHelp) throws ProgramException {
    }

    /**
     * Load all transactions to wallet from input/output file.
     * @param wallet container to store transactions
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    @Override
    public void loadTransactions(Wallet wallet) throws ProgramException {
    }

    /**
     * Write meal records to the input/output file.
     * @param meals container storing meal records
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    @Override
    public void writeFile(MealList meals) throws ProgramException {
    }

    /**
     * Write exercise records to the input/output file.
     * @param meals container storing exercise records
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    @Override
    public void writeExercises(MealList meals) throws ProgramException {
    }

    /**
     * Write default meal values to the input/output file.
     * @param meals container storing default meal values
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    @Override
    public void writeDefaults(MealList meals) throws ProgramException {
    }

    /**
     * Write goal to the input/output file.
     * @param user container storing goal
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    @Override
    public void writeGoal(User user) throws ProgramException {
    }

    /**
     * Write user information to input/output file.
     * @param user container storing user information
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    @Override
    public void writeUser(User user) throws ProgramException {
    }

    /**
     * Write transactions to the input/output file.
     * @param wallet container storing transactions
     * @throws ProgramException if FileUtil is unable to open file or it is unable to read the file
     */
    @Override
    public void writeTransaction(Wallet wallet) throws ProgramException {
    }

    /**
     * Getter for isMealDone.
     * @return true if meallist object is completely loaded, false otherwise
     */
    @Override
    public boolean isMealDone() {
        return true;
    }
}
