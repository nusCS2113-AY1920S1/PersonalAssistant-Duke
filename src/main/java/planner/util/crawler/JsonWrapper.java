//@@author namiwa

package planner.util.crawler;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;

import planner.logic.exceptions.legacy.ModInvalidTimeException;
import planner.logic.exceptions.planner.ModBadRequestStatus;
import planner.logic.exceptions.planner.ModFailedJsonException;
import planner.logic.modules.module.ModuleInfoDetailed;
import planner.logic.modules.module.ModuleInfoSummary;
import planner.logic.modules.module.ModuleTask;
import planner.util.datetime.NattyWrapper;
import planner.util.logger.PlannerLogger;
import planner.util.storage.Storage;

public class JsonWrapper {

    private Gson gson;
    private NattyWrapper natty;
    private RequestData requestsData;
    private final String listFile = "data/modsListData.json";
    private final String listDetailedFile = "data/modsDetailedListData.json";
    private final String userModuleFile = "data/userData.json";
    private final String academicYear = "2019-2020";

    public enum Requests {
        DETAILED, SUMMARY
    }

    /**
     * Constructor for JsonWrapper to access module information.
     */
    public JsonWrapper() {
        gson = new Gson();
        natty = new NattyWrapper();
        requestsData = new RequestData();
    }

    public Date parseStringToDate(String input) throws ModInvalidTimeException {
        return natty.runParser(input);
    }

    public LocalDateTime parseStringToLocalDateTime(String input) throws ModInvalidTimeException {
        return natty.dateToLocalDateTime(input);
    }


    private HashMap<String, ModuleInfoDetailed> getMapFromList(List<ModuleInfoDetailed> modsList) {
        HashMap<String, ModuleInfoDetailed> ret = new HashMap<>();
        for (ModuleInfoDetailed temp : modsList) {
            String modCode = temp.getModuleCode();
            ret.put(modCode, temp);
        }
        return ret;
    }

    /**
     * For each data set, request for nusMods API.
     */
    public void runRequests(Storage store) throws ModBadRequestStatus {
        storeJson(Requests.DETAILED, store);
    }

    /**
     * Updating detailed module list file in data folder.
     * @param academicYear Academic Year input by user.
     * @param store Storage object to write files.
     * @throws ModBadRequestStatus If the user's status return from API call is not 200 (success).
     */
    public void runRequests(String academicYear, Storage store) throws ModBadRequestStatus {
        store.setDataPath(Paths.get(listDetailedFile));
        if (store.getDataPathExists()) {
            return;
        }
        requestsData.storeModData(requestsData.requestModuleListDetailed(academicYear), store);
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
                throw new ModBadRequestStatus();
            }
        }
    }


    /**
     * Reads the Json file for to be parsed into a java object. Since the data is
     * presented in a JSON array, our class object class would need to be wrapped
     * in an array as well.
     * @return List of ModuleInfoSummary Objects, null if it fails to parse.
     */
    private List<ModuleInfoSummary> getModuleListObject() {
        try {
            JsonReader reader = new JsonReader(new FileReader(listFile));
            Type listType = new TypeToken<List<ModuleInfoSummary>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            PlannerLogger.log(e);
        } catch (IOException ei) {
            System.out.println(Arrays.toString(ei.getStackTrace()));
            PlannerLogger.log(ei);
        }
        return null;
    }

    /**
     * Main helper function to obtained HashMap of summary info from modsListData.json.
     * @return HashMap with module code as the key and ModuleInfoSummary object as the value.
     * @throws ModFailedJsonException If the previous call to getModuleListObject() returns null.
     */
    public HashMap<String, ModuleInfoSummary> getModuleSummaryMap() throws ModFailedJsonException {
        List<ModuleInfoSummary> modsList = getModuleListObject();
        if (modsList == null) {
            throw new ModFailedJsonException();
        }
        HashMap<String, ModuleInfoSummary> ret = new HashMap<>();
        for (ModuleInfoSummary temp : modsList) {
            String modCode = temp.getModuleCode();
            ret.put(modCode, temp);
        }
        return ret;
    }

    /**
     * Converts the stored json file into a list of ModuleInfoDetailed objects.
     * @return a list of ModuleInfoDetailed objects, null if it fails to parse.
     */
    private List<ModuleInfoDetailed> getModuleListDetailedObject() {
        try {
            JsonReader reader = new JsonReader(new FileReader(listDetailedFile));
            Type listType = new TypeToken<List<ModuleInfoDetailed>>(){}.getType();
            return gson.fromJson(reader, listType);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            PlannerLogger.log(e);
        } catch (IOException ei) {
            System.out.println(Arrays.toString(ei.getStackTrace()));
            PlannerLogger.log(ei);
        }
        return new ArrayList<>();
    }

    /**
     * Overloaded function to generate runtime file from resources instead of query file from NUSMODS.
     * @param set Flag to run code.
     * @param store Storage Object for file check.
     * @return HashMap of Module code mapped to corresponding ModuleInfoDetailed.
     * @throws ModFailedJsonException If the user's status return from API call is not 200 (success).
     */
    public HashMap<String, ModuleInfoDetailed> getModuleDetailedMap(boolean set, Storage store)
            throws ModFailedJsonException {
        if (set && store.getDataPathExists()) {
            return getModuleDetailedMap();
        } else {
            InputStream in = this.getClass().getResourceAsStream("/data/modsDetailedListData.json");
            Type listType = new TypeToken<List<ModuleInfoDetailed>>(){}.getType();
            InputStreamReader inputStreamReader = new InputStreamReader(in, StandardCharsets.UTF_8);
            List<ModuleInfoDetailed> modsList = gson.fromJson(inputStreamReader, listType);
            return getMapFromList(modsList);
        }
    }

    /**
     * Main helper function to obtained HashMap of detailed info from modsDetailedListData.json.
     * @return HashMap with module code as the key and ModuleInfoSummary object as the value.
     * @throws ModFailedJsonException If the previous call to getModuleListObject() returns null.
     */
    public HashMap<String, ModuleInfoDetailed> getModuleDetailedMap() throws ModFailedJsonException {
        List<ModuleInfoDetailed> modsList = getModuleListDetailedObject();
        if (modsList.size() == 0) {
            throw new ModFailedJsonException();
        }
        return getMapFromList(modsList);
    }


    /**
     * Stores the current state of the taskList into a json file.
     * @param tasksList List of module tasks.
     * @param store object which handles file storing.
     */
    public void storeTaskListAsJson(List<ModuleTask> tasksList, Storage store) {
        String jsonString = gson.toJson(tasksList);
        List<String> stringsList = requestsData.getResponseList(jsonString);
        store.setDataPath(Paths.get(userModuleFile));
        store.writeModsData(stringsList);
    }

    /**
     * Returns taskList after reading json file.
     * @return List of tasks of the read was successful, null if otherwise.
     */
    public List<ModuleTask> readJsonTaskList(Storage store) {
        try {
            store.setDataPath(Paths.get(userModuleFile));
            if (store.getDataPathExists()) {
                JsonReader reader = new JsonReader(new FileReader(userModuleFile));
                Type listType = new TypeToken<List<ModuleTask>>() {}.getType();
                return gson.fromJson(reader, listType);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
            PlannerLogger.log(e);
        } catch (IOException ei) {
            System.out.println(Arrays.toString(ei.getStackTrace()));
            PlannerLogger.log(ei);
        }
        return new ArrayList<>();
    }

}
