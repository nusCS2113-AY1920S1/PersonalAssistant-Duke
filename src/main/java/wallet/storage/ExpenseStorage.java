package wallet.storage;

import wallet.exception.InsufficientParameters;
import wallet.exception.WrongParameterFormat;
import wallet.model.record.Category;
import wallet.model.record.Expense;
import wallet.model.record.RecurrenceRate;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ExpenseStorage extends Storage<Expense> {
    public static final String DEFAULT_STORAGE_FILEPATH_EXPENSE = "./data/expense.txt";
    public static final String MESSAGE_ERROR_DATA_FORMAT = "Error in format of data when reading from text file.";
    public static final String MESSAGE_ERROR_FILE_NOT_FOUND = "No saved expenses found.";
    public static final String MESSAGE_ERROR_WRITE_EXPENSE = "Error encountered when writing to text file.";

    /**
     * Loads the expenses from expense.txt into a temporary ArrayList of Expense objects.
     *
     * @return The ArrayList of Expense objects.
     */
    //@@author kyang96
    @Override
    public ArrayList<Expense> loadFile() {
        ArrayList<Expense> expenseList = new ArrayList<>();

        try {
            RandomAccessFile raf = new RandomAccessFile(DEFAULT_STORAGE_FILEPATH_EXPENSE, "rws");
            String str;
            while (raf.getFilePointer() != raf.length()) {
                str = raf.readLine();
                String[] data = str.split(",");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                Expense expense = null;
                if (data.length == 5) {
                    expense = new Expense(data[1], LocalDate.parse(data[3], formatter), Double.parseDouble(data[2]),
                            Category.getCategory(data[4]), false, RecurrenceRate.NO);
                } else {
                    expense = new Expense(data[1], LocalDate.parse(data[3], formatter), Double.parseDouble(data[2]),
                            Category.getCategory(data[4]), true, RecurrenceRate.getRecurrence(data[5]));
                }

                if (expense != null) {
                    expense.setId(Integer.parseInt(data[0]));
                    expenseList.add(expense);
                }
            }
            raf.close();
        } catch (FileNotFoundException e) {
            throw new WrongParameterFormat(MESSAGE_ERROR_FILE_NOT_FOUND);
        } catch (IOException | ArrayIndexOutOfBoundsException iob) {
            throw new InsufficientParameters(MESSAGE_ERROR_DATA_FORMAT);
        }

        return expenseList;
    }

    /**
     * Writes the expense list into expense.txt.
     */
    @Override
    public void writeListToFile(ArrayList<Expense> expenseList) {
        try {
            RandomAccessFile raf = new RandomAccessFile(DEFAULT_STORAGE_FILEPATH_EXPENSE, "rws");
            raf.setLength(0);

            for (Expense expense : expenseList) {
                if (raf.getFilePointer() != 0) {
                    raf.writeBytes("\r\n");
                }
                raf.writeBytes(expense.writeToFile());
            }
            raf.close();
        } catch (IOException e) {
            throw new WrongParameterFormat(MESSAGE_ERROR_WRITE_EXPENSE);
        }
    }
    //@@author
}