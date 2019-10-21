package retractCommands;

import EPstorage.PastCommands;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import object.PastCommandStructure;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import sort.SortProfile;

import java.io.*;
import java.lang.reflect.Array;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class PastUserCommands {

    public static void update(PastCommands pastCommands) {
        JSONParser parser = new JSONParser();
        JSONArray jsonArray = new JSONArray();
        ArrayList<PastCommandStructure> arrayList = pastCommands.getMap();
        for (int i = 0; i < arrayList.size(); i += 1) {
            PastCommandStructure pastCommandStructure = arrayList.get(i);
            String date = pastCommandStructure.getDate();
            String query = pastCommandStructure.getQuery();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("date", date);
            jsonObject.put("query", query);
            jsonArray.add(jsonObject);
        }
        //jsonArray.addAll(arrayList);
        File fileToSaveJson = new File("data/pastCommands.json");
        byte[] storeArray = jsonArray.toString().getBytes();
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(fileToSaveJson));
            bos.write(storeArray);
            bos.flush();
            bos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<PastCommandStructure> load() throws IOException, ParseException {
        ArrayList<PastCommandStructure> arrayList = new ArrayList<PastCommandStructure>();
            JSONParser parser = new JSONParser();
            JSONArray jsonArray = (JSONArray) parser.parse(new FileReader("data/pastCommands.json"));
            if (jsonArray == null) {
                System.out.println("thisss");
                return arrayList;
            } else {
                for (int i = 0; i < jsonArray.size(); i += 1) {
                    JSONObject jsonObject = ((JSONObject) jsonArray.get(i));
                    String date = (String) jsonObject.get("date");
                    String query = (String) jsonObject.get("query");
                    PastCommandStructure pastCommandStructure = new PastCommandStructure(date, query);
                    arrayList.add(pastCommandStructure);
                }
                return arrayList;
            }
    }
}
