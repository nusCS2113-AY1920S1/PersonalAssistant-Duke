package FrontEnd;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Usage:
 * <li>String msg = Ansi.Red.and(Ansi.BgYellow).format("Hello %s", name)</li>
 * <li>String msg = Ansi.Blink.colorize("BOOM!")</li>
 *
 * Or, if you are adverse to that, you can use the constants directly:
 * <li>String msg = new Ansi(Ansi.ITALIC, Ansi.GREEN).format("Green money")</li>
 * Or, even:
 * <li>String msg = Ansi.BLUE + "scientific"</li>
 *
 * NOTE: Nothing stops you from combining multiple FG colors or BG colors,
 *       but only the last one will display.
 *
 * @author dain
 * https://gist.github.com/dainkaplan/4651352
 */
public final class AsciiColours {

    // Color code strings from:
    // http://www.topmudsites.com/forums/mud-coding/413-java-ansi.html
    public static final String	SANE				= "\u001B[0m" + "\u001B[30m" + "\u001B[47m";
//    public static final String	SANE				= "\u001B[0m";


    public static final String	HIGH_INTENSITY		= "\u001B[1m";
    public static final String	LOW_INTENSITY		= "\u001B[2m";

    public static final String	ITALIC				= "\u001B[3m";
    public static final String	UNDERLINE			= "\u001B[4m";
    public static final String	BLINK				= "\u001B[5m";
    public static final String	RAPID_BLINK			= "\u001B[6m";
    public static final String	REVERSE_VIDEO		= "\u001B[7m";
    public static final String	INVISIBLE_TEXT		= "\u001B[8m";

    public static final String	BLACK				= "\u001B[30m";
    public static final String	RED					= "\u001B[31m";
    public static final String	GREEN				= "\u001B[32m";
    public static final String	YELLOW				= "\u001B[33m";
    public static final String	BLUE				= "\u001B[34m";
    public static final String	MAGENTA				= "\u001B[35m";
    public static final String	CYAN				= "\u001B[36m";
    public static final String	WHITE				= "\u001B[37m";

    public static final String	BACKGROUND_BLACK	= "\u001B[40m";
    public static final String	BACKGROUND_RED		= "\u001B[41m";
    public static final String	BACKGROUND_GREEN	= "\u001B[42m";
    public static final String	BACKGROUND_YELLOW	= "\u001B[43m";
    public static final String	BACKGROUND_BLUE		= "\u001B[44m";
    public static final String	BACKGROUND_MAGENTA	= "\u001B[45m";
    public static final String	BACKGROUND_CYAN		= "\u001B[46m";
    public static final String	BACKGROUND_WHITE	= "\u001B[47m";

    public static final AsciiColours HighIntensity = new AsciiColours(HIGH_INTENSITY);
    public static final AsciiColours Bold = HighIntensity;
    public static final AsciiColours LowIntensity = new AsciiColours(LOW_INTENSITY);
    public static final AsciiColours Normal = LowIntensity;

    public static final AsciiColours Italic = new AsciiColours(ITALIC);
    public static final AsciiColours Underline = new AsciiColours(UNDERLINE);
    public static final AsciiColours Blink = new AsciiColours(BLINK);
    public static final AsciiColours RapidBlink = new AsciiColours(RAPID_BLINK);

    public static final AsciiColours Black = new AsciiColours(BLACK);
    public static final AsciiColours Red = new AsciiColours(RED);
    public static final AsciiColours Green = new AsciiColours(GREEN);
    public static final AsciiColours Yellow = new AsciiColours(YELLOW);
    public static final AsciiColours Blue = new AsciiColours(BLUE);
    public static final AsciiColours Magenta = new AsciiColours(MAGENTA);
    public static final AsciiColours Cyan = new AsciiColours(CYAN);
    public static final AsciiColours White = new AsciiColours(WHITE);

    public static final AsciiColours BgBlack = new AsciiColours(BACKGROUND_BLACK);
    public static final AsciiColours BgRed = new AsciiColours(BACKGROUND_RED);
    public static final AsciiColours BgGreen = new AsciiColours(BACKGROUND_GREEN);
    public static final AsciiColours BgYellow = new AsciiColours(BACKGROUND_YELLOW);
    public static final AsciiColours BgBlue = new AsciiColours(BACKGROUND_BLUE);
    public static final AsciiColours BgMagenta = new AsciiColours(BACKGROUND_MAGENTA);
    public static final AsciiColours BgCyan = new AsciiColours(BACKGROUND_CYAN);
    public static final AsciiColours BgWhite = new AsciiColours(BACKGROUND_WHITE);

    final private String[] codes;
    final private String codes_str;
    public AsciiColours(String... codes) {
        this.codes = codes;
        String _codes_str = "";
        for (String code : codes) {
            _codes_str += code;
        }
        codes_str = _codes_str;
    }

    public AsciiColours and(AsciiColours other) {
        List<String> both = new ArrayList<String>();
        Collections.addAll(both, codes);
        Collections.addAll(both, other.codes);
        return new AsciiColours(both.toArray(new String[] {}));
    }

    public String colorize(String original) {
        return codes_str + original + SANE;
    }

    public String format(String template, Object... args) {
        return colorize(String.format(template, args));
    }
}