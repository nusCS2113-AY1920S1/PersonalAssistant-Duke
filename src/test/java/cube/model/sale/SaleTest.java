package cube.model.sale;

import java.util.Date;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static cube.testutil.Assert.assertThrowEquals;

public class SaleTest {
    @Test
    public void compareToTest() {
        Sale s1 = new Sale("b", 0, 0, 0, new Date(2019, 8, 8));
        Sale s2 = new Sale("a", 0, 0, 0, new Date(2019, 8, 8));
        Sale s3 = new Sale("a", 0,0,0, new Date(2019, 9, 9));
        int dateSmaller = s2.compareTo(s3);
        int dateLarger = s3.compareTo(s3);
        int dateEqual = s3.compareTo(s3);
        final int nameSmaller = s2.compareTo(s1);
        final int dateLargerNameSmaller = s3.compareTo(s1);
        final int nameEqual = s1.compareTo(s1);
        final int nameLarger = s1.compareTo(s2);
        assertEquals(dateSmaller, -1);
        assertEquals(dateEqual, 1);
        assertEquals(dateLarger, 1);
        assertEquals(nameSmaller, -1);
        assertEquals(nameEqual, 1);
        assertEquals(nameLarger, 1);
        assertEquals(dateLargerNameSmaller, 1);
    }
}
