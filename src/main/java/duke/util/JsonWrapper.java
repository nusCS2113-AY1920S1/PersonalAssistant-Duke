package duke.util;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import duke.exceptions.ModBadRequestStatus;

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
     */
    public JsonWrapper() {
        gson = new Gson();
        requestsData = new RequestsData();
    }

    /**
     * For each data set, request for nusMods API.
     */
    public void runRequests(Storage store) throws ModBadRequestStatus {
        for (Requests req : Requests.values()) {
            storeJson(req, store);
        }
    }

    private void storeJson(Requests type, Storage store) throws ModBadRequestStatus {
        switch (type) {
            case SUMMARY: {
                store.setDataPath(Paths.get(listFile));
                if (store.getDataPathExists()) {
                    break;
                }
                requestsData.storeModData(requestsData.requestModuleList(academicYear), store);
                break;
            }
            case DETAILED: {
                store.setDataPath(Paths.get(listDetailedFile));
                if (store.getDataPathExists()) {
                    break;
                }
                requestsData.storeModData(requestsData.requestModuleListDetailed(academicYear), store);
                break;
            }
            default: {
                throw new IllegalStateException("Unexpected value: " + type);
            }
        }
    }


    //TODO: This function would return a string list of
    //      all modules in NUS in this academic year.

    /**
     * Reads the Json file for to be parsed into a java object. Since the data is
     * presented in a JSON array, our class object class would need to be wrapped
     * in an array as well.
     */
    private void readJson() {
        try {
            JsonReader reader = new JsonReader(new FileReader(listFile));
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);

        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException ei) {
            System.out.println(Arrays.toString(ei.getStackTrace()));
        }
    }
}
