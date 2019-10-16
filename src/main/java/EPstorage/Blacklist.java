package EPstorage;

import object.MovieInfoObject;

import java.util.ArrayList;

public class Blacklist {

    private static ArrayList<String>  BlackListMovies = new ArrayList<>();

    public static void addToBlacklist(String Movie)
    {
        if(Movie.trim() == ""){
            return;
        }
        BlackListMovies.add(Movie.toLowerCase());
    }

    public static boolean removeFromBlacklist(String Movie){
        if(Movie.trim() == ""){return false;}
        if(BlackListMovies.contains(Movie.toLowerCase())){
            BlackListMovies.remove(Movie.toLowerCase());
            return true;
        }else{
            return false;
        }
    }
    public static String getIndex(int index){
        if(index >= BlackListMovies.size()){
            return "";
        }
        return BlackListMovies.get(index-1);
    }
    public static void clearBlacklist(){
        BlackListMovies.clear();
    }

    public static String printList(){
        String feedback = "";
        int i  = 1;
        for(String e : BlackListMovies){
            feedback += String.valueOf(i);
            feedback += ") ";
            feedback += e;
            feedback += "\t\t";
            i++;
            if(i%4 ==0){
                feedback += "\n";
            }
        }

        return feedback;
    }

    public static ArrayList<String> getBlackListMovies() {
        return (ArrayList<String>) BlackListMovies.clone();
    }

    public static String printHint(){
        String feedback = "";
        int i  = 1;
        for(String e : BlackListMovies){

            feedback += e;

            feedback += "\n";

        }

        return feedback;
    }

    public static ArrayList<MovieInfoObject> filter(ArrayList<MovieInfoObject> mMovies){
        ArrayList<MovieInfoObject> filteredMovies = new ArrayList<>();
        for(MovieInfoObject o : mMovies){
            boolean isBlacklisted = false;
            for(String e : BlackListMovies){
                if(o.getTitle().toLowerCase().equals(e.toLowerCase())){
                    isBlacklisted =true;
                }
            }
            if(!isBlacklisted){
                filteredMovies.add(o);
            }
        }
        return filteredMovies;
    }
}
