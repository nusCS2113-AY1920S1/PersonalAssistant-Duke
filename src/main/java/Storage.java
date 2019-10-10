import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class Storage {

    private static String appDir;

    public Storage(){
        appDir = System.getProperty("user.dir");
    }

    public boolean getFarmerExist(){
        return new File(appDir.concat("\\data\\save.json")).exists();
    }

    /**
     * Finds and return previously saved game data.
     * @return Last saved farmer object.
     * @throws ParseException "save.json" does not contain json data.
     * @throws IOException "save.json" does not exist.
     */
    public JSONObject loadFarmer() throws ParseException, IOException {
        Reader reader;
        reader = new FileReader(appDir.concat("\\data\\save.json"));
        JSONParser parser = new JSONParser();
        return (JSONObject) parser.parse(reader);
    }

    /**
     * Stores farmer object into "save.json".
     * @param farmer Farmer object to be stored in to save file.
     * @throws IOException Fail to save farmer object into save file.
     */
    public static void storeFarmer(Farmer farmer) throws IOException {
        FileWriter file = new FileWriter(appDir.concat("\\data\\save.json"));
        file.write(farmer.toJSON().toJSONString());
    }

    public String getAsciiArt(String name) throws IOException {
        StringBuilder art = new StringBuilder();
        FileReader fileReader = new FileReader("./src/main/resources/asciiArt/" + name + ".txt");
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            art.append(line).append("\n");
        }
        return art.toString();
    }

    public String getLevel(int level) throws IOException{
            //runs level

    }
}
