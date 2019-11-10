package duchess.storage;

import duchess.model.Module;
import duchess.model.task.Task;
import duchess.model.task.Todo;

import java.util.List;

/**
 * Add seed data when application is first started.
 */
public class Seed {
    /**
     * Seeds a given store with default data.
     *
     * @param store the store to seed
     */
    public static void execute(Store store) {
        // Modules
        Module a = new Module("CS1231", "Discrete Mathematics");
        Module b = new Module("CS2040", "Algorithms");
        Module c = new Module("CS2113", "Software Engineering");
        Module d = new Module("CS1010", "Introduction to Programming");
        Module e = new Module("EE2026", "Digital Design");
        Module f = new Module("GES1010", "Nation Building");
        List.of(a, b, c, d, e, f).forEach(x -> store.getModuleList().add(x));

        // Todos
        Task g = new Todo("Reading");
        g.setModule(f);
        Task h = new Todo("Personal stuff");
        Task i = new Todo("Revision");
        i.setModule(c);
        Task j = new Todo("Community Service");
        Task k = new Todo("Consult prof on concept");
        k.setModule(e);
        List.of(g, h, i, j, k).forEach(x -> store.getTaskList().add(x));
    }
}
