//@@author e0323290

package gazeeebo.storage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ExpensePageStorage {
    /**
     * Expense storage file name.
     */
    private String relativePathExpensesResource
            = "Expenses.txt";

    /**
     * This method writes to the file Expense.txt.
     *
     * @param fileContent save the expenses into this file
     * @throws IOException catch the error if the read file fails.
     */
    public void writeToExpensesFile(String fileContent) throws IOException {
        FileWriter fileWriter = new FileWriter(relativePathExpensesResource);
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * This method read from the file
     * Expense.txt and put the details into a HashMap.
     *
     * @return Returns the HashMap of expenses,
     *         key is the date of purchase and the value is list of items and price.
     * @throws FileNotFoundException catch the error if the read file fails.
     */
    public HashMap<LocalDate, ArrayList<String>> readFromExpensesFile() throws FileNotFoundException {
        HashMap<LocalDate, ArrayList<String>> expenses = new HashMap<LocalDate, ArrayList<String>>();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        File f = new File(relativePathExpensesResource);
        Scanner sc = new Scanner(f);
        while (sc.hasNext()) {
            ArrayList<String> itemAndPriceList = new ArrayList<>();
            String[] split = sc.nextLine().split("\\|");
            LocalDate dateOfPurchase = LocalDate.parse(split[0], fmt);
            boolean isEqual = false;
            for (LocalDate key : expenses.keySet()) {
                if (dateOfPurchase.equals(key)) { //if date equal
                    expenses.get(key).add(split[1]);
                    isEqual = true;
                }
            }
            if (!isEqual) {
                itemAndPriceList.add(split[1]);
                expenses.put(dateOfPurchase, itemAndPriceList);
            }
        }
        return expenses;
    }
}
