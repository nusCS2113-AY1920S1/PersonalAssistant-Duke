package diyeats.model.user;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {
    private User user = new User("Foo Chi Hen", 22, 100, Gender.MALE, 0, 100, LocalDate.now());

    @Test
    void user() {
        user.setWeight(100);
        assertEquals(user.getWeight(), 100);
        assertEquals(user.getName(), "Foo Chi Hen");
        assertEquals(user.getHeight(), 100);
        assertEquals(user.getActivityLevel(), 0);
        assertEquals(user.getAge(), 22);
        assertTrue(user.getAllWeight() instanceof HashMap);
    }
}
