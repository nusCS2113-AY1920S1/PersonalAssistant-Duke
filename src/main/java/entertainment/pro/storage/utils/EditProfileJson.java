package entertainment.pro.storage.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entertainment.pro.model.UserProfile;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

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
     * Constructor for EditProfileJson.
     */
    public EditProfileJson() throws IOException {
        file = new File("./userProfile.json");
        if (file.exists()) {
            this.inputStream = new FileInputStream(file);
        } else {
            file.createNewFile();
            UserProfile userProfile = new UserProfile();
            mapper.writeValue(file, userProfile);
            this.inputStream = new FileInputStream(file);
        }
    }

    /**
     * Responsible for loading UserProfile object from userProfile.json.
     */
    public UserProfile load() throws IOException {
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
        return parse(userProfileString);
    }


    private UserProfile parse(String userProfileString) {
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
        UserProfile userProfile = new UserProfile();
        userProfile = userProfile.setUserName(name);
        userProfile = userProfile.setUserAge(age);
        userProfile = userProfile.setGenreIdPreference(genrePreference);
        userProfile = userProfile.setGenreIdRestriction(genreRestriction);
        userProfile = userProfile.setPlaylistNames(playlistNames);
        userProfile = userProfile.setAdult(adult);
        userProfile = userProfile.setSortByAlphabetical(alphabeticalOrder);
        userProfile = userProfile.setSortByHighestRating(highestRating);
        userProfile = userProfile.setSortByLatestRelease(latestRelease);
        return userProfile;
    }

    /**
     * update json file with any changes made to user profile.
     */
    public void updateProfile(UserProfile userProfile) throws IOException {
        System.out.println("this is");
        mapper.writeValue(file, userProfile);
    }
}