package EPstorage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Commands {
    private File genreList;
    private ProfileStorage profileStorage;
    private UserProfile userProfile;

    public Commands()  {
        genreList = new File("/Users/wenhui/main/EPdata/genreIDlist.txt");
        profileStorage = new ProfileStorage();
        userProfile = new UserProfile();
    }

    public void setName(String name) throws IOException {
        profileStorage.changeName(name);
        userProfile.setUserName(name);
    }

    public void setAge(String age) throws IOException {
        profileStorage.changeAge(age);
        userProfile.setUserAge(Integer.parseInt(age));
    }

    public void setPreference(String preferences) throws IOException {
        String tokens[] = preferences.split(Pattern.quote("-"));
        ArrayList<Integer> genrePreferences = new ArrayList<>(50);
        for (String log : tokens){
            if (log.length() > 0){
                if (log.charAt(0) == 'g') {
                    log = log.substring(2).trim();
                    genrePreferences.add(Integer.parseInt(findGenreID(log)));
                }
            }
        }
        profileStorage.changeGenre(genrePreferences);
        userProfile.setGenreId(genrePreferences);
    }

    public String findGenreID(String genreName) throws FileNotFoundException {
        Scanner scan = new Scanner(genreList);
        while (scan.hasNextLine()){
            String genreInfo = scan.nextLine();
            String[] tokens = genreInfo.split(Pattern.quote(" - "));
            if (tokens[0].equalsIgnoreCase(genreName)){
                return tokens[1];
            }
        }
        return "0000000";
    }

    public String findGenreName(String ID) throws FileNotFoundException {
        Scanner scan = new Scanner(genreList);
        while (scan.hasNextLine()){
            String genreInfo = scan.nextLine();
            String[] tokens = genreInfo.split(Pattern.quote(" - "));
            if (tokens[1].equals(ID)){
                return tokens[0];
            }
        }
        return "0000000";
    }

    public String convertToLabel(ArrayList<Integer> userList) throws FileNotFoundException {
        String labelText = "";
        for (Integer log : userList){
            labelText += findGenreName(Integer.toString(log));
            labelText += "\n";
        }
        return labelText;
    }
}
