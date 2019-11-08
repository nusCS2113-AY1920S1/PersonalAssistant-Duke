package duke.model.profile;

import duke.commons.exceptions.NoSuchCategoryException;
import duke.commons.exceptions.NoSuchItineraryException;
import duke.model.planning.Itinerary;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

public class ProfileCard {
    private Person person;
    private Preference preference = new Preference();
    private HashMap<String, Itinerary> favourite = new HashMap<>();

    public ProfileCard() {
        setPerson("User", LocalDateTime.now());
    }

    public void setPreference(String category, Boolean setting) throws NoSuchCategoryException {
        preference.setPreference(category, setting);
    }

    public void setPerson(String name, LocalDateTime birthday) {
        this.person = new Person(name, birthday);
    }

    public String getPersonName() {
        return person.getName();
    }

    public LocalDateTime getPersonBirthday() {
        return person.getBirthday();
    }

    public ArrayList<Boolean> getPreference() {
        return preference.getAllPreference();
    }

    public HashMap<String, Itinerary> getFavouriteList() {
        return favourite;
    }

    public Itinerary getFavourite(String name) throws NoSuchItineraryException {
        try {
            return favourite.get(name);
        } catch (NullPointerException e) {
            throw new NoSuchItineraryException();
        }
    }

    public void addFavourite(String name, Itinerary fav) {
        fav.setName(name);
        favourite.put(name, fav);
    }

    public int getAge() {
        return Period.between(person.getBirthday().toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();
    }
}
