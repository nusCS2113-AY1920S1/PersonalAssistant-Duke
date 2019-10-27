package entertainment.pro.storage.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import entertainment.pro.model.MovieModel;
import entertainment.pro.model.UserProfile;
import entertainment.pro.storage.user.Blacklist;
import entertainment.pro.ui.MovieHandler;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Type;
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


    public EditProfileJson() throws IOException {
        file = new File("./userProfile.json");
        if (file.exists()) {
            this.inputStream = new FileInputStream(file);
        } else {
//            if (!file.getParentFile().exists()) {
//                file.getParentFile().mkdirs();
//            }
            file.createNewFile();
            UserProfile userProfile = null;
            mapper.writeValue(file, userProfile);
//            FileWriter fileWriter = new FileWriter(file);
//            fileWriter.write("{\"userName\":\"*undefined*\",\"userAge\":0,\"genreIdPreference\":[],\"genreIdRestriction\":[],\"adult\":true,\"playlistNames\":[],\"sortByAlphabetical\":false,\"sortByLatestRelease\":false,\"sortByHighestRAting\":false}");
            this.inputStream = new FileInputStream(file);
        }
    }

    public UserProfile load() throws IOException {
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
        ArrayList<String> emptyPlaylist = new ArrayList<>();
        long age = (long) jsonObject.get("age");
        int ageToInt = Math.toIntExact(age);
        return new UserProfile((String)jsonObject.get("name"),
                ageToInt, genrePreference, genreRestriction,
                (boolean) jsonObject.get("adult"), emptyPlaylist , (boolean)jsonObject.get("sortByAlphabetical"),
                (boolean) jsonObject.get("sortByHighestRating"), (boolean)jsonObject.get("sortByLatestRelease"));

    }

    /**
     * update json file with any changes made to user profile
     */
    public void updateProfile(UserProfile userProfile) throws IOException {
        System.out.println("this is");
        mapper.writeValue(file, userProfile);
    }
}

