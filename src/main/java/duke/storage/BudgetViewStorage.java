package duke.storage;

import duke.exception.DukeException;
import duke.model.BudgetView;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BudgetViewStorage {
    private static final String STORAGE_DELIMITER = "\n";

    private static final File DEFAULT_USER_DIRECTORY = new File("data" + File.separator + "duke");
    private static final File BUDGETVIEW_FILE = new File(DEFAULT_USER_DIRECTORY, "budgetView.txt");

    /**
     * Constructor of Budget object.
     *
     * @throws DukeException if the file cannot be created or read.
     * @throws IOException   if the file cannot be created or read.
     */
    public BudgetViewStorage() throws DukeException, IOException {
        DEFAULT_USER_DIRECTORY.mkdirs();
    }

    /**
     * Writes to the save file.
     *
     * @throws DukeException if unable to save the file successfully
     */
    public void saveBudgetView (BudgetView budgetView) throws DukeException {
        try {
            Map<Integer, String> budgetViewCategory = budgetView.getBudgetViewCategory();
            BUDGETVIEW_FILE.createNewFile();
            try (FileWriter fileWriter = new FileWriter(BUDGETVIEW_FILE)) {
                if (!budgetViewCategory.isEmpty()) {
                    for (Integer view : budgetViewCategory.keySet()) {
                        String category = budgetViewCategory.get(view);
                        fileWriter.write(view + " " + category);
                        fileWriter.write(STORAGE_DELIMITER);
                    }
                }
            }
        } catch (IOException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_SAVE_FILE_FAILED, BUDGETVIEW_FILE.getPath()));
        }
    }

    /**
     * loads from the save file.
     *
     * @throws DukeException if the file cannot be created or read.if the file cannot be created or read.
     * @throws IOException   if the file cannot be created or read.
     */
    public BudgetView loadBudgetView() throws DukeException, IOException {
        BUDGETVIEW_FILE.createNewFile();
        Map<Integer, String> budgetViewCategory = new HashMap<>();
        try (Scanner fileReader = new Scanner(BUDGETVIEW_FILE).useDelimiter(STORAGE_DELIMITER)) {
            while (fileReader.hasNext()) {
                String line = fileReader.next();
                String[] separatedLine = line.split(" ", 2);
                int view = Integer.parseInt(separatedLine[0]);
                budgetViewCategory.put(view, separatedLine[1]);
            }
        } catch (IOException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_LOAD_FILE_FAILED, BUDGETVIEW_FILE.getPath()));
        }
        return new BudgetView(budgetViewCategory);
    }
}
