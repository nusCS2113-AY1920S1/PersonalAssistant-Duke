//@@author LL-Pengfei
/**
 * FoodTest.java
 * Test whether the Food Class is functioning as expected.
 */
import cube.exception.CubeException;
import cube.model.food.Food;
import static org.junit.jupiter.api.Assertions.*;

package cube.model.food;

/**
 * This class tests whether the Food Class is functioning as expected.
 */
public class FoodTest extends Food {
    /**
     * The default constructor.
     *
     * @param name The food name.
     */
    public FoodTest(String name) {
        super(name);
    }

    /**
     * This method tests the constructor of the Food Class.
     *
     * @throws CubeException if test fails.
     */
    public void testConstructor() throws CubeException {
        String food1Name = "love2113t";
        Food food1 = new Food(food1Name);
        int food1Revenue = 0;

        assertEquals(food1Name, food1.getName(), "Food name wrong.");
        assertTrue(food1Revenue==food1.getFoodRevenue(),"Food revenue not initialized properly.");

        Food food2 = new Food(food1Name);
        fail("Constructor allows duplicate food names.");

        Food food3 = new Food(null);
        fail("Constructor allows null food name.");
    }

    /**
     * This method tests the setting and getting food variables.
     */
    public void testAssignAndRetrieveGrades() {
        // create a student
        Student stu = new Student("Jimmy", "946302B");

        // assign a few grades to this student
        stu.assignGrade("cs2102", 60);
        stu.assignGrade("cs2103", 70);
        stu.assignGrade("cs3214s", 80);

        // verify that the assignment is correct
        assertTrue("fail to assign cs2102 grade", stu.getGrade("cs2102") == 60);
        assertTrue("fail to assign cs2103 grade", stu.getGrade("cs2103") == 70);

        // attempt to retrieve a course that does not exist
        try {
            stu.getGrade("cs21002");
            fail("fail to catch non-existent course name");
        } catch (RuntimeException e) {
        }
    }
}