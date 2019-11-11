package CoreTests;

import rims.core.ResourceList;
import rims.core.Ui;
import rims.exception.*;
import rims.resource.Item;
import rims.resource.Resource;
import rims.resource.Room;

import java.util.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.*;

//@@author isbobby
/**
 * Test class covers the following methods: getResourceById getResourceByName
 * stringToDate dateToString
 * 
 */
public class ResourceListTest {
    private static Ui ui;
    private static ResourceList listUnderTest;

    private static Resource resource_1;
    private static Resource resource_2;
    private static Resource resource_3;

    @BeforeAll
    private static void init() throws RimsException {
        ui = new Ui();
        ArrayList<Resource> emptyList = new ArrayList<Resource>();
        listUnderTest = new ResourceList(ui, emptyList);

        resource_1 = new Item(1, "testitem");
        resource_2 = new Item(2, "testitem");
        resource_3 = new Room(3, "testroom");

        listUnderTest.add(resource_1);
        listUnderTest.add(resource_2);
        listUnderTest.add(resource_3);
    }

    @BeforeEach
    private void setup() {
        ;
    }

    @AfterEach
    private void cleanup() {
        ;
    }

    /**
     * This tests the method getResourceById, whether it is able to return the
     * correct resource.
     * 
     * @throws RimsException
     */
    @Test
    public void getResourceByIdCorrectnessTest() throws RimsException {
        assertEquals(resource_1, listUnderTest.getResourceById(1));
        ui.print("Test: getResourceById() correctness\nStatus:Passed");
    }

    /**
     * This test tries to query for a resource name that does not exist.
     * 
     * @throws RimsException
     */
    @Test
    public void getResourceByNameCorrectnessTest() throws RimsException {
        assertEquals(resource_3, listUnderTest.getResourceByName("testroom"));
        ui.print("Test: getResourceByName() correctness\nStatus:Passed");
    }

    /**
     * This test tries to query for a resource name that does not exist.
     * 
     * @throws RimsException
     */
    @Test
    public void getAllOfResourcesByNameCorrectnessTest() throws RimsException {
        ArrayList<Resource> result = new ArrayList<Resource>();
        result.add(resource_1);
        result.add(resource_2);
        assertEquals(result, listUnderTest.getAllOfResource("testitem"));
        ui.print("Test: getAllOfResourceByName() correctness\nStatus:Passed");
    }

    /**
     * This test tries to query for a resource that does not exist.
     */
    @Test
    public void resourceDoesNotExistTest() {
        Exception e = assertThrows(RimsException.class, () -> {
            listUnderTest.getResourceById(10);
        });
        assertEquals("No such resource ID!", e.getMessage());
        System.out.print("Test: ID does not exist test\nStatus: Passed\n");
    }

    /**
     * getNumberOfResource(name) test
     */
    @Test
    public void getNumberOfResourceCorrectnessTest() {
        assertEquals(2, listUnderTest.getNumberOfResource("testitem"));
        System.out.print("Test: getNumberOfResource(name) correctness\nStatus: Passed\n");
    }

    @Test
    public void getAvailableNumberOfResourceTest() {
        assertEquals(2, listUnderTest.getAvailableNumberOfResource("testitem"));
        System.out.print("Test: getAvailableNumberOfResource test\nStatus: Passed\n");
    }

    @Test
    public void generateReservationIdTest() {
        assertEquals(0, listUnderTest.generateReservationId());
        System.out.print("Test: generateReservationId test\nStatus: Passed\n");
    }
}