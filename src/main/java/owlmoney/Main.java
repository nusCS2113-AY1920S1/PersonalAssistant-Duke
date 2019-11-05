package owlmoney;

import owlmoney.logic.command.Command;
import owlmoney.logic.command.UpdateCommand;
import owlmoney.logic.parser.ParseCommand;
import owlmoney.logic.parser.exception.ParserException;
import owlmoney.logic.regex.RegexUtil;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.exception.BondException;
import owlmoney.model.card.exception.CardException;
import owlmoney.model.goals.exception.GoalsException;
import owlmoney.model.profile.Profile;
import owlmoney.model.profile.exception.ProfileException;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.storage.Storage;
import owlmoney.ui.Ui;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 * Starts an instance of OwlMoney.
 */
class Main {

    private Ui ui;
    private ParseCommand parser;
    private Profile profile;
    private Storage storage;
    private static final String FILE_PATH = "data/";
    private static final String PROFILE_FILE_NAME = "profile.csv";

    /**
     * Initializes a new OwlMoney session.
     */
    private Main() {
        ui = new Ui();
        parser = new ParseCommand();
        storage = new Storage(FILE_PATH);
    }

    /**
     * Checks if username meets requirement.
     *
     * @param name Profile user name.
     * @throws MainException If name is empty or if name contain special characters
     */
    private void checkUserName(String name) throws MainException {
        if (name.isEmpty() || name.isBlank()) {
            throw new MainException("Name cannot be empty!");
        }
        if (!RegexUtil.regexCheckName(name)) {
            throw new MainException("Name can only be alphanumeric and at most 30 characters");
        }
    }

    /**
     * Gets username when first run.
     */
    private void getUserName() {
        boolean check = true;
        while (check) {
            try {
                Scanner scanner = new Scanner(System.in);
                String username = scanner.nextLine();
                checkUserName(username);
                profile = new Profile(username, ui);
                check = false;
            } catch (MainException e) {
                ui.printError(e.toString());
            }
        }
    }

    /**
     * Starts up the initialized OwlMoney session.
     */
    private void run() {
        boolean hasExited = false;
        try {
            List<String[]> importData = storage.readFile(PROFILE_FILE_NAME);
            String userName = importData.get(0)[0];
            profile = new Profile(userName, ui);
            try {
                profile.profileUpdate(ui, true);
            } catch (BankException exceptionMessage) {
                ui.printError("Error updating outdated recurring transactions");
            }
            ui.greet(profile.profileGetUsername());
            profile.profileReminderForGoals();
        } catch (IOException exceptionMessage) {
            ui.printError("Unable to import profile files, starting fresh");
            ui.firstTimeRun();
            getUserName();
            ui.greet(profile.profileGetUsername());
            try {
                storage.createDirectoryIfNotExist(FILE_PATH);
                storage.writeProfileFile(new String[]{profile.profileGetUsername()},PROFILE_FILE_NAME);
            } catch (IOException ex) {
                ui.printError("Unable to save profile now, your data is at risk, but we will"
                        + " try saving again, feel free to continue using the program.");
            }
        }
        while (parser.hasNextLine()) {
            try {
                Command command = parser.parseLine();
                hasExited = command.execute(profile, ui);
                Command updateProfile = new UpdateCommand(false);
                updateProfile.execute(profile, ui);
                if (hasExited) {
                    break;
                }
            } catch (ParserException | BankException | TransactionException | BondException | CardException
                    | GoalsException | ProfileException exceptionMessage) {
                ui.printError(exceptionMessage.toString());
            }
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }
}

