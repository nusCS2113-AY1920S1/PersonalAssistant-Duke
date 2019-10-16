package duke.command;

import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;

public class Switch {

   public final String name;
   public final Class type;
   public final boolean isOptional;
   public final ArgLevel argLevel;
   public final String root;
   public final List<String> aliases;

   public Switch(String name, Class type, boolean isOptional, ArgLevel argLevel, String root, String... aliases) {
      this.name = name;
      this.type = type;
      this.isOptional = isOptional;
      this.argLevel = argLevel;
      this.root = root;
      this.aliases = List.of(aliases);
   }
}
