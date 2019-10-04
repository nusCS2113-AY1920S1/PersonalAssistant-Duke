package wallet.storage;

import wallet.model.record.Expense;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ExpenseStorage extends Storage<Expense> {
    public static final String DEFAULT_STORAGE_FILEPATH_EXPENSE = "./data/expense.txt";

    @Override
    public ArrayList<Expense> loadFile() {
        ArrayList<Expense> expenseList = new ArrayList<>();

        try {
            RandomAccessFile raf = new RandomAccessFile(DEFAULT_STORAGE_FILEPATH_EXPENSE, "r");
            String str;
            while (raf.getFilePointer() != raf.length()) {
                str = raf.readLine();
                String[] data = str.split(",");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                Expense expense = null;
                if (data.length == 5) {
                    expense = new Expense(data[1], LocalDate.parse(data[3], formatter), Double.parseDouble(data[2]),
                            data[4], false, null);
                } else {
                    expense = new Expense(data[1], LocalDate.parse(data[3], formatter), Double.parseDouble(data[2]),
                            data[4], true, data[5]);
                }

                if (expense != null) {
                    expense.setId(Integer.parseInt(data[0]));
                    expenseList.add(expense);
                }
            }
            raf.close();
        } catch (FileNotFoundException e) {
            System.out.println("No saved expenses found.");
        } catch (IOException e) {
            System.out.println("End of file.");
        }

        return expenseList;
    }

    @Override
    public void writeToFile(Expense expense) {
        try {
            RandomAccessFile raf = new RandomAccessFile(DEFAULT_STORAGE_FILEPATH_EXPENSE, "rws");
            raf.seek(raf.length());
            if (raf.getFilePointer() != 0) {
                raf.writeBytes("\r\n");
            }
            raf.writeBytes(expense.writeToFile());
            raf.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }

    @Override
    public void updateToFile(Expense expense, int index) {

    }

    @Override
    public void removeFromFile(ArrayList<Expense> expenseList, int index) {

    }
}
