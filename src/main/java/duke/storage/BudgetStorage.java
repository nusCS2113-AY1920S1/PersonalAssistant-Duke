package duke.storage;

import duke.task.BudgetList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.util.ArrayList;

//@@author maxxyx96
public class BudgetStorage {
    private String filePath = System.getProperty("user.dir") + "/";

    /**
     * Creates a storage with path pointing to the file in the system.
     *
     * @param filePathForBudget The location of the file in computer.
     */
    public BudgetStorage(String filePathForBudget) {
        this.filePath += filePathForBudget;
    }

    /**
     * Checks if a variable is convertable to a float value.
     *
     * @param amount the string to be converted to a float value.
     * @return returns true if it can be converted to a float value, false otherwise.
     */
    private boolean isFloat(String amount) {
        try {
            Float.parseFloat(amount.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if a variable is convertable to what we are reading in.
     *
     * @param line the string to be checked
     * @return returns true if it is convertable, false otherwise.
     */
    private boolean isFormatCorrect(String line) {
        try {
            String[] lineSplit = line.split(":");
            Float.parseFloat(lineSplit[0].trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Updates the task list from reading the contents of the text file.
     *
     * @return ArrayList to update the Expenses.
     * @throws IOException If there is an error reading the text file.
     */
    public ArrayList<String> read() throws IOException {
        ArrayList<String> items = new ArrayList<>();
        File file = new File(filePath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String budget = bufferedReader.readLine();
        try {
            if (isFloat(budget)) {
                items.add(budget);
            }
        } catch (Exception e) {
            items.add("0");
        }
        while ((budget = bufferedReader.readLine()) != null) {
            if (isFormatCorrect(budget)) {
                items.add(budget);
            }
        }
        bufferedReader.close();
        return items;
    }

    /**
     * Updates the text file with data that is utilised to calculate the budget.
     *
     * @param budgetList The list of budget-related tasks.
     * @throws IOException If there is an error writing to the text file.
     */
    public void write(BudgetList budgetList) throws IOException {
        String fileContent = "";
        for (int i = 0; i < budgetList.getSize(); i++) {
            fileContent += budgetList.getList().get(i) + "\n";
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(fileContent);
        writer.close();
    }

    //Solution adapted from https://stackoverflow.com/questions/20389255/reading-a-resource-file-from-within-jar
    /**
     * Extracts the sample data from jar file and moves it to data folder in the computer.
     *
     * @param samplePath path of the sample data set for budget.
     * @throws IOException When there is an error writing to the text file.
     */
    public void writeSample(String samplePath) throws IOException {
        String fileContent = "";
        InputStream in = BudgetStorage.class.getResourceAsStream(samplePath);
        if (in == null) {
            in = BudgetStorage.class.getClassLoader().getResourceAsStream(samplePath);
        }
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        String input = "";
        while ((input = bufferedReader.readLine()) != null) {
            fileContent += input + "\n";
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write(fileContent);
        writer.close();
        bufferedReader.close();
        in.close();
    }
}
//@@author
