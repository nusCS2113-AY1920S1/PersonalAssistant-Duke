package diyeats.storage;

import com.google.gson.GsonBuilder;
import diyeats.commons.file.LocalDateAdapter;

import java.time.LocalDate;

//@@author Fractalisk

public class WriteTest {
    private Write writer = new Write(new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class,
            new LocalDateAdapter()).create());
    private Load loader = new Load(new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class,
            new LocalDateAdapter()).create());
    private LocalDate date = LocalDate.parse("2019-11-08");
}
