package duke.storage;

import duke.commons.LogsCenter;
import duke.exception.DukeException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

public class PlanAttributesStorageManager implements PlanAttributesStorage {

    private static final Logger logger = LogsCenter.getLogger(PlanAttributesStorageManager.class);

    private static final File DEFAULT_USER_DIRECTORY = new File("data" + File.separator + "duke");
    private static final File PLAN_ATTRIBUTES_FILE = new File(DEFAULT_USER_DIRECTORY, "planAttributes.txt");

    private static String STORAGE_DELIMITER = "\n\n";

    public PlanAttributesStorageManager() {
        DEFAULT_USER_DIRECTORY.mkdir();
    }

    @Override
    public void savePlanAttributes(Map<String, String> attributes) throws DukeException {
        try {
            PLAN_ATTRIBUTES_FILE.createNewFile();
            try (FileWriter fileWriter = new FileWriter(PLAN_ATTRIBUTES_FILE)) {
                for (String key : attributes.keySet()) {
                    String value = attributes.get(key);
                    fileWriter.write(key + " " + value);
                    fileWriter.write(STORAGE_DELIMITER);
                }
            }
        } catch (IOException e) {
            throw new DukeException(String.format(DukeException
                    .MESSAGE_SAVE_FILE_FAILED, PLAN_ATTRIBUTES_FILE.getPath()));
        }

    }

    @Override
    public Map<String, String> loadAttributes() {
        Map<String, String> attributes = new HashMap<>();
        try {
            PLAN_ATTRIBUTES_FILE.createNewFile();
            try (Scanner scanner = new Scanner(PLAN_ATTRIBUTES_FILE).useDelimiter(STORAGE_DELIMITER)) {
                while (scanner.hasNext()) {
                    String keyValue = scanner.next();
                    String[] keyValueArr = keyValue.split(" ");
                    if (keyValueArr.length == 2) {
                        attributes.put(keyValueArr[0], keyValueArr[1]);
                    }
                }
            }
        } catch (IOException e) {
            logger.warning("Error loading planAttributes Storage, starting with a empty file");
            return new HashMap<>();
        }
        return attributes;
    }
}
