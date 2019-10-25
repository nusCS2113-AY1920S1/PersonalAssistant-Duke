package owlmoney;

import java.util.Scanner;

import owlmoney.logic.command.Command;
import owlmoney.logic.parser.ParseCommand;
import owlmoney.logic.regex.RegexUtil;
import owlmoney.model.card.exception.CardException;
import owlmoney.logic.parser.exception.ParserException;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.exception.BondException;
import owlmoney.model.goals.exception.GoalsException;
import owlmoney.model.profile.Profile;
import owlmoney.model.profile.exception.ProfileException;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * The main class.
 */
class Main {

    private Ui ui;
    private ParseCommand parser;
    private Profile profile;
    //private Storage storage;

    /**
     * Initializes a new OwlMoney session.
     */
    private Main() {
        ui = new Ui();
        parser = new ParseCommand();
        /*storage = new Storage("data/data.txt");
        try {
            tasks = new TaskList(storage.readFile());
        } catch (FileNotFoundException e) {
            ui.printError("Could not read tasks from disk, will start with empty file");
            tasks = new TaskList();
        }*/
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
            throw new MainException("Name can only contain letters and at most 50 characters");
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
                profile = new Profile(username);
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

        //Temporary do this chunk
        ui.firstTimeRun();
        getUserName();
        ui.greet(profile.profileGetUsername());
        // until above this line
        while (parser.hasNextLine()) {
            try {
                Command command = parser.parseLine();
                hasExited = command.execute(profile, ui);
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

