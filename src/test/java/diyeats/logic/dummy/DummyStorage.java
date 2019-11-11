package diyeats.logic.dummy;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.autocorrect.Autocorrect;
import diyeats.model.meal.MealList;
import diyeats.model.user.User;
import diyeats.model.wallet.Wallet;
import diyeats.storage.Storage;

import java.util.ArrayList;

/**
 * This is a stub storage class for testing purposes.
 */
public class DummyStorage extends Storage {

    public DummyStorage() {
    }

    @Override
    public void loadMealInfo(MealList meals) throws ProgramException {
    }

    @Override
    public User loadUser() throws ProgramException {
        return null;
    }

    @Override
    public void loadWord(Autocorrect autocorrect) throws ProgramException {
    }

    @Override
    public void loadHelp(ArrayList<String> lines, String specifiedHelp) throws ProgramException {
    }

    @Override
    public void loadTransactions(Wallet wallet) throws ProgramException {
    }

    @Override
    public void writeFile(MealList meals) throws ProgramException {
    }

    @Override
    public void writeExercises(MealList meals) throws ProgramException {
    }

    @Override
    public void writeDefaults(MealList meals) throws ProgramException {
    }

    @Override
    public void writeGoal(User user) throws ProgramException {
    }

    @Override
    public void writeUser(User user) throws ProgramException {
    }

    @Override
    public void writeTransaction(Wallet wallet) throws ProgramException {
    }

    @Override
    public boolean isMealDone() {
        return true;
    }
}
