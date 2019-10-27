package entertainment.pro.storage.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entertainment.pro.model.UserProfile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * class that deals with editing the UserProfile.json file
 */
public class EditProfileJson {
    private ObjectMapper mapper = new ObjectMapper();
    private File file;
    private InputStream inputStream;
    private TypeReference<UserProfile> typeReference = new TypeReference<UserProfile>() {
    };


    public EditProfileJson() throws FileNotFoundException {
        file = new File("EPdata/userProfile.json");
        this.inputStream = new FileInputStream(file);
    }

    public UserProfile load() throws IOException {
       // Scanner scanner = new Scanner( new File(fileName) );
        //String jsonString = scanner.useDelimiter("\\A").next();
        //scanner.close();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        try {

            jsonObject = (JSONObject) parser.parse(new FileReader("EPdata/userProfile.json"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        UserProfile userProfile;
        ArrayList<Integer> genrePreference = new ArrayList<>();
        ArrayList<Integer> genreRestriction = new ArrayList<>();
        JSONArray jsonArray = (JSONArray) jsonObject.get("genreIdPreference");
        JSONArray jsonArray1 = (JSONArray) jsonObject.get("genreIdRestriction");
        for (int i = 0; i < jsonArray.size(); i += 1) {
            Long num = (Long) jsonArray.get(i);
            int num2 = num.intValue();
            genrePreference.add(num2);
        }
        for (int i = 0; i < jsonArray1.size(); i += 1) {
            Long num = (Long) jsonArray1.get(i);
            int num2 = num.intValue();
            genreRestriction.add(num2);
        }
        //ArrayList<Integer> arrayList = (ArrayList<Integer>) jsonObject.get("genreIdPreference");
        boolean isRating = (boolean) jsonObject.get("sortByHighestRating");
        //boolean isDates = (boolean) jsonObject.get("sortByLatestDates");
        return new UserProfile(genrePreference, genreRestriction,
                (boolean) jsonObject.get("adult"), (boolean)jsonObject.get("sortByAlphabetical"),
                (boolean) jsonObject.get("sortByHighestRating"), (boolean)jsonObject.get("sortByLatestRelease"));

    }

    /**
     * update json file with any changes made to user profile
     */
    public void updateProfile(UserProfile userProfile) throws IOException {
       // File oldFile = file;
        //File newFile = new File("EPdata/userProfile.json");
        mapper.writeValue(file, userProfile);
        inputStream.close();
        //oldFile.delete();
        //newFile.renameTo(new File(file.getAbsolutePath()));
    }
}
