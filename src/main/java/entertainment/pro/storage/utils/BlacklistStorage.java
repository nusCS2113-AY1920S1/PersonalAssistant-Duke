package entertainment.pro.storage.utils;


import entertainment.pro.model.MovieInfoObject;
import entertainment.pro.storage.user.Blacklist;
import entertainment.pro.model.UserProfile;
import entertainment.pro.storage.user.Blacklist;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import entertainment.pro.model.MovieModel;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;

/**
 * class that deals with editing the BlackList duke.storage file.
 *
 */
public class BlacklistStorage {


    private ObjectMapper mapper = new ObjectMapper();
    private File file;
    private InputStream inputStream;
    private TypeReference<UserProfile> typeReference = new TypeReference<UserProfile>() { };

    private enum BlacklistKeys { KEYS, MOVIES, ID };

    /**
     * checkstyle made me put javadoc here >:( whoever made this function pls edit the the javadoc tqtq -wh.
     */
    public BlacklistStorage() throws IOException {
        file = new File("./BlacklistStorage.json");
        if (file.createNewFile()) {
            createNewBlacklistFile();

        }
        this.inputStream = new FileInputStream(file);

    }

    /**
     * load blacklisted movies and keywords from JSON duke.storage.
     *
     */
    public void load() throws IOException {

        try {

            File newFile = new File("./BlacklistStorage.json");
            if (newFile.createNewFile()) {
                Blacklist.initialiseAll(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<MovieModel>());
            } else {
                FileReader reader = new FileReader(newFile);
                JSONParser jsonParser = new JSONParser();
                JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
                System.out.println("jsonObject");
                System.out.println(jsonObject.get(BlacklistKeys.KEYS.toString()));

                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<String>>() { }.getType();
                ArrayList<String> keyList = gson.fromJson(jsonObject.get(BlacklistKeys.KEYS.toString()).toString(),
                        type);

                Type type2 = new TypeToken<ArrayList<MovieModel>>() { }.getType();
                ArrayList<MovieModel> movieList = gson.fromJson(jsonObject.get(BlacklistKeys.ID.toString()).toString(),
                        type2);

                ArrayList<String> movieTitleList = gson.fromJson(jsonObject.get(BlacklistKeys.MOVIES.toString())
                                .toString(), type);

                Blacklist.initialiseAll(keyList, movieTitleList, movieList);

            }


        } catch (Exception e) {
            //TODO add exception handling
        }
    }

    /**
     * update json file with any changes made blacklist.
     */
    public void updateBlacklistFile(ArrayList<String> blackListKeyWords,
                                    ArrayList<MovieModel> blackListMovies,
                                     ArrayList<String> blackListMoviesTitle) throws IOException {

        JSONObject allblacklist = new JSONObject();

        allblacklist.put(BlacklistKeys.KEYS, blackListKeyWords);
        allblacklist.put(BlacklistKeys.ID, blackListMovies);
        allblacklist.put(BlacklistKeys.MOVIES, blackListMoviesTitle);

        File oldFile = file;
        File newFile = new File("./tempBlacklist.json");
        mapper.writeValue(newFile, allblacklist);
        oldFile.delete();
        newFile.renameTo(new File(file.getAbsolutePath()));

        System.out.println("Successfully DONE SAVING!");
    }

    /**
     * checkstyle made me put javadoc here >:( whoever made this function pls edit the the javadoc tqtq -wh.
     */
    public void createNewBlacklistFile() throws IOException {

        JSONObject allblacklist = new JSONObject();

        allblacklist.put(BlacklistKeys.KEYS, new ArrayList<String>());
        allblacklist.put(BlacklistKeys.ID, new ArrayList<MovieModel>());
        allblacklist.put(BlacklistKeys.MOVIES, new ArrayList<String>());

        File newFile = new File("./BlacklistStorage.json");
        mapper.writeValue(newFile, allblacklist);

    }
}
