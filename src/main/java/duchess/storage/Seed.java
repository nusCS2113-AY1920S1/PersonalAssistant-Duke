package duchess.storage;

import duchess.model.Grade;
import duchess.model.Module;
import duchess.model.task.Deadline;
import duchess.model.task.Task;
import duchess.model.task.Todo;

import java.time.LocalDateTime;
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

        // Grades
        Grade gd1 = new Grade("midterm", 20, 30, 20);
        a.addGrade(gd1);
        Grade gd2 = new Grade("problem set1", 60, 100, 10);
        b.addGrade(gd2);
        Grade gd3 = new Grade("assignment1", 20, 20, 12);
        d.addGrade(gd3);
        Grade gd4 = new Grade("lab assignment1", 15, 20, 20);
        e.addGrade(gd4);
        Grade gd5 = new Grade("essay", 18.5, 30, 15);
        f.addGrade(gd5);


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

        // Deadlines
        LocalDateTime time1 = LocalDateTime.of(2019, 12, 23, 8, 00);
        Task l = new Deadline("PPP submission", time1);
        l.setModule(c);
        LocalDateTime time2 = LocalDateTime.of(2019, 12, 2, 14, 00);
        Task m = new Deadline("Term Paper submission", time2);
        Grade gd6 = new Grade("Term Paper submission", 20);
        m.setModule(f);
        m.setGrade(gd6);
        LocalDateTime time3 = LocalDateTime.of(2019, 12, 24, 23, 59);
        Task n = new Deadline("Buy Christmas gifts", time3);
        LocalDateTime time4 = LocalDateTime.of(2019, 12, 13, 23, 59);
        Task o = new Deadline("Send email to friends.", time4);
        List.of(l, m, n, o).forEach(x -> store.getTaskList().add(x));
    }
}
