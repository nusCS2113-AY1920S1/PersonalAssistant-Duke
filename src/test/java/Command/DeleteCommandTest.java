import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import rims.command.*;
import rims.core.*;
import rims.exception.RimsException;
import rims.resource.Resource;

import java.io.ByteArrayOutputStream;


import java.io.*;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertThrows;

//@@hin1
public class DeleteCommandTest {
    private Ui tempUi;
    private Storage tempStorage;
    private ResourceList tempResources;

    private DeleteCommand commandUnderTest;

    private File testResourceFile;
    private File testReservationsFile;
    @TempDir Path tempDir;

    @BeforeEach
    public void init() throws RimsException {
        this.testResourceFile = new File("tempDeleteResources.txt");
        this.testReservationsFile = new File("tempDeleteReservations.txt");
    }

    @AfterEach
    public void deleteTempFiles() {
        testResourceFile.delete();
        testReservationsFile.delete();
    }

    @Test
    public void DeleteNonExistentItemTest() throws RimsException {
        this.tempUi = new Ui();
        this.tempStorage = new Storage(testResourceFile.getName(),testReservationsFile.getName());
        this.tempResources = new ResourceList(tempUi,tempStorage.getResources());

        commandUnderTest = new DeleteCommand("ball","item");
        assertThrows(RimsException.class, () -> commandUnderTest.execute(tempUi,tempStorage,tempResources));
    }

    @Test
    public void DeleteNonExistentRoomTest() throws RimsException {
        this.tempUi = new Ui();
        this.tempStorage = new Storage(testResourceFile.getName(),testReservationsFile.getName());
        this.tempResources = new ResourceList(tempUi,tempStorage.getResources());

        commandUnderTest = new DeleteCommand("seminar room 1","room");
        assertThrows(RimsException.class, () -> commandUnderTest.execute(tempUi,tempStorage,tempResources));
    }

    @Test
    public void validDeleteRoomTest() throws RimsException {
        this.tempUi = new Ui();
        this.tempStorage = new Storage(testResourceFile.getName(),testReservationsFile.getName());
        this.tempResources = new ResourceList(tempUi,tempStorage.getResources());
        new AddCommand("seminar room 1").execute(tempUi,tempStorage,tempResources);

        commandUnderTest = new DeleteCommand("seminar room 1","room");
        commandUnderTest.execute(tempUi,tempStorage,tempResources);
    }

    @Test
    public void InvalidResourceIdTest() throws RimsException {
        InputStream sysin = System.in;
        ByteArrayInputStream input = new ByteArrayInputStream("1\n".getBytes());
        System.setIn(input);
        this.tempUi = new Ui();
        this.tempStorage = new Storage(testResourceFile.getName(),testReservationsFile.getName());
        this.tempResources = new ResourceList(tempUi,tempStorage.getResources());
        new AddCommand("ball", 1).execute(tempUi,tempStorage,tempResources);

        commandUnderTest = new DeleteCommand("ball","item");
        assertThrows(RimsException.class, () -> commandUnderTest.execute(tempUi,tempStorage,tempResources));
    }

    @Test
    public void validDeleteItemTest() throws RimsException {
        InputStream sysin = System.in;
        ByteArrayInputStream input = new ByteArrayInputStream("0\n".getBytes());
        System.setIn(input);
        this.tempUi = new Ui();
        this.tempStorage = new Storage(testResourceFile.getName(),testReservationsFile.getName());
        this.tempResources = new ResourceList(tempUi,tempStorage.getResources());
        new AddCommand("ball", 1).execute(tempUi,tempStorage,tempResources);

        commandUnderTest = new DeleteCommand("ball","item");
        commandUnderTest.execute(tempUi,tempStorage,tempResources);
    }

    @Test
    public void validDeleteItemsTest() throws RimsException {
        InputStream sysin = System.in;
        ByteArrayInputStream input = new ByteArrayInputStream("0 1\n".getBytes());
        System.setIn(input);
        this.tempUi = new Ui();
        this.tempStorage = new Storage(testResourceFile.getName(),testReservationsFile.getName());
        this.tempResources = new ResourceList(tempUi,tempStorage.getResources());
        new AddCommand("ball", 1).execute(tempUi,tempStorage,tempResources);
        new AddCommand("ball", 1).execute(tempUi,tempStorage,tempResources);

        commandUnderTest = new DeleteCommand("ball","item");
        commandUnderTest.execute(tempUi,tempStorage,tempResources);
    }

}
