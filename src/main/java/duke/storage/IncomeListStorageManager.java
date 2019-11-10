package duke.storage;

import duke.commons.FileUtil;
import duke.commons.LogsCenter;
import duke.exception.DukeException;
import duke.model.Income;
import duke.model.IncomeList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;

import static java.util.Objects.requireNonNull;

/**
 * Settles the storage of incomeList
 */
public class IncomeListStorageManager implements IncomeListStorage {

    private static final Logger logger = LogsCenter.getLogger(IncomeListStorageManager.class);

    private static final File DEFAULT_USER_DIRECTORY = new File("data" + File.separator + "duke");
    private static final File INCOME_FILE = new File(DEFAULT_USER_DIRECTORY, "income.txt");

    private static String STORAGE_DELIMITER = "\n\n";

    /**
     * Constructor of IncomeListStorageManager
     *
     * @throws IOException if I/O error is encountered
     */
    public IncomeListStorageManager() throws IOException {
        FileUtil.createIfMissing(INCOME_FILE.toPath());
        logger.info("income.txt file created");
    }

    /**
     * Saves and stores the incomeList to income.txt by overwriting income.txt.
     *
     * @param incomeList the updated incomeList right before termination of Duke++
     * @throws DukeException if I/O error is encountered
     */
    @Override
    public void saveIncomeList(IncomeList incomeList) throws DukeException {
        try {
            try (FileWriter fileWriter = new FileWriter(INCOME_FILE)) {
                for (Income income : incomeList.getInternalList()) {
                    fileWriter.write(income.toStorageString());
                    fileWriter.write(STORAGE_DELIMITER);
                }
            }
        } catch (IOException e) {
            throw new DukeException(String.format(DukeException.
                    MESSAGE_SAVE_FILE_FAILED, INCOME_FILE.getPath()));
        }
    }

    /**
     * Loads incomeList from income.txt.
     * Creates a new incomeList if income.txt is corrupted.
     *
     * @return IncomeList(newList) new incomeList
     * @throws DukeException if file is corrupted
     */
    @Override
    public IncomeList loadIncomeList() throws DukeException {
        List<Income> internalList = new ArrayList<Income>();
        try {
            try (Scanner fileReader = new Scanner(INCOME_FILE).useDelimiter(STORAGE_DELIMITER)) {
                while (fileReader.hasNext()) {
                    internalList.add(IncomeList.itemFromStorageString(fileReader.next()));
                }
            }
            requireNonNull(internalList);
            return new IncomeList(internalList);
        } catch (IOException | DukeException | IllegalStateException e) {
            logger.info("Income file is corrupted! Overwriting current income.txt file...");
            List<Income> newList = new ArrayList<Income>();
            return new IncomeList(newList);
        }
    }
}
