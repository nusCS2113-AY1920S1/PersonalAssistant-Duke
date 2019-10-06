package duke.task;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadJSONFile {
    public static void ReadJSONFile(){
        JSONParser parser = new JSONParser();

        try{
            JSONObject jsonObject = (JSONObject) parser.parse(new FileReader("myJSON.json"));
            String name = (String) jsonObject.get("name");
            System.out.println(name);

            //loop array
            JSONArray coursesArray = (JSONArray) jsonObject.get("courses");

        }
        catch(FileNotFoundException e) {
            System.out.println("SOMETHINGS WRONG NO FILE - CREATING ONE ");
        }
        catch(IOException e) {
            System.out.println("IOException ");
        }
        catch(ParseException e){
            System.out.println("ParseException ");
        }
        catch(Exception e){
            System.out.println("Some other shit");
        }

    }
}
