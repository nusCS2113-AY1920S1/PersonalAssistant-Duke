package duke.data;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

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
     * Method will load saved files.
     *
     * @return
     */
    @Override
    public ArrayList<ToDo> load(String date) {
        ArrayList<ToDo> list = new ArrayList<>();
        JSONParser parser = new JSONParser();
        Object obj;
        try {
            InputStream inputStream = getClass().getResourceAsStream("/savedData.json");
            String data = scanInputStream(inputStream);
            obj = parser.parse(data);
            JSONObject jsonObject = (JSONObject) obj;
            JSONArray todoList = (JSONArray) jsonObject.get("20191110");
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
        //add the task to the array object
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
}
