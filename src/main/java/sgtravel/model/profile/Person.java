package sgtravel.model.profile;

import java.time.LocalDateTime;

/**
 * Contains information of user.
 */
public class Person {
    private String name;
    private LocalDateTime birthday;

    /**
     * Constructs a person object with name and birthday.
     *
     * @param name Name of User.
     * @param birthday Birthday of User.
     */
    public Person(String name, LocalDateTime birthday) {
        this.name = name;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getBirthday() {
        return birthday;
    }

    public void setName(String name) {
        this.name = name;
    }

}
