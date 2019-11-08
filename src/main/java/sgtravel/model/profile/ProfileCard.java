package sgtravel.model.profile;

import sgtravel.commons.exceptions.NoSuchCategoryException;
import sgtravel.commons.exceptions.NoSuchItineraryException;
import sgtravel.model.planning.Itinerary;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;

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

    /**
     * Returns the favourite itinerary of given name.
     *
     * @param name Name of favourite itinerary to return.
     * @return Itinerary of given name.
     * @throws NoSuchItineraryException If there is no itinerary of given name.
     */
    public Itinerary getFavourite(String name) throws NoSuchItineraryException {
        try {
            return favourite.get(name);
        } catch (NullPointerException e) {
            throw new NoSuchItineraryException();
        }
    }

    /**
     * Adds the itinerary of given name to favourite.
     * @param name Name of itinerary.
     * @param fav Itinerary object.
     */
    public void addFavourite(String name, Itinerary fav) {
        fav.setName(name);
        favourite.put(name, fav);
    }

    /**
     * Returns the age of user.
     *
     * @return Age of user.
     */
    public int getAge() {
        return Period.between(person.getBirthday().toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();
    }

    public void setFavourite(HashMap<String, Itinerary> itinerary) {
        this.favourite = itinerary;
    }
}
