package EPstorage;

import java.util.ArrayList;

/**
 * structure for UserProfile object
 */
public class UserProfile {
    private String userName;
    private int userAge;
    private ArrayList<Integer> genreId;
    private boolean adult = true;

    public String getUserName(){
        return userName;
    }

    public int getUserAge(){
        return userAge;
    }

    public ArrayList<Integer> getGenreId() {
        return genreId;
    }

    public void setUserName(String inputName) {
        userName = inputName;
    }

    public void setUserAge(int inputAge) {
        userAge = inputAge;
    }

    public void setGenreId(ArrayList<Integer> inputGenre) {
        genreId = inputGenre;
    }

    public void addGenreId(ArrayList<Integer> inputGenre) {
        genreId.addAll(inputGenre);
    }

    public void removeGenreId(ArrayList<Integer> inputGenre) {
        genreId.removeAll(inputGenre);
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }
}