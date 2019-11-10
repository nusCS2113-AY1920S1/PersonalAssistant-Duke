package duke.data;


import com.fasterxml.jackson.databind.ObjectMapper;
import duke.models.ToDo;
import duke.util.DateHandler;
import org.apache.logging.log4j.core.appender.mom.JmsAppender;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URISyntaxException;
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
    private String path = userDir + "/savedData.json";
    /**
     * Location of the saved file that schedule will be using.
     */

    /**
     * Method will load the .json file as a jsonObject for use.
     *
     * @return json object of the loaded file
     * @throws ParseException error that occurs in the event file cannot be parsed
     */
    public JSONObject initialize() {
        JSONObject jsonObject = new JSONObject();
        try {
            Object obj;
            JSONParser parser = new JSONParser();
            InputStream inputStream = getClass().getResourceAsStream("/savedData.json");
            String data = scanInputStream(inputStream);
            obj = parser.parse(data);
            jsonObject = (JSONObject) obj;
        } catch (ParseException e) {
            System.err.println("Cannot load file");
        }
        return jsonObject;
    }

    /**
     * Method will load saved files that exist inside jar.
     *
     * @return list of items in jar file
     */
    @Override
    public ArrayList<ToDo> load(String date) {
        ArrayList<ToDo> list = new ArrayList<>();
        try {
            File file = new File(path);
            JSONObject jsonObject;
            if (file.exists()) {
                System.out.println("Loading from new file");
                JSONParser parser = new JSONParser();
                Object obj = parser.parse(new FileReader(path));
                jsonObject = (JSONObject) obj;
            } else {
                System.out.println("Loading from old file");
                jsonObject = initialize();
                ObjectMapper mapper = new ObjectMapper();
                FileWriter fileWriter = new FileWriter(path);
                fileWriter.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject));
                fileWriter.flush();
                fileWriter.close();
            }
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

        } catch (NullPointerException | FileNotFoundException e) {
            e.getLocalizedMessage();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
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
    public void save(ToDo toDo, String date) throws IOException {
        JSONObject dateFile = initialize();
        JSONArray dayTasks = new JSONArray();

        //get previous JSON object for day
        if (dateFile.get(date) == null) {
            System.err.println("Create new saved date");
        } else {
            dayTasks = (JSONArray) dateFile.get(date);
        }

        //add new object to the array
        JSONObject newTask = createJSonObject(toDo);
        dayTasks.add(newTask);
        // clear old array from save file
        dateFile.remove(date);

        //update file with new JSON array
        dateFile.put(date, dayTasks);
        ObjectMapper mapper = new ObjectMapper();
        try {
            FileWriter fileWriter = new FileWriter(path);
            Object json = mapper.readValue(dateFile.toJSONString(), Object.class);
            mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json);
            fileWriter.write(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json));
            fileWriter.flush();
            fileWriter.close();

        } catch (IOException e) {
            System.err.println("file not found");
        }
//        } catch (URISyntaxException e) {
//            e.getMessage();
//        }
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

        return list;
    }
}
