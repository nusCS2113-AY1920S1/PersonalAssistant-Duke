package EPstorage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;


/**
 * commands for storage
 */
public class Commands {
    private File genreList;
    private UserProfile userProfile;
    private EditProfileJson editProfileJson;

    public Commands(UserProfile userProfile) throws FileNotFoundException {
        genreList = new File("/Users/wenhui/main/EPdata/genreIDlist.txt");
        this.userProfile = userProfile;
        this.editProfileJson = new EditProfileJson();
    }

    public void setName(String name) throws IOException {
        userProfile.setUserName(name);
        editProfileJson.updateProfile(userProfile);
    }

    public void setAge(String age) throws IOException {
        userProfile.setUserAge(Integer.parseInt(age));
        editProfileJson.updateProfile(userProfile);
    }

    public void setPreference(String preferences) throws IOException {
        String tokens[] = preferences.split(Pattern.quote("-"));
        ArrayList<Integer> genrePreferences = new ArrayList<>(50);
        for (String log : tokens){
            if (log.length() > 0){
                if (log.charAt(0) == 'g') {
                    log = log.substring(2).trim();
                    genrePreferences.add(findGenreID(log));
                }
            }
        }
        userProfile.setGenreId(genrePreferences);
        editProfileJson.updateProfile(userProfile);
    }

    public void addPreference(String preferences) throws IOException {
        String tokens[] = preferences.split(Pattern.quote("-"));
        ArrayList<Integer> genrePreferences = new ArrayList<>(50);
        for (String log : tokens){
            if (log.length() > 0){
                if (log.charAt(0) == 'g') {
                    log = log.substring(2).trim();
                    genrePreferences.add(findGenreID(log));
                }
            }
        }
        userProfile.addGenreId(genrePreferences);
        editProfileJson.updateProfile(userProfile);
        }

    public void removePreference(String preferences) throws IOException {
        String tokens[] = preferences.split(Pattern.quote("-"));
        ArrayList<Integer> genrePreferences = new ArrayList<>(50);
        for (String log : tokens){
            if (log.length() > 0){
                if (log.charAt(0) == 'g') {
                    log = log.substring(2).trim();
                    genrePreferences.add(findGenreID(log));
                }
            }
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