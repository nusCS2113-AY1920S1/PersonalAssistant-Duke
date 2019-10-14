package duke.dukeobject;

import duke.exception.DukeException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Scanner;

public class Budget {


    private static final String STORAGE_DELIMITER = "\n";

    private File file;

    private BigDecimal monthlyBudget;

    /**
     * Maps a category to the budget set for the category.
     */
    private HashMap<String, BigDecimal> budgetCategory;

    /**
     * Constructor of Budget object.
     *
     * @param file the File for budget in Duke
     * @throws DukeException if the file cannot be created or read.
     * @throws IOException   if the file cannot be created or read.
     */
    public Budget(File file) throws DukeException, IOException {
        this.file = file;
        load();
    }

    /**
     * Writes to the save file.
     *
     * @throws DukeException if unable to save the file successfully
     */
    public void save() throws DukeException {
        try {
            file.createNewFile();
            try (FileWriter fileWriter = new FileWriter(file)) {
                fileWriter.write(monthlyBudget.toPlainString());
                fileWriter.write(STORAGE_DELIMITER);
                if (!budgetCategory.isEmpty()) {
                    for (String category : budgetCategory.keySet()) {
                        BigDecimal budget = budgetCategory.get(category);
                        fileWriter.write(category + " " + budget);
                        fileWriter.write(STORAGE_DELIMITER);
                    }
                }
            }
        } catch (IOException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_SAVE_FILE_FAILED, file.getPath()));
        }
    }


    /**
     * loads from the save file.
     *
     * @throws DukeException if the file cannot be created or read.if the file cannot be created or read.
     * @throws IOException   if the file cannot be created or read.
     */
    public void load() throws DukeException, IOException {
        file.createNewFile();
        monthlyBudget = BigDecimal.ZERO;
        budgetCategory = new HashMap<>();
        try (Scanner fileReader = new Scanner(file).useDelimiter(STORAGE_DELIMITER)) {
            if (fileReader.hasNext()) {
                String monthlyBudgetString = fileReader.next();
                monthlyBudget = new BigDecimal(monthlyBudgetString);
            }
            while (fileReader.hasNext()) {
                String line = fileReader.next();
                String[] separatedLine = line.split(" ");
                String category = separatedLine[0];
                String budgetString = separatedLine[1];
                BigDecimal budget = new BigDecimal(budgetString);
                budget.setScale(2, RoundingMode.HALF_UP);
                budgetCategory.put(category, budget);
            }
        } catch (IOException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_LOAD_FILE_FAILED, file.getPath()));
        }
    }

    /**
     * Setter method for monthlyBudget.
     *
     * @param monthlyBudget BigDecimal budget set for each month
     */
    public void setMonthlyBudget(BigDecimal monthlyBudget) {
        this.monthlyBudget = monthlyBudget;
    }

    /**
     * Gets a string value for montlyBudget.
     *
     * @return a String of the monthly budget
     */
    public String getMonthlyBudgetString() {
        return "$" + monthlyBudget.toPlainString();
    }

    /**
     * Sets budget to a given category.
     *
     * @param category the String tag specified that we want to set a budget for
     * @param budget   a BigDecimal amount for the budget we want to set
     */
    public void setCategoryBudget(String category, BigDecimal budget) {
        budgetCategory.put(category, budget);
    }

    /**
     * Gets the difference between the monthly budget and the total expenses spent.
     *
     * @param total the BigDecimal total expenditure from expenseList
     * @return BigDecimal value fo the difference
     */
    public BigDecimal getRemaining(BigDecimal total) {
        return monthlyBudget.subtract(total);
    }

    public HashMap<String, BigDecimal> getBudgetCategory() {
        return budgetCategory;
    }

}
