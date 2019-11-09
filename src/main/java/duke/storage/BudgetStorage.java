package duke.storage;

import duke.exception.DukeException;
import duke.logic.parser.Parser;
import duke.model.Budget;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BudgetStorage {
    private static final String STORAGE_DELIMITER = "\n";

    private static final File DEFAULT_USER_DIRECTORY = new File("data" + File.separator + "duke");
    private static final File BUDGET_FILE = new File(DEFAULT_USER_DIRECTORY, "budget.txt");

    /**
     * Constructor of Budget object.
     *
     * @throws DukeException if the file cannot be created or read.
     * @throws IOException   if the file cannot be created or read.
     */
    public BudgetStorage() throws DukeException, IOException {
        DEFAULT_USER_DIRECTORY.mkdirs();
    }

    /**
     * Writes to the save file.
     *
     * @throws DukeException if unable to save the file successfully
     */
    public void saveBudget(Budget budget) throws DukeException {
        try {
            Map<String, BigDecimal> budgetCategory = budget.getBudgetCategory();
            BUDGET_FILE.createNewFile();
            try (FileWriter fileWriter = new FileWriter(BUDGET_FILE)) {
                fileWriter.write(budget.getMonthlyBudgetString());
                fileWriter.write(STORAGE_DELIMITER);
                if (!budgetCategory.isEmpty()) {
                    for (String category : budgetCategory.keySet()) {
                        BigDecimal budgetBD = budgetCategory.get(category);
                        fileWriter.write(category + " " + budgetBD);
                        fileWriter.write(STORAGE_DELIMITER);
                    }
                }
            }
        } catch (IOException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_SAVE_FILE_FAILED, BUDGET_FILE.getPath()));
        }
    }

    /**
     * loads from the save file.
     *
     * @throws DukeException if the file cannot be created or read.if the file cannot be created or read.
     * @throws IOException   if the file cannot be created or read.
     */
    public Budget loadBudget() throws DukeException, IOException {
        BUDGET_FILE.createNewFile();
        BigDecimal monthlyBudget = BigDecimal.ZERO;
        Map<String, BigDecimal> budgetCategory = new HashMap<>();
        try (Scanner fileReader = new Scanner(BUDGET_FILE).useDelimiter(STORAGE_DELIMITER)) {
            if (fileReader.hasNext()) {
                String monthlyBudgetString = fileReader.next();
                monthlyBudget = new BigDecimal(monthlyBudgetString);
            }
            while (fileReader.hasNext()) {
                String line = fileReader.next();
                String[] separatedLine = line.split(" ");
                int lineLength = separatedLine.length;
                StringBuilder categoryBuilder = new StringBuilder();
                for (int wordNumber = 0; wordNumber < lineLength - 2; ++wordNumber) {
                    categoryBuilder.append(separatedLine[wordNumber]).append(" ");
                }
                categoryBuilder.append(separatedLine[lineLength - 2]);
                String budgetString = separatedLine[lineLength - 1];
                BigDecimal budget = Parser.parseMoney(budgetString);
                budget.setScale(2, RoundingMode.HALF_UP);
                budgetCategory.put(categoryBuilder.toString(), budget);
            }
        } catch (IOException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_LOAD_FILE_FAILED, BUDGET_FILE.getPath()));
        }
        return new Budget(monthlyBudget, budgetCategory);
    }
}
