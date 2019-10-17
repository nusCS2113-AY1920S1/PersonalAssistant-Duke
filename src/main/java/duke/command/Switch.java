package duke.command;

import java.util.List;

/**
 * A switch for a command, with various specified properties.
 */
public class Switch {

   public final String name;
   public final Class type;
   public final boolean isOptional;
   public final ArgLevel argLevel;
   public final String root;
   public final List<String> aliases;

   /**
    * Construct a new Switch object, describing the properties of the switch.
    * @param name The full name of the switch.
    * @param type The expected type of the switch. If there is more than one possible type, specify "String" and
    *             parse and differentiate within the command itself.
    * @param isOptional Whether or not the switch is optional.
    * @param argLevel The requirement for an argument for this switch.
    * @param root The shortest prefix of this switch by which it can be unambiguously recognised.
    * @param aliases Any other names for this switch that do not constitute a prefix for it.
    */
   public Switch(String name, Class type, boolean isOptional, ArgLevel argLevel, String root, String... aliases) {
      this.name = name;
      this.type = type;
      this.isOptional = isOptional;
      this.argLevel = argLevel;
      this.root = root;
      this.aliases = List.of(aliases);
   }
}
