package sgtravel.model.profile;

import java.time.LocalDateTime;

public class Person {
    private String name;
    private LocalDateTime birthday;

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

    public void setBirthday(LocalDateTime birthday) {
        this.birthday = birthday;
    }
}
