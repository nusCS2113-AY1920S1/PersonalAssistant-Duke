package duke.storage;

import duke.task.BudgetList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BudgetStorage {
    //protected String filePath = "./";
    protected String filePath = "";
    String storageClassPath = Storage.class.getProtectionDomain().getCodeSource().getLocation().getPath();

    /**
     * Creates a storage with path pointing to the file in the system.
     *
     * @param filePathForBudget The location of the file in computer.
     */
    public BudgetStorage(String filePathForBudget) {
        String[] pathSplitter = storageClassPath.split("/");
        for (String directory: pathSplitter) {
            if (!directory.isEmpty() && !directory.equals("build")) {
                this.filePath += directory + "/";
            } else if (directory.equals("build")) {
                break;
            }
        }
        this.filePath += filePathForBudget;
    }

    /**
     * Updates the task list from reading the contents of the text file.
     *
     * @return ArrayList to update the Expenses.
     * @throws IOException  If there is an error reading the text file.
     */
    public ArrayList<Float> read() throws IOException {
        ArrayList<Float> items = new ArrayList<>();
        File file = new File(filePath);
        BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
        String budget;

        while ((budget = bufferedReader.readLine()) != null) {
            items.add(Float.parseFloat(budget.trim()));
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
}
