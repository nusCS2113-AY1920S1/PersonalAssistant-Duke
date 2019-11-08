package seedu.duke.email.storage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import seedu.duke.common.storage.StorageHelper;
import seedu.duke.common.storage.TimestampHelper;
import seedu.duke.email.EmailKeywordPairList;
import seedu.duke.email.entity.KeywordPair;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;

/**
 * A helper class to save and read keyword pairs to and from local file.
 */
public class EmailKeywordPairStorage {
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
        String content = constructKeywordFileJson(keywordPairList).toString();
        Path path = StorageHelper.prepareDataPath(keywordPairFilename);
        StorageHelper.saveToFile(path, content);
    }

    /**
     * read keyword pair list from local file.
     *
     * @return keyword pair list read
     * @throws IOException when error occurs at file io of keyword pair file
     * @throws JSONException when error occurs at parsing keyword pair json array
     */
    public static EmailKeywordPairList readKeywordPairList() throws IOException, JSONException {
        Path path = StorageHelper.prepareDataPath(keywordPairFilename);
        String content = StorageHelper.readFromFile(path);
        return parseKeywordPairJson(new JSONObject(content));
    }

    /**
     * Checks whether the keyword pair file already exists.
     *
     * @return whether the keyword pair file already exists
     */
    public static boolean keywordPairFileExists() {
        return StorageHelper.fileExists(StorageHelper.prepareDataPath(keywordPairFilename));
    }

    private static JSONObject constructKeywordFileJson(EmailKeywordPairList keywordPairList) throws JSONException {
        JSONObject fileJson = new JSONObject();
        fileJson.put("timestamp", getKeywordPairTimestamp(keywordPairList));
        fileJson.put("keywordPairs", constructKeywordPairJson(keywordPairList));
        return fileJson;
    }

    private static String getKeywordPairTimestamp(EmailKeywordPairList keywordPairList) {
        if (keywordPairList.getUpdatedOn() == null) {
            return TimestampHelper.getTimestamp();
        }
        return TimestampHelper.formatDateTime(keywordPairList.getUpdatedOn());
    }

    private static JSONArray constructKeywordPairJson(EmailKeywordPairList keywordPairList) throws JSONException {
        JSONArray array = new JSONArray();
        for (KeywordPair keywordPair : keywordPairList) {
            array.put(keywordPair.toJsonObject());
        }
        return array;
    }

    private static EmailKeywordPairList parseKeywordPairJson(JSONObject fileJson) throws JSONException {
        LocalDateTime timestamp = TimestampHelper.parseTimestamp(fileJson.getString("timestamp"));
        JSONArray array = fileJson.getJSONArray("keywordPairs");
        EmailKeywordPairList keywordPairList = new EmailKeywordPairList();
        for (int i = 0; i < array.length(); i++) {
            keywordPairList.add(new KeywordPair(array.getJSONObject(i)));
        }
        keywordPairList.setUpdatedOn(timestamp);
        return keywordPairList;
    }
}
