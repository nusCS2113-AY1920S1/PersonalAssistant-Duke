package entertainment.pro.storage.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import entertainment.pro.model.GenreId;
import entertainment.pro.model.UserProfile;
import entertainment.pro.storage.utils.EditProfileJson;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;


/**
 * class that contains all methods that deal with Profile object
 */
public class ProfileCommands {
//    private File genreList;
    private UserProfile userProfile;
    private EditProfileJson editProfileJson;

    public ProfileCommands(UserProfile userProfile) throws IOException {
//        genreList = new File("../../../../EPdata/genreIDlist.txt");
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
        if (flagMap.containsKey("-g")) {
            setGenrePreference(flagMap);
        }
        if (flagMap.containsKey("-a")) {
            setAdultPreference(flagMap.get("-a").get(0));
        }
        if (flagMap.containsKey("-g") && flagMap.containsKey("-a")) {
            setAll(flagMap, flagMap.get("-a").get(0));
        }
    }

    /**
     * set user preferences -- for genre
     */
    private void setGenrePreference(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        ArrayList<Integer> genrePreferences = new ArrayList<>(10);
        for (String log : flagMap.get("-g")) {
            genrePreferences.add(findGenreID(log));
        }
        userProfile.setGenreIdPreference(genrePreferences);
        editProfileJson.updateProfile(userProfile);
    }

    /**
     * set user preferences -- for adult content restriction
     * yes = allow adult content
     * no = restrict adult content
     */
    private void setAdultPreference(String value) throws IOException {
        if (value.equals("yes")) {
            userProfile.setAdult(true);
        } else if (value.equals("no")) {
            userProfile.setAdult(false);
        }
        editProfileJson.updateProfile(userProfile);
    }

    /**
     * to allow setting of both genre and adult content restriction preferences at the same time
     */
    private void setAll(TreeMap<String, ArrayList<String>> flagMap, String value) throws IOException {
        ArrayList<Integer> genrePreferences = new ArrayList<>(10);
        for (String log : flagMap.get("-g")) {
            genrePreferences.add(findGenreID(log));
        }
        userProfile.setGenreIdPreference(genrePreferences);
        if (value.equals("yes")) {
            userProfile.setAdult(true);
        } else if (value.equals("no")) {
            userProfile.setAdult(false);
        }
        editProfileJson.updateProfile(userProfile);
    }

    /**
     * set user restrictions
     */
    public void setRestriction(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        ArrayList<Integer> genreRestrictions = new ArrayList<>(10);
        for (String log : flagMap.get("-g")) {
            genreRestrictions.add(findGenreID(log));
        }
        userProfile.setGenreIdRestriction(genreRestrictions);
        editProfileJson.updateProfile(userProfile);
    }

    public void addRestriction(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        ArrayList<Integer> genreRestrictions = new ArrayList<>(50);
        for (String log : flagMap.get("-g")){
            genreRestrictions.add(findGenreID(log));
        }
        userProfile.addGenreIdRestriction(genreRestrictions);
        editProfileJson.updateProfile(userProfile);
    }

    public void removeRestriction(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        ArrayList<Integer> genreRestrictions = new ArrayList<>(50);
        for (String log : flagMap.get("-g")){
            genreRestrictions.add(findGenreID(log));
        }
        userProfile.removeGenreIdRestriction(genreRestrictions);
        editProfileJson.updateProfile(userProfile);
    }

    public void clearRestriction(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        userProfile.removeGenreIdRestriction(userProfile.getGenreIdRestriction());
        editProfileJson.updateProfile(userProfile);
    }

    public void addPreference(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        ArrayList<Integer> genrePreferences = new ArrayList<>(50);
        for (String log : flagMap.get("-g")){
            genrePreferences.add(findGenreID(log));
        }
        userProfile.addGenreIdPreference(genrePreferences);
        editProfileJson.updateProfile(userProfile);
        }

    public void removePreference(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        ArrayList<Integer> genrePreferences = new ArrayList<>(50);
        for (String log : flagMap.get("-g")){
            genrePreferences.add(findGenreID(log));
        }
        userProfile.removeGenreIdPreference(genrePreferences);
        editProfileJson.updateProfile(userProfile);
    }

    public void clearPreference(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
        if (flagMap.containsKey("-g")) {
            clearGenrePreference();
        }
        if (flagMap.containsKey("-a")) {
            clearAdultPreference();
        }
        if (flagMap.containsKey("-g") && flagMap.containsKey("-a")) {
            clearAll();
        }
    }

    private void clearGenrePreference() throws IOException {
        userProfile.removeGenreIdPreference(userProfile.getGenreIdPreference());
        editProfileJson.updateProfile(userProfile);
    }

    private void clearAdultPreference() throws IOException {
        userProfile.setAdult(true);
        editProfileJson.updateProfile(userProfile);
    }

    private void clearAll() throws IOException {
        userProfile.removeGenreIdPreference(userProfile.getGenreIdPreference());
        userProfile.setAdult(true);
        editProfileJson.updateProfile(userProfile);
    }

    private Integer findGenreID(String genreName) throws IOException {
        genreName = genreName.trim();
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

    private String findGenreName(int ID) throws IOException {
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

    public String getAdultLabel() {
        if (userProfile.isAdult()) {
            return "allow";
        } else {
            return "restrict";
        }
    }

    public void addPlaylist(String listName) throws IOException {
        userProfile.addPlaylist(listName);
        editProfileJson.updateProfile(userProfile);
    }

    public void deletePlaylist(String listName) throws IOException {
        userProfile.deletePlaylist(listName);
        editProfileJson.updateProfile(userProfile);
    }

    public void renamePlaylist(String oldName, String newName) throws IOException {
        ArrayList<String> playlistNames = userProfile.getPlaylistNames();
        ArrayList<String> newPlaylistNames = new ArrayList<>();
        for (String log : playlistNames) {
            if (!log.equals(oldName)) {
                newPlaylistNames.add(log);
            } else {
                newPlaylistNames.add(newName);
            }
        }
        userProfile.setPlaylistNames(newPlaylistNames);
        editProfileJson.updateProfile(userProfile);
    }
}