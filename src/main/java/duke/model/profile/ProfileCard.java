package duke.model.profile;

import duke.commons.exceptions.CategoryNotFoundException;
import duke.model.planning.Itinerary;

import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;

public class ProfileCard {
    private Person person;
    private Preference preference = new Preference();
    private ArrayList<Itinerary> favourite = new ArrayList<>();

    public ProfileCard() {
        setPerson("User", LocalDateTime.now());
    }

    public void setPreference(String category, Boolean setting) throws CategoryNotFoundException {
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

    public ArrayList<Itinerary> getFavourite() {
        return favourite;
    }

    public void addFavourite(Itinerary fav) {
        favourite.add(fav);
    }

    public int getAge() {
        return Period.between(person.getBirthday().toLocalDate(), LocalDateTime.now().toLocalDate()).getYears();
    }
}
