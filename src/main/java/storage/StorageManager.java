package storage;

import duke.exception.DukeException;
import executor.command.CommandType;
import storage.task.Task;
import storage.task.TaskList;
import storage.task.TaskType;
import storage.wallet.Receipt;
import storage.wallet.ReceiptTracker;
import storage.wallet.Wallet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StorageManager {
    private String initializationStatus;
    private StorageWallet walletStore;
    private StorageTask taskStore;
    private Wallet wallet = new Wallet();
    private TaskList taskList = new TaskList();


    /**
     * Constructor for the StorageManager Object.
     * @param taskPath String detailing the filepath to the stored Task Data
     * @param walletPath String detailing the filepath to the stored Wallet Data
     */
    public StorageManager(String taskPath, String walletPath) {
        this.initializationStatus = "";
        this.initializationStatus += this.loadTasks(taskPath);
        this.initializationStatus += this.loadWallet(walletPath);
    }

    public StorageManager() {
        this.initializationStatus = "";
    }

    /**
     * Saves both the TaskList and the Wallet.
     * @throws DukeException Error Saving either the Wallet or the TaskList
     */
    public void saveAllData() throws DukeException {
        this.taskStore.saveData(this.taskList);
        this.walletStore.saveData(this.wallet);
    }

    /**
     * Loads both TaskList and Wallet Test Data for Testers.
     * @throws DukeException Cannot find either Wallet or Test Data
     */
    public void loadTestData() throws DukeException {
        this.taskStore.loadTestData(this.taskList);
        this.walletStore.loadTestData(this.wallet);
    }

    /**
     * Tracks a particular tag.
     * @param tag String to track by
     * @throws DukeException The tag is already tracked.
     */
    public void trackTag(String tag) throws DukeException {
        this.wallet.addFolder(tag);
    }

    /**
     * Untracks a particular tag.
     * @param tag String to untrack
     * @throws DukeException The tag is not yet tracked.
     */
    public void untrackTag(String tag) throws DukeException {
        this.wallet.removeFolder(tag);
    }


    /**
     * Adds a receipt to the Wallet Object.
     * Used in CommandAddReceipt.
     * @param r Receipt Object to be added into the Wallet
     * @throws DukeException Error occurred when trying to add Receipt to Wallet.
     */
    public void addReceipt(Receipt r) throws DukeException {
        try {
            this.wallet.addReceipt(r);
        } catch (Exception e) {
            throw new DukeException("Unable to add receipt to the Wallet.\n");
        }
    }

    /**
     * Gets all receipts for a particular date.
     * Used by CommandDateList.
     * @param date String that represents the date
     * @return ReceiptTracker containing all the Receipts of a particular date
     * @throws DukeException Error occurred when getting Receipts on Date
     */
    public ReceiptTracker getReceiptsByDate(String date) throws DukeException {
        try {
            return this.wallet.getReceiptsByDate(date);
        } catch (Exception e) {
            throw new DukeException("Unable to get receipts with Date:" + date + "\n");
        }
    }

    /**
     * Returns a TaskList of Task by a specific date.
     * @param date LocalDate to be found
     * @return TaskList containing all the Tasks matching date
     * @throws DukeException Error when trying to access Tasks at the given date.
     */
    public TaskList getTasksByDate(LocalDate date) throws DukeException {
        TaskList filteredTaskList = new TaskList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
        try {
            for (Task task : this.taskList) {
                String dateOfTask = task.getDate().format(formatter);
                if (dateOfTask.equals(date.format(formatter))) {
                    filteredTaskList.addTask(task);
                }
            }
        } catch (Exception e) {
            throw new DukeException("There are no tasks for today.\n");
        }
        return filteredTaskList;
    }

    /**
     * Gets all receipts for a particular month in a particular year.
     * @param month int representing month to look for
     * @param year int representing year to look for
     * @return ReceiptTracker containing all the Receipts for a partiuclar month/year
     * @throws DukeException Error occurred when getting receipts by month
     */
    public ReceiptTracker getReceiptsByMonthDate(int month, int year) throws DukeException {
        try {
            return this.wallet.getReceiptsByMonthYear(month, year);
        } catch (Exception e) {
            throw new DukeException("Unable to get receipt from month/year: " + month + "/" + year + "\n");
        }
    }

    /**
     * Gets all receipts for a particular year.
     * @param year int representing year to look for
     * @return ReceiptTracker containing all the Receipts for a partiuclar year
     * @throws DukeException Error occurred when getting receipts by month
     */
    public ReceiptTracker getReceiptsByYear(int year) throws DukeException {
        try {
            return this.wallet.getReceiptsByYear(year);
        } catch (Exception e) {
            throw new DukeException("Unable to get receipts from year: " + year + "\n");
        }
    }

    /**
     * Accessor method to ReceiptTracker's getReceiptByTag() method.
     * @param tag String to be found in tag
     * @return ReceiptTracker containing all the Receipts with tag
     * @throws DukeException Error when trying to obtain all receipts with the tag
     */
    public ReceiptTracker getReceiptsByTag(String tag) throws DukeException {
        try {
            return this.wallet.getReceipts().getReceiptsByTag(tag);
        } catch (Exception e) {
            throw new DukeException("Unable to get receipts with tag: " + tag + "\n");
        }
    }

    /**
     * Gets all receipts that have cash spent attribute more than or equal to user input.
     * Used by CommandMajorExpense
     * @param amount String that represents the user input
     * @return ReceiptTracker containing all the major expense receipts
     * @throws DukeException Error occurred when getting major expenses
     */
    public String getMajorExpense(String amount) throws DukeException {
        try {
            return this.wallet.getReceipts().getMajorExpenses(amount).getPrintableReceipts();
        } catch (NumberFormatException e) {
            throw new DukeException("Invalid cash input. Please enter integer");
        } catch (DukeException e) {
            throw e;
        } catch (Exception e) {
            throw new DukeException("Unable to get major expenses");
        }
    }

    /**
     * Gets all receipts that have cash spent attribute more than or equal to $100.
     * Used by CommandMajorExpense
     * @return ReceiptTracker containing all the receipts above or equal to $100
     * @throws DukeException Error occurred when getting major expenses
     */
    public String getMajorReceipt() throws DukeException {
        try {
            return this.wallet.getReceipts().getMajorReceipts().getPrintableReceipts();
        } catch (DukeException e) {
            throw e;
        } catch (Exception e) {
            throw new DukeException("Unable to get major receipts");
        }
    }

    /**
     * Deletes Task by Index in TaskList Object.
     * Used in CommandDelete.
     * @param index int of Task to be deleted
     * @throws DukeException Error occured when trying to delete Task
     */
    public void deleteTaskByIndex(int index) throws DukeException {
        try {
            this.taskList.deleteTaskByIndex(index);
        } catch (Exception e) {
            throw new DukeException("Invalid 'delete' statement. "
                    + "Please indicate the index of the task you wish to mark delete.\n");
        }
    }

    /**
     * Deletes Receipt by Index in ReceiptTracker Object.
     * Used in CommandDeleteReceipt
     * @param index int of Receipt to be deleted
     * @throws DukeException Error occured when trying to delete Receipt
     */
    public void deleteReceiptByIndex(int index) throws DukeException {
        try {
            this.wallet.getReceipts().deleteReceiptsByIndex(index);
        } catch (Exception e) {
            throw new DukeException("Invalid 'deletereceipt' statement."
            + "Please indicate the index of the receipt you wish to delete.\n");
        }
    }

    /**
     * Gets the taskName property of a Task by its index in a TaskList.
     * Used in CommandDelete.
     * @param index int representing the index of the Task
     * @return String corresponding to the taskName property of the Task
     * @throws DukeException Error occurred when retrieving the taskName of the Task
     */
    public String getTaskNameByIndex(int index) throws DukeException {
        try {
            return this.taskList.get(index).getTaskName();
        } catch (Exception e) {
            throw new DukeException("Unable to get Task Name of Task at index " + index + "\n");
        }
    }


    /**
     * Gets the balance property of the Wallet Object.
     * @return Double representing the balance property of the Wallet Object.
     * @throws DukeException Error occurred when retrieving the balance of the Wallet Object.
     */
    public Double getWalletBalance() throws DukeException {
        try {
            return this.wallet.getBalance();
        } catch (Exception e) {
            throw new DukeException("Unable to get Wallet Balance.\n");
        }
    }

    /**
     * Gets the totalExpenses property of the Wallet Object.
     * @return Double representing the totalExpenses property of the Wallet Object.
     * @throws DukeException Error occurred when retrieving the balance of the Wallet Object.
     */
    public Double getWalletExpenses() throws DukeException {
        try {
            return this.wallet.getTotalExpenses();
        } catch (Exception e) {
            throw new DukeException("Unable to get Total Expenses.\n");
        }
    }

    /**
     * Gets all the Tasks containing a string.
     * @param name String to be found
     * @return TaskList containing all the Tasks with the name
     * @throws DukeException Error creating a TaskList with all Tasks containing the name
     */
    public TaskList getTasksByName(String name) throws DukeException {
        try {
            return this.taskList.getTasksByName(name);
        } catch (Exception e) {
            throw new DukeException("Unable to obtain tasks with name: " + name);
        }
    }

    /**
     * Accessor Method to ReceiptTracker's getPrintableReceipts() method.
     * @return String detailing all the Receipts to be printed to the User
     */
    public String getPrintableReceipts() {
        return this.wallet.getReceipts().getPrintableReceipts();
    }

    /**
     * Accessor Method to TaskList's getPrintableTasks() method.
     * @return String detailing all the Tasks to be printed to the User.
     */
    public String getPrintableTasks() {
        return this.taskList.getPrintableTasks();
    }

    /**
     * Accessor Method to Task's markDone Method.
     * @param index int representing the index of the Task to mark done
     * @throws DukeException Error when trying to Mark a Task as Done
     */
    public void markTaskDoneByIndex(int index) throws DukeException {
        try {
            this.taskList.get(index).markDone();
        } catch (Exception e) {
            throw new DukeException("Unable to mark Task as done. \n");
        }
    }

    /**
     * Loads all queued Tasks in particular indexed-Task.
     * @param index int representing the index of the Task to load all the queuedTasks from
     * @throws DukeException Error when trying to load queued Tasks.
     */
    public void loadQueuedTasksByIndex(int index) throws DukeException {
        try {
            this.taskList.loadQueuedTasks(this.taskList.get(index));
        } catch (Exception e) {
            throw new DukeException("Unable to Load Queued Tasks.\n");
        }
    }

    /**
     * Creates a Task and adds it to the TaskList.
     * @param taskType TaskType of the Task to be created.
     * @param userInput String with all the Task details
     * @throws DukeException Error when trying to add a task into the TaskList
     */
    public void createTask(TaskType taskType, String userInput) throws DukeException {
        try {
            this.taskList.addTask(TaskList.createTask(taskType, userInput));
        } catch (DukeException e) {
            throw e;
        } catch (Exception e) {
            throw new DukeException("Unable to add new Task into TaskList.\n");
        }
    }

    /**
     * Returns a String detailing the most recent Task appended to the TaskList.
     * @return String detailing the most recently appended Task
     */
    public String getPrintableLatestTask() {
        return this.taskList.getMostRecentTaskAdded().genTaskDesc();
    }

    /**
     * Initializes a queuedTask List of a Task given its index.
     * @param index int representing the index of the Task in question
     * @throws DukeException Error when trying to access a Task outside the Array Size
     */
    public void initializeQueueByIndex(int index) throws DukeException {
        try {
            this.taskList.get(index).initializeQueue();
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("No task found at Index: " + index + "\n");
        }
    }

    /**
     * Creates and queues a Task to an existing Task given its index.
     * @param index int representing the index of the Task to queue the other Task on
     * @param commandType CommandType of the Task to be created
     * @param taskStr String with all the details of the Task to be created.
     * @throws DukeException Error when trying to queue a task
     */
    public void queueTaskByIndex(int index, CommandType commandType, String taskStr) throws DukeException {
        try {
            Task task = TaskList.createTask(TaskType.valueOf(commandType.toString()), taskStr);
            this.taskList.get(index).getQueuedTasks().addTask(task);
        } catch (DukeException e) {
            throw e;
        } catch (Exception e) {
            throw new DukeException("Unable to queue new task.\n");
        }
    }

    private String loadTasks(String taskPath) {
        this.taskStore = new StorageTask(taskPath);
        String outputStr;
        try {
            this.taskStore.loadData(this.taskList);
            outputStr = "Tasks loaded successfully.\n";
        } catch (DukeException e) {
            outputStr = e.getMessage();
        }
        return outputStr;
    }

    private String loadWallet(String walletPath) {
        String outputStr;
        this.walletStore = new StorageWallet(walletPath);
        try {
            this.walletStore.loadData(this.wallet);
            outputStr = "Wallet loaded successfully.\n";
        } catch (DukeException e) {
            outputStr = e.getMessage();
        }
        return outputStr;
    }

    // Setters & Getters

    public void setWalletBalance(Double amt) {
        this.wallet.setBalance(amt);
    }

    public TaskList getTaskList() {
        return this.taskList;
    }

    public Wallet getWallet() {
        return this.wallet;
    }

    public String getInitializationStatus() {
        return initializationStatus;
    }

    public int getTaskListSize() {
        return this.taskList.size();
    }

    public int getReceiptTrackerSize() {
        return this.wallet.getReceipts().size();
    }

}
