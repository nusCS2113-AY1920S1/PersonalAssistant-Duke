package duke;

import duke.command.Command;
import duke.command.FilterCommand;
import duke.command.ListPriorityCommand;
import duke.command.SetPriorityCommand;
import duke.command.AddCommand;
import duke.command.DeleteCommand;
import duke.command.FindTasksByPriorityCommand;
import duke.enums.ErrorMessages;
import duke.parser.Parser;
import duke.storage.BudgetStorage;
import duke.storage.ContactStorage;
import duke.storage.PriorityStorage;
import duke.storage.Storage;
import duke.task.BudgetList;
import duke.task.PriorityList;
import duke.task.FilterList;
import duke.task.ContactList;
import duke.task.TaskList;
import duke.ui.Ui;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Represents a duke that controls the program.
 */
public class Duke {
    private Storage storage;
    private TaskList items;
    private FilterList filterList;
    private Ui ui;
    private ContactStorage contactStorage;
    private ContactList contactList;
    private DukeLogger dukeLogger;

    private PriorityStorage priorityStorage;
    private PriorityList priorityList;

    private BudgetStorage budgetStorage;
    private BudgetList budgetList;

    private static final String storageFilePath = "data";
    private static final String taskFilePath = "data/duke.txt";
    private static final String priorityFilePath = "data/priority.txt";
    private static final String budgetFilePath = "data/budget.txt";
    private static final String contactsFilePath = "data/contacts.txt";
    private static final String sampleBudgetFilePath = "sample/budget.txt";
    private static final String sampleContactsFilePath = "sample/contacts.txt";
    private static final String samplePriorityFilePath = "sample/priority.txt";
    private static final String sampleTaskFilePath = "sample/duke.txt";
    private static final Logger logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    private boolean sampleTaskFileUsed = false;
    private boolean samplePriorityFileUsed = false;
    private boolean sampleContactsFileUsed = false;
    private boolean sampleBudgetFileUsed = false;
    private String creditsDesc;

    /**
     * Creates a duke to initialize storage, task list, and ui.
     *
     * @throws IOException  If there is an error writing the text file.
     */
    public Duke() throws IOException {
        initialize();
        dukeLogger.setupLogger();
        try {
            readStorage();
        } catch (IOException e) {
            ui.showLoadingError();
            logger.log(Level.SEVERE,"Storage text file is not found");
            createEmptyTaskList();
            storage.writeSample(sampleTaskFilePath);
            sampleTaskFileUsed = true;
            readStorage();
        }
        try {
            readPriorityStorage();
        } catch (IOException e) {
            ui.showLoadingError();
            logger.log(Level.SEVERE,"Priority storage text file is not found");
            createEmptyPriorityList();
            priorityStorage.writeSample(samplePriorityFilePath);
            samplePriorityFileUsed = true;
            readPriorityStorage();
        }
        try {
            readContactStorage();
        } catch (IOException e) {
            ui.showLoadingError();
            logger.log(Level.SEVERE,"Contact list text file is not found");
            createEmptyContactList();
            contactStorage.writeSample(sampleContactsFilePath);
            sampleContactsFileUsed = true;
            readContactStorage();
        }
        try {
            readBudgetStorage();
        } catch (IOException e) {
            ui.showLoadingError();
            logger.log(Level.SEVERE,"Budget list text file is not found");
            createEmptyBudgetList();
            budgetStorage.writeSample(sampleBudgetFilePath);
            sampleBudgetFileUsed = true;
            readBudgetStorage();
        }
        try {
            readCredits();
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Credits text file is not found");
        }
    }

    //@@author talesrune
    private void initialize() {
        checkStorageExist();
        dukeLogger = new DukeLogger();
        ui = new Ui();
        filterList = new FilterList();
        storage = new Storage(taskFilePath);
        priorityStorage = new PriorityStorage(priorityFilePath);
        contactStorage = new ContactStorage(contactsFilePath);
        budgetStorage = new BudgetStorage(budgetFilePath);
    }


    private void readStorage() throws IOException {
        items = new TaskList(storage.read());
    }

    private void readPriorityStorage() throws IOException {
        priorityList = new PriorityList(priorityStorage.read());
    }

    private void readContactStorage() throws IOException {
        contactList = new ContactList(contactStorage.read());
    }

    private void readBudgetStorage() throws IOException {
        budgetList = new BudgetList(budgetStorage.read());
    }

    private void readCredits() throws IOException {
        creditsDesc = storage.readCredits();
    }

    private void createEmptyTaskList() {
        items = new TaskList();
    }

    private void createEmptyPriorityList() {
        priorityList = new PriorityList();
    }

    private void createEmptyContactList() {
        contactList = new ContactList();
    }

    private void createEmptyBudgetList() {
        budgetList = new BudgetList();
    }

    /**
     * Checks whether the sample files are used and inform the user.
     *
     * @return String of a message informing using of sample files.
     */
    public String checkSampleUsed() {
        String str = "";
        if (sampleTaskFileUsed) {
            str += ErrorMessages.MISSING_TASKFILE.message;
        }
        if (samplePriorityFileUsed) {
            str += ErrorMessages.MISSING_PRIORITYFILE.message;
        }
        if (sampleContactsFileUsed) {
            str += ErrorMessages.MISSING_CONTACTSFILE.message;
        }
        if (sampleBudgetFileUsed) {
            str += ErrorMessages.MISSING_BUDGETFILE.message;
        }
        return str;
    }
    //@@author

    //@@author maxxyx96
    /**
     * Creates a directory for data storage if there is none created yet.
     *
     */
    public void checkStorageExist() {
        File storageFileDirectory = new File(storageFilePath);
        if (!storageFileDirectory.exists()) {
            storageFileDirectory.mkdirs();
        }
    }

    /**
     * Gets the budget list from Duke.
     *
     * @return the budget List.
     */
    public BudgetList getBudgetList() {
        return budgetList;
    }
    //@@author

    //@@author e0318465
    /**
     * Retrieves the current contact list (GUI).
     *
     * @return A list of contacts.
     */
    public ContactList getFullContactList() {
        return contactList;
    }
    //@@author

    /**
     * Executes a command to overwrite existing storage with the current updated lists(GUI).
     *
     * @param cmd Command to be executed.
     * @throws IOException  If there is an error writing the text file.
     */
    public void saveState(Command cmd) throws IOException {
        cmd.executeStorage(items, ui, storage, budgetStorage, budgetList,
                contactStorage, contactList, priorityStorage, priorityList);
    }

    //@@author talesrune
    /**
     * Retrieves a command from interpreting the user input (GUI).
     *
     * @param sentence The user input.
     * @return Command to be executed thereafter.
     * @throws Exception  If there is an error reading the command.
     */
    public Command getCommand(String sentence) throws Exception {
        Command cmd = Parser.parse(sentence, items, budgetList, contactList);
        return cmd;
    }

    /**
     * Executes a command and outputs the result to the user (GUI).
     *
     * @param cmd Command to be executed.
     * @return String to be outputted.
     */
    public String executeCommand(Command cmd) {
        String str;
        if (cmd instanceof ListPriorityCommand
                || cmd instanceof SetPriorityCommand
                || cmd instanceof FindTasksByPriorityCommand
                || cmd instanceof AddCommand
                || cmd instanceof DeleteCommand) {
            str = cmd.executeGui(items, priorityList, ui);
        } else {
            str = cmd.executeGui(items, ui);
        }
        if (cmd instanceof FilterCommand) {
            cmd.execute(items,filterList);
        }
        return str;
    }

    /**
     * Retrieves the current task list (GUI).
     *
     * @return A list of tasks.
     */
    public TaskList getTaskList() {
        return items;
    }

    /**
     * Retrieves the current task list (GUI).
     *
     * @return A list of tasks.
     */
    public FilterList getFilterList() {
        return filterList;
    }

    /**
     * Retrieves credits list (GUI).
     *
     * @return A list of credits.
     */
    public String getCreditsList() {
        return creditsDesc;
    }
    //@@author

    //@@author maxxyx96
    /**
     * Saves the current state of the lists upon closing by "x" or "alt + f4".
     *
     */
    public void suddenStop() {
        try {
            TimeUnit.SECONDS.sleep(1);
            priorityStorage.write(priorityList);
            contactStorage.write(contactList);
            budgetStorage.write(budgetList);
            storage.write(items);
            logger.info("Save success.");
            new DukeLogger().stopLogger(logger);
        } catch (IOException | InterruptedException i) {
            logger.severe("Error saving storage files upon exiting.");
        }
    } //@@author
}