package EPstorage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.regex.Pattern;


/**
 * class that contains all methods that deal with Profile object
 */
public class ProfileCommands {
    private File genreList;
    private UserProfile userProfile;
    private EditProfileJson editProfileJson;

    public ProfileCommands(UserProfile userProfile) throws FileNotFoundException {
        genreList = new File("/Users/wenhui/main/EPdata/genreIDlist.txt");
        this.userProfile = userProfile;
        this.editProfileJson = new EditProfileJson();
    }

    /**
     * change name in profile
     */
    public void setName(String name) throws IOException {
        userProfile.setUserName(name);
        editProfileJson.updateProfile(userProfile);
    }

    /**
     * change age in profile
     */
    public void setAge(String age) throws IOException {
        userProfile.setUserAge(Integer.parseInt(age));
        editProfileJson.updateProfile(userProfile);
    }

    /**
     * set user preferences
     */
    public void setPreference(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        ArrayList<Integer> genrePreferences = new ArrayList<>(10);
        for (String log : flagMap.get("-g")){
            genrePreferences.add(findGenreID(log));
        }
        userProfile.setGenreId(genrePreferences);
        editProfileJson.updateProfile(userProfile);
    }

    public void addPreference(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        ArrayList<Integer> genrePreferences = new ArrayList<>(50);
        for (String log : flagMap.get("-g")){
            genrePreferences.add(findGenreID(log));
        }
        userProfile.addGenreId(genrePreferences);
        editProfileJson.updateProfile(userProfile);
        }

    public void removePreference(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        ArrayList<Integer> genrePreferences = new ArrayList<>(50);
        for (String log : flagMap.get("-g")){
            genrePreferences.add(findGenreID(log));
        }
        userProfile.removeGenreId(genrePreferences);
        editProfileJson.updateProfile(userProfile);
    }

    public Integer findGenreID(String genreName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = new FileInputStream("EPdata/GenreId.json");
        TypeReference<ArrayList<GenreId>> typeReference = new TypeReference<ArrayList<GenreId>>() {};
        ArrayList<GenreId> genreIds = mapper.readValue(inputStream, typeReference);
        for (GenreId log : genreIds){
            if (log.getGenre().equalsIgnoreCase(genreName)){
                inputStream.close();
                return log.getId();
            }
        }
        inputStream.close();
        return 0;
    }

    public String findGenreName(int ID) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream inputStream = new FileInputStream("EPdata/GenreId.json");
        TypeReference<ArrayList<GenreId>> typeReference = new TypeReference<ArrayList<GenreId>>() {};
        ArrayList<GenreId> genreIds = mapper.readValue(inputStream, typeReference);
        for (GenreId log : genreIds){
            if (log.getId() == ID){
                inputStream.close();
                return log.getGenre();
            }
        }
        inputStream.close();
        return "0";
    }

    public String convertToLabel(ArrayList<Integer> userList) throws IOException {
        String labelText = "";
        for (Integer log : userList){
            labelText += findGenreName(log);
            labelText += "\n";
        }
        return labelText;
    }


}