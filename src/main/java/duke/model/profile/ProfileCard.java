package duke.model.profile;

import duke.model.planning.Agenda;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class ProfileCard {
    private Person person;
    private Preference preference;
    private ArrayList<Agenda> favourite;
    private boolean isNewUser = true;

    public void setPreference(Preference preference) {
        this.preference = preference;
    }

    public void setPerson(String name, LocalDateTime birthday) {
        this.person = new Person(name, birthday);
        isNewUser = false;
    }

    public boolean isNewUser() {
        return isNewUser;
    }

    public String getPersonName() {
        return person.getName();
    }

    public LocalDateTime getPersonBirthday() {
        return person.getBirthday();
    }

    public Preference getPreference() {
        return preference;
    }

    public ArrayList<Agenda> getFavourite() {
        return favourite;
    }
}
