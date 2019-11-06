package duke.data;

import duke.models.TimeSlot;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

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
    private String filePath = userDir + "\\src\\main\\java\\duke\\data\\savedData.json";

    /**
     * Method will load saved files.
     */
    @Override
    public void load() {
        JSONParser parser = new JSONParser();

        try {

            Object obj = parser.parse(new FileReader(filePath));

            JSONObject jsonObject = (JSONObject) obj;

            String name = (String) jsonObject.get("Name");
            String author = (String) jsonObject.get("Author");
            JSONArray companyList = (JSONArray) jsonObject.get("Second List");

            System.out.println("Name: " + name);
            System.out.println("Author: " + author);
            System.out.println("\nSecond List:");
            Iterator iterator = companyList.iterator();
            while (iterator.hasNext()) {
                System.out.println(iterator.next());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Method will save file to location.
     */
    @Override
    @SuppressWarnings("unchecked")
    public void save(TimeSlot timeSlot) throws IOException {
        JSONObject saveObj = new JSONObject();
        JSONArray company = new JSONArray();
        company.add(timeSlot);
        saveObj.put("Time Slots", company);
        FileWriter file = new FileWriter(filePath);
        try {
            file.write(saveObj.toJSONString());
            System.out.println("Successfully Copied JSON Object to File...");
            System.out.println("\nJSON Object: " + saveObj);
        } catch (IOException e) {
            System.err.println("file not found");
        } finally {
            file.flush();
            file.close();
        }
    }
}
