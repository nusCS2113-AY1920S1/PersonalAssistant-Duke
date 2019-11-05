package storage;

import duke.exception.DukeException;
import executor.command.CommandType;
import executor.task.Task;
import executor.task.TaskList;
import executor.task.TaskType;
import ui.Receipt;
import ui.ReceiptTracker;
import ui.Wallet;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class StorageManager {
    private String initializationStatus;
    private StorageWallet walletStore;
    private StorageTask taskStore;
    private Wallet wallet = new Wallet();
    private TaskList taskList = new TaskList();

    public StorageManager(String taskPath, String walletPath) {
        this.initializationStatus = "";
        this.initializationStatus += this.loadTasks(taskPath);
        this.initializationStatus += this.loadWallet(walletPath);
    }

    public StorageManager() {
        this.initializationStatus = "";
    }

    public void saveAllData() {
        this.taskStore.saveData(this.taskList);
        this.walletStore.saveData(this.wallet);
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

    public ReceiptTracker getReceiptsByTag(String tag) throws DukeException {
        try {
            return this.wallet.getReceipts().getReceiptsByTag(tag);
        } catch (Exception e) {
            throw new DukeException("Unable to get receipts with tag: " + tag + "\n");
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

    public String getPrintableReceipts() {
        return this.wallet.getReceipts().getPrintableReceipts();
    }

    public String getPrintableTasks() {
        return this.taskList.getPrintableTasks();
    }

    public String getPrintableTaskByIndex(int index) throws DukeException {
        try {
            return getPrintableTaskByIndex(index);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("The TaskList does not contain that Task index.\n");
        } catch (IndexOutOfBoundsException e) {
            throw new DukeException("TaskList does not contain that Task index.\n");
        }
    }

    public void markTaskDoneByIndex(int index) throws DukeException {
        try {
            this.taskList.get(index).markDone();
        } catch (Exception e) {
            throw new DukeException("Unable to mark Task as done. \n");
        }
    }

    public void loadQueuedTasksByIndex(int index) throws DukeException {
        try {
            this.taskList.loadQueuedTasks(this.taskList.get(index));
        } catch (Exception e) {
            throw new DukeException("Unable to Load Queued Tasks.\n");
        }
    }

    public void createTask(TaskType taskType, String userInput) throws DukeException {
        try {
            this.taskList.addTask(TaskList.createTask(taskType, userInput));
        } catch (DukeException e) {
            throw e;
        } catch (Exception e) {
            throw new DukeException("Unable to add new Task into TaskList.\n");
        }
    }

    public String getPrintableLatestTask() {
        return this.taskList.getMostRecentTaskAdded().genTaskDesc();
    }

    public void initializeQueueByIndex(int index) {
        this.taskList.get(index).initializeQueue();
    }

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
            this.taskList = this.taskStore.loadData();
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
            this.wallet = this.walletStore.loadData();
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
