package duke.storage;

import duke.commons.exceptions.DukeException;
import duke.model.Goal;
import duke.model.user.Gender;
import duke.model.user.User;
import duke.model.wallet.Deposit;
import duke.model.wallet.Payment;
import duke.model.wallet.Transaction;
import duke.model.wallet.Wallet;

public class LoadLineParser {

    //TODO: Fix SLAP issues parsing and other stuff
    public static void parseTransactions(String line, Wallet wallet) {
        String[] splitLine = line.split("\\|", 3);
        String transactionType = splitLine[0];
        String transactionAmount = splitLine[1];
        String transactionDate = splitLine[2];
        Transaction newTransaction;
        if (transactionType.equals("PAY")) {
            newTransaction = new Payment(transactionAmount, transactionDate);
            LoadTransactionUtil.load(wallet.getTransactions(), newTransaction);
        } else if (transactionType.equals("DEP")) {
            newTransaction = new Deposit(transactionAmount, transactionDate);
            LoadTransactionUtil.load(wallet.getTransactions(), newTransaction);
        }
    }

    public static void parseGoal(User user, String line) throws DukeException {
        try {
            if (line != null) {
                String[] splitLine = line.split("\\|");
                Goal goal = new Goal(splitLine);
                user.setGoal(goal, true);
            }
        } catch (Exception e) {
            throw new DukeException("It appears the goal file has been corrupted. No goal shall be set for"
                    + " this session");
        }
    }

    public static User parseUser(String line) {
        String[] splitLine = line.split("\\|");
        String name = splitLine[0];
        int age = Integer.parseInt(splitLine[1]);
        int height = Integer.parseInt(splitLine[2]);
        int activityLevel = Integer.parseInt(splitLine[3]);
        int originalWeight = Integer.parseInt(splitLine[4]);
        String sex = splitLine[5];
        String lastDate = splitLine[6];
        Gender gender;
        if (sex.equals("M")) {
            gender = Gender.MALE;
        } else {
            gender = Gender.FEMALE;
        }
        return new User(name, age, height, gender, activityLevel, originalWeight, lastDate);
    }
}
