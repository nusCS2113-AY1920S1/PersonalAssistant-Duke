package duke.storage;

import duke.exception.DukeException;
import duke.model.Expense;
import duke.model.ExpenseList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExpenseListStorageManager implements ExpenseListStorage {

    private static final File DEFAULT_USER_DIRECTORY = new File("data" + File.separator + "duke");
    private static final File EXPENSES_FILE = new File(DEFAULT_USER_DIRECTORY, "expenses.txt");


    private static String STORAGE_DELIMITER = "\n\n";

    public ExpenseListStorageManager() {
        DEFAULT_USER_DIRECTORY.mkdirs();
    }

    @Override
    public void saveExpenseList(ExpenseList expenseList) throws DukeException {
        try {
            EXPENSES_FILE.createNewFile();
            try (FileWriter fileWriter = new FileWriter(EXPENSES_FILE)) {
                for (Expense expense : expenseList.getInternalList()) {
                    fileWriter.write(expense.toStorageString());
                    fileWriter.write(STORAGE_DELIMITER);
                }
            }
        } catch (IOException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_SAVE_FILE_FAILED, EXPENSES_FILE.getPath()));
        }
    }

    @Override
    public ExpenseList loadExpenseList() throws DukeException {
        List<Expense> internalList = new ArrayList<Expense>();
        try {
            EXPENSES_FILE.createNewFile();
            try (Scanner fileReader = new Scanner(EXPENSES_FILE).useDelimiter(STORAGE_DELIMITER)) {
                while (fileReader.hasNext()) {
                    internalList.add(ExpenseList.itemFromStorageString(fileReader.next()));
                }
            }
        } catch (IOException | DukeException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_LOAD_FILE_FAILED, EXPENSES_FILE.getPath()));
        }
        return new ExpenseList(internalList);
    }
}
