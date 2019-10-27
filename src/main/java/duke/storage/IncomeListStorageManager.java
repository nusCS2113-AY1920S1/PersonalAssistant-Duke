package duke.storage;

import duke.exception.DukeException;
import duke.model.Income;
import duke.model.IncomeList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class IncomeListStorageManager implements IncomeListStorage {

    private static final File DEFAULT_USER_DIRECTORY = new File("data" + File.separator + "duke");
    private static final File INCOME_FILE = new File(DEFAULT_USER_DIRECTORY, "income.txt");


    private static String STORAGE_DELIMITER = "\n\n";

    public IncomeListStorageManager() {
        DEFAULT_USER_DIRECTORY.mkdirs();
    }

    @Override
    public void saveIncomeList(IncomeList incomeList) throws DukeException {
        try {
            INCOME_FILE.createNewFile();
            try (FileWriter fileWriter = new FileWriter(INCOME_FILE)) {
                for (Income income : incomeList.getInternalList()) {
                    fileWriter.write(income.toStorageString());
                    fileWriter.write(STORAGE_DELIMITER);
                }
            }
        } catch (IOException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_SAVE_FILE_FAILED, INCOME_FILE.getPath()));
        }
    }

    @Override
    public IncomeList loadIncomeList() throws DukeException {
        List<Income> internalList = new ArrayList<Income>();
        try {
            INCOME_FILE.createNewFile();
            try (Scanner fileReader = new Scanner(INCOME_FILE).useDelimiter(STORAGE_DELIMITER)) {
                while (fileReader.hasNext()) {
                    internalList.add(IncomeList.itemFromStorageString(fileReader.next()));
                }
            }
        } catch (IOException | DukeException e) {
            throw new DukeException(String.format(DukeException.MESSAGE_LOAD_FILE_FAILED, INCOME_FILE.getPath()));
        }
        return new IncomeList(internalList);
    }
}
