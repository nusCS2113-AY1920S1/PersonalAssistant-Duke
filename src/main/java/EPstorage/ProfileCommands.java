package EPstorage;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.TreeMap;


/**
 * class that contains all methods that deal with Profile object
 */
public class ProfileCommands {
    private File genreList;
    private UserProfile userProfile;
    private EditProfileJson editProfileJson;

    public ProfileCommands(UserProfile userProfile) throws FileNotFoundException {
        genreList = new File("../../../../EPdata/genreIDlist.txt");
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
    public void setGenrePreference(TreeMap<String, ArrayList<String>> flagMap) throws IOException {
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
    public void setAdultPreference(String value) throws IOException {
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
    public void setAll(TreeMap<String, ArrayList<String>> flagMap, String value) throws IOException {
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

    public void clearGenrePreference() throws IOException {
        userProfile.removeGenreIdPreference(userProfile.getGenreIdPreference());
        editProfileJson.updateProfile(userProfile);
    }

    public void clearAdultPreference() throws IOException {
        userProfile.setAdult(true);
        editProfileJson.updateProfile(userProfile);
    }

    public void clearAll() throws IOException {
        userProfile.removeGenreIdPreference(userProfile.getGenreIdPreference());
        userProfile.setAdult(true);
        editProfileJson.updateProfile(userProfile);
    }

    public Integer findGenreID(String genreName) throws IOException {
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

    public String getAdultLabel() {
        if (userProfile.isAdult()) {
            return "allow";
        } else {
            return "restrict";
        }
    }
}