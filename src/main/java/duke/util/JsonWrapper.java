package duke.util;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.Arrays;

public class JsonWrapper {

    private Gson gson;
    private RequestsData requestsData;
    private final String listFile = "data/modsListData.json";
    private final String listDetailedFile = "data/modsDetailedListData.json";
    private final String academicYear = "2019-2020";

    public enum Requests {
        DETAILED, SUMMARY
    }

    /**
     * Constructor for JsonWrapper to access module information.
     * @param store Storage class which handles file writing.
     */
    public JsonWrapper(Storage store) {
        gson = new Gson();
        requestsData = new RequestsData();
        runRequests(store);
    }

    private void runRequests(Storage store) {
        for (Requests req : Requests.values()) {
            storeJson(store, req);
        }
    }

    private void storeJson(Storage store, Requests type) {
        switch (type) {
            case SUMMARY: {
                store.setDataPath(listFile);
                if (store.getDataPathExists()) {
                    return;
                }
                requestsData.storeModData(requestsData.requestModuleList(academicYear), store);
                break;
            }
            case DETAILED: {
                store.setDataPath(listDetailedFile);
                if (store.getDataPathExists()) {
                    return;
                }
                requestsData.storeModData(requestsData.requestModuleListDetailed(academicYear), store);
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + type);
            }
        }
    }

    private void readJson(Path filePath) {
        try {
            JsonReader reader = new JsonReader(new FileReader(listFile));
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            System.out.println(jsonObject);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException ei) {
            System.out.println(Arrays.toString(ei.getStackTrace()));
        }
    }
}
