import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Iterator;

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
    public Farmer loadFarmer() throws ParseException, IOException {
        Reader reader;
        reader = new FileReader(appDir.concat("\\data\\save.json"));
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(reader);
        //TODO: Get game save.
//        Examples:
//        String name = (String) jsonObject.get("name");
//
//        long age = (Long) jsonObject.get("age");
//
//        JSONArray msg = (JSONArray) jsonObject.get("messages");
//        Iterator<String> iterator = msg.iterator();
//        while (iterator.hasNext()) {
//            System.out.println(iterator.next());
//        }
        return new Farmer();
    }

    /**
     * Stores farmer object into "save.json".
     * @param farmer Farmer object to be stored in to save file.
     * @throws IOException Fail to save farmer object into save file.
     */
    public static void storeFarmer(Farmer farmer) throws IOException {
        //TODO: Put farmer into json object and write it to file.

//        Example:
//        JSONObject obj = new JSONObject();
//        obj.put("name", "mkyong.com");
//        obj.put("age", 100);
//
//        JSONArray list = new JSONArray();
//        list.add("msg 1");
//        list.add("msg 2");
//        list.add("msg 3");
//
//        obj.put("messages", list);

        JSONObject obj = new JSONObject();
        FileWriter file = new FileWriter(appDir.concat("\\data\\save.json"));
        file.write(obj.toJSONString());
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
}
