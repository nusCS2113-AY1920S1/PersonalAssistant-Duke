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
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * class that deals with editing the UserProfile.json file.
 */
public class EditProfileJson {
    private ObjectMapper mapper = new ObjectMapper();
    private File file;
    private InputStream inputStream;
    private TypeReference<UserProfile> typeReference = new TypeReference<UserProfile>() {
    };

    /**
     * constructor for EditProfileJson.
     */
    public EditProfileJson() throws IOException {
        file = new File("./userProfile.json");
        if (file.exists()) {
            this.inputStream = new FileInputStream(file);
        } else {
            file.createNewFile();
//            UserProfile userProfile = null;
            UserProfile userProfile = new UserProfile();
            mapper.writeValue(file, userProfile);
            this.inputStream = new FileInputStream(file);
        }
    }

    /**
     * to load UserProfile object from userProfile.json.
     */
    public UserProfile load() throws IOException {
//        InputStream inputStream = getClass().getResourceAsStream("./userProfile.json");
        InputStream inputStream = new FileInputStream("./userProfile.json");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String userProfileString = "";
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            userProfileString += line;
        }
        bufferedReader.close();
        inputStreamReader.close();
        inputStream.close();

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = (JSONObject) parser.parse(new FileReader("./userProfile.json"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        UserProfile userProfile = new UserProfile();
        parse(userProfileString, userProfile);
        return userProfile;
//        return mapper.readValue(inputStream, typeReference);
    }


    private void parse(String userProfileString, UserProfile userProfile) {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = (JSONObject) parser.parse(userProfileString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final String name = (String) jsonObject.get("userName");
        final int age = ((Long) jsonObject.get("userAge")).intValue();
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
        final boolean adult = (boolean) jsonObject.get("adult");
        final ArrayList<String> playlistNames = (ArrayList<String>) jsonObject.get("playlistNames");
        final boolean highestRating = (boolean) jsonObject.get("sortByHighestRating");
        final boolean latestRelease = (boolean) jsonObject.get("sortByLatestRelease");
        final boolean alphabeticalOrder = (boolean) jsonObject.get("sortByAlphabetical");
        userProfile.setUserName(name);
        userProfile.setUserAge(age);
        userProfile.setGenreIdPreference(genrePreference);
        userProfile.setGenreIdRestriction(genreRestriction);
        userProfile.setPlaylistNames(playlistNames);
        userProfile.setAdult(adult);
        userProfile.setSortByAlphabetical(alphabeticalOrder);
        userProfile.setSortByHighestRating(highestRating);
        userProfile.setSortByLatestRelease(latestRelease);
    }

    /**
     * update json file with any changes made to user profile.
     */
    public void updateProfile(UserProfile userProfile) throws IOException {
        System.out.println("this is");
        mapper.writeValue(file, userProfile);
    }
}