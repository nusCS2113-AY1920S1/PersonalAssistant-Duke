package duke.data;


import com.fasterxml.jackson.databind.ObjectMapper;
import duke.models.ToDo;
import duke.util.DateHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.Iterator;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Set;

/**
 * Class controls all the loading of data for the Schedule class.
 */
public class ScheduleStorage implements IStorage {
    /**
     * Location of the saved file that schedule will be using.
     */
    private String userDir = System.getProperty("user.dir");
    /**
     * Location of the saved file that schedule will be using.
     */
    private String filePath = userDir;

    /**
     * Method will load the .json file as a jsonObject for use.
     *
     * @return json object of the loaded file
     * @throws ParseException error that occurs in the event file cannot be parsed
     */
    public JSONObject initialize() throws ParseException {
        Object obj;
        JSONParser parser = new JSONParser();
        InputStream inputStream = getClass().getResourceAsStream("/savedData.json");
        String data = scanInputStream(inputStream);
        obj = parser.parse(data);
        JSONObject jsonObject = (JSONObject) obj;
        return jsonObject;
    }

    /**
     * Method will load saved files.
     *
     * @return
     */
    @Override
    public ArrayList<ToDo> load(String date) {
        ArrayList<ToDo> list = new ArrayList<>();
        try {
            JSONObject jsonObject = initialize();

            //get the array with the listed date
            JSONArray todoList = (JSONArray) jsonObject.get(date);

            //create an object to be added to the list array
            for (Object o : todoList) {
                if (o instanceof JSONObject) {
                    String name = (String) ((JSONObject) o).get("Name");
                    String startTime = (String) ((JSONObject) o).get("Start Time");
                    String endTime = (String) ((JSONObject) o).get("End Time");
                    String location = (String) ((JSONObject) o).get("Location");
                    ToDo toDo = new ToDo(startTime, endTime, location, name, "20191110");
                    list.add(toDo);
                }
            }

        } catch (NullPointerException | ParseException e) {
            //return empty list
        }
        return list;

    }

    /**
     * Method will create one JSon object save file to location.
     */
    @SuppressWarnings("unchecked")
    public JSONObject createJSonObject(ToDo todo) throws IOException {
        JSONObject saveObj = new JSONObject();
        //Define all details of one task in a day
        saveObj.put("Name", todo.getClassName());
        saveObj.put("Start Time", todo.getStartTime());
        saveObj.put("End Time", todo.getEndTime());
        saveObj.put("Location", todo.getLocation());
        saveObj.put("Status", todo.getStatus());
        return saveObj;
    }

    /**
     * Method will save file to location.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void save(ToDo toDo) throws IOException {
        JSONObject dateFile = new JSONObject();

        JSONArray allTasks = new JSONArray();
        for (int i = 0; i < 3; i++) {
            JSONObject tasks = createJSonObject(toDo);
            allTasks.add(tasks);
        }
        dateFile.put("20191105", allTasks);
        ObjectMapper mapper = new ObjectMapper();
        FileWriter file = new FileWriter(filePath);
        Object json = mapper.readValue(dateFile.toJSONString(), Object.class);
        mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
        try {
            file.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));
        } catch (IOException e) {
            System.err.println("file not found");
        } finally {
            file.flush();
            file.close();
        }
    }

    /**
     * Method takes buffer input stream and converts it to a string.
     *
     * @param inputStream The information to be converted
     * @return String of json file data
     */
    public String scanInputStream(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);
        StringBuffer sb = new StringBuffer();
        while (sc.hasNext()) {
            sb.append(sc.nextLine());
        }
        return sb.toString();
    }

    /**
     * Method will give an overview of all listed items.
     *
     * @return String array with all items
     */
    public ArrayList<String> loadOverview() {
        ArrayList<String> list = new ArrayList<>();
        try {
            JSONObject jsonObject = initialize();
            Set keySet = jsonObject.keySet();
            Iterator keySetIt = keySet.iterator();
            int index = 1;
            while (keySetIt.hasNext()) {
                String date = DateHandler
                    .dateFormatter(
                        "yyyy-MM-dd",
                        "dd MMM yyyy",
                        keySetIt.next().toString()
                    );
                String item = index++ + ". " + date;
                list.add(item);

            }
        } catch (ParseException e) {
            e.getMessage();
        }
        return list;
    }
}
