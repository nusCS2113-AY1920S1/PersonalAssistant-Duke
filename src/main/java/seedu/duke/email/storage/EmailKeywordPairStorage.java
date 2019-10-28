package seedu.duke.email.storage;

import org.json.JSONArray;
import org.json.JSONException;
import seedu.duke.common.storage.Storage;
import seedu.duke.email.EmailKeywordPairList;
import seedu.duke.email.entity.KeywordPair;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * A helper class to save and read keyword pairs to and from local file.
 */
public class EmailKeywordPairStorage implements Storage {
    private static String keywordPairFilename = "keywords.txt";

    /**
     * Save keyword pair list to local file.
     *
     * @param keywordPairList keyword pair list to be saved
     * @throws JSONException when error occurs at serializing keyword pair json array
     * @throws IOException when error occurs at file io of keyword pair file
     */
    public static void saveKeywordPairList(EmailKeywordPairList keywordPairList) throws JSONException,
            IOException {
        String content = constructKeywordPairJson(keywordPairList).toString();
        Path path = Storage.prepareDataPath(keywordPairFilename);
        Storage.saveToFile(path, content);
    }

    /**
     * read keyword pair list from local file.
     *
     * @return keyword pair list read
     * @throws IOException when error occurs at file io of keyword pair file
     * @throws JSONException when error occurs at parsing keyword pair json array
     */
    public static EmailKeywordPairList readKeywordPairList() throws IOException, JSONException {
        Path path = Storage.prepareDataPath(keywordPairFilename);
        String content = Storage.readFromFile(path);
        return parseKeywordPairJson(new JSONArray(content));
    }

    /**
     * Checks whether the keyword pair file already exists.
     *
     * @return whether the keyword pair file already exists
     */
    public static boolean keywordPairFileExists() {
        return Storage.fileExists(Storage.prepareDataPath(keywordPairFilename));
    }

    private static JSONArray constructKeywordPairJson(EmailKeywordPairList keywordPairList) throws JSONException {
        JSONArray array = new JSONArray();
        for (KeywordPair keywordPair : keywordPairList) {
            array.put(keywordPair.toJsonObject());
        }
        return array;
    }

    private static EmailKeywordPairList parseKeywordPairJson(JSONArray array) throws JSONException {
        EmailKeywordPairList keywordPairList = new EmailKeywordPairList();
        for (int i = 0; i < array.length(); i++) {
            keywordPairList.add(new KeywordPair(array.getJSONObject(i)));
        }
        return keywordPairList;
    }
}
