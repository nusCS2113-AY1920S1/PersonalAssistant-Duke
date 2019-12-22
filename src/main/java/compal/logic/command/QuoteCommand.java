package compal.logic.command;

import compal.model.tasks.TaskList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Random;

public class QuoteCommand extends Command {

    /**
     * Private Constants.
     */
    private static final String CMD_TEAM = "team";

    /**
     * Private attributes.
     */
    private String restOfInput;
    private static Set<String> shoSet = new HashSet<String>();
    private static Set<String> lpzSet = new HashSet<String>();
    private static Set<String> catSet = new HashSet<String>();
    private static Set<String> jaeSet = new HashSet<String>();
    private static Set<String> yjySet = new HashSet<String>();
    private static ArrayList<String> shoQuote = new ArrayList<>();
    private static ArrayList<String> lpzQuote = new ArrayList<>();
    private static ArrayList<String> catQuote = new ArrayList<>();
    private static ArrayList<String> jaeQuote = new ArrayList<>();
    private static ArrayList<String> yjyQuote = new ArrayList<>();
    private static ArrayList<String> genQuote = new ArrayList<>();

    /**
     * Constructor for QuoteCommand.
     *
     * @param restOfInput User input after quote command word.
     */
    public QuoteCommand(String restOfInput) {
        this.restOfInput = restOfInput.toLowerCase();
        shoSet.add("sho");
        shoSet.add("sholihin");
        lpzSet.add("jerry");
        lpzSet.add("lpz");
        lpzSet.add("peize");
        catSet.add("cat");
        catSet.add("catherine");
        jaeSet.add("jaedon");
        yjySet.add("junyi");
        initialiseShoQuotes();
        initialiseLpzQuotes();
        initialiseCatQuotes();
        initialiseJaeQuotes();
        initialiseYjyQuotes();
    }

    @Override
    public CommandResult commandExecute(TaskList taskList) {
        Random rand = new Random();
        int randInt = rand.nextInt(1000);
        if (shoSet.contains(restOfInput)) {
            int index = randInt % shoQuote.size();
            return new CommandResult(shoQuote.get(index), false);
        } else if (lpzSet.contains(restOfInput)) {
            int index = randInt % lpzQuote.size();
            return new CommandResult(lpzQuote.get(index), false);
        } else if (catSet.contains(restOfInput)) {
            int index = randInt % (catQuote.size());
            return new CommandResult(catQuote.get(index), false);
        } else if (jaeSet.contains(restOfInput)) {
            int index = randInt % (jaeQuote.size());
            return new CommandResult(jaeQuote.get(index), false);
        } else if (yjySet.contains(restOfInput)) {
            int index = randInt % (yjyQuote.size());
            return new CommandResult(yjyQuote.get(index), false);
        } else if (restOfInput.equals(CMD_TEAM)) {
            ArrayList<String> teamQuotes = initialiseTeamQuotes();
            int index = randInt % teamQuotes.size();
            return new CommandResult(teamQuotes.get(index), false);
        } else {
            ArrayList<String> allQuotes = initialiseTeamQuotes();
            initialiseGenQuotes();
            allQuotes.addAll(genQuote);
            int index = randInt % allQuotes.size();
            return new CommandResult(allQuotes.get(index), false);
        }
    }

    /**
     * Put all team quotes into an ArrayList.
     *
     * @return ArrayList of quotes from all team members.
     */
    public ArrayList<String> initialiseTeamQuotes() {
        ArrayList<String> teamQuotes = new ArrayList<>();
        teamQuotes.addAll(shoQuote);
        teamQuotes.addAll(lpzQuote);
        teamQuotes.addAll(catQuote);
        teamQuotes.addAll(jaeQuote);
        teamQuotes.addAll(yjyQuote);
        return teamQuotes;
    }

    /**
     * Add all quotes by Sho into shoQuote Arraylist.
     */
    public void initialiseShoQuotes() {
        shoQuote.add("\n\nOkay timeline to fix all bugs is Friday night ahhh, Then document self-check is "
                    + "\nSunday night. Then we on Monday stay back after 2101 finalise then submit then "
                    + "\nstay back for demo prep and present \n\n"
                    + " - Sholihin, Whatsapp message in lead-up to pitch");
        shoQuote.add("\n\nanywas when you guys test,besides bugs please say things you dont like too or"
                    + "\nplaces of improvement\n\n"
                    + " - Sholihin, Whatsapp message nearing the end of project \n (Yes he made those typos too)");
        shoQuote.add("\n\nI've done my 2105 assig, I'll pass it to you guys later \n\n"
                    + " - Sholihin, Whatsapp message, being a total godsend");
        shoQuote.add("\n\nHosneslty idk why I can't sleep but I cmi TMR you guys know why \n\n"
                    + " - Sholihin, Whatsapp message before consult, indicating clearly that he needs \n to sleep");
        shoQuote.add("\n\n Today need stay back for anything after 2113t?\n\n"
                    +  " If not fuck it\n\n"
                    +  " - Sholihin, Whatsapp message echoing everyone's thoughts");
        shoQuote.add("\n\nI did baby\n\n"
                    + "But if not we could hug it out and share body heat\n\n"
                    + " - Sholihin, Whatsapp reply to Jun Yi telling people to bring jacket as the \n"
                    + " aircon was on at full blast at Hon Sui Sen Library");
        shoQuote.add("\n\nI reward peize\n\n"
                    + "With the hot photo\n\n"
                    + " - Sholihin, Whatsapp message trolling people");
        shoQuote.add("\n\nHey guys, i know this is abit late @ night but i got some important stuff to \n"
                    + "write down\n\n"
                    + " - Sholihin, usual introduction before unloading info via Whatsapp");
        shoQuote.add("\n\nNo, I carry this around in my bag everyday.\n\n"
                    + " - Sholihin, on his Polaroid camera");
    }

    /**
     * Add all quotes by Peize into lpzQuote Arraylist.
     */
    public void initialiseLpzQuotes() {
        lpzQuote.add("\n\nGuys you can check if I wrote anything wrong about your commands \n\n"
                    + " - Peize, Whatsapp message after slogging it out over help command");
        lpzQuote.add("\n\nFighting with Travis check now\n\n"
                    + " - Peize, Whatsapp message at the start of an hours-long battle");
        lpzQuote.add("\n\nOkay thanks\n\n"
                    + "Can go back to sleep\n\n"
                    + " - Peize, Whatsapp message after confirming that there wasn't any CS2101 lesson \n"
                    + " for the day");
        lpzQuote.add("\n\nI pushed some Junit testing classes\n\n"
                    + " - Peize, frequent Whatsapp messages before Jun Yi breaks them the next day");
        lpzQuote.add("\n\nJun yi I just finished 2106 do u need it?\n\n"
                    + " - Peize, Whatsapp message after doing the impossible");
        lpzQuote.add("\n\nOOOOOHHHHHHH, Cheong Long!!\n\n"
                    + " - Peize, after realising we were not talking about Jackie Chan");
    }

    /**
     * Add all quotes by Cat into catQuote Arraylist.
     */
    public void initialiseCatQuotes() {
        catQuote.add("\n\nSince we need to include a screenshot of our gui in the ppp, "
                    + "shld we do sth like \nthis? *Photo of well-labelled screenshot*\n\n"
                    + " - Catherine, Whatsapp message after mulling over PPP");
        catQuote.add("\n\nSurprisingly there's no merge conflict\n\n"
                    + " - Catherine, Whatsapp message after merging lmf to master");
        catQuote.add("\n\nI can do it, I'm gonna code later anyways\n\n"
                    + "But if anything I not sure, I just ask yall\n\n"
                    + " - Catherine, Whatsapp message before merging lmf to master");
        catQuote.add("\n\nGuys, I just pulled from the lmf branch but the code cannot run haha\n\n"
                    + " - Catherine, Whatsapp message containing what she thought would be her last words");
        catQuote.add("\n\nI really thought it was going to take only half an hour!\n\n"
                    + " - Catherine, famous remarks on the final rush to submission\n"
                    + " (we ended up spending the whole day)");
        catQuote.add("\n\nCan you guys please follow the format that I've put in the User Guide?\n\n"
                    + " - Catherine, wondering why no one did despite laying everything out clearly");
        catQuote.add("\n\nI eat four to five small meals a day.\n\n"
                    + " - Catherine");
    }

    /**
     * Add all quotes by Jaedon into jaeQuote Arraylist.
     */
    public void initialiseJaeQuotes() {
        jaeQuote.add("\n\nYou are the jewel bro \n\n"
                    + " - Jaedon, Whatsapp reply to Sholihin's suggestion to go Jewel");
        jaeQuote.add("\n\nsure kerry\n\n"
                    + "\n\noh sorry *jerry\n\n"
                    + "\n\nwah your help command\n\n"
                    + "\n\nis better than linux man page i swear\n\n"
                    + "\n\ngood work on that haha\n\n"
                    + " - Jaedon, Whatsapp message full of praise for Peize's comprehensive help\n"
                    + " command");
        jaeQuote.add("\n\nThis is just like what we pm each other sia\n\n"
                    + " - Jaedon, reminiscing about his conversations with Sholihin\n"
                    + " while trying to start a conversation in the Whatsapp group");
        jaeQuote.add("\n\ni can help to add later\n\n"
                    + " - Jaedon, ever helpful Whatsapp message");
    }

    /**
     * Add all quotes by Jun Yi into yjyQuote Arraylist.
     */
    public void initialiseYjyQuotes() {
        yjyQuote.add("\n\nEr guys i will be late today \n\n"
                    + "As in i will be extra late cos i REALLY overslept this time \n\n"
                    + " - Jun Yi, Whatsapp message on the day of pitch \n (but honestly should be a message he sends "
                    + "everyday");
        yjyQuote.add("\n\nHow about Candice? I heard Candice is coming \n\n"
                    + "Candice dick fit in your mouth \n\n"
                    + " - Jun Yi, Whatsapp message to counter 'Joe mama' joke");
        yjyQuote.add("\n\nchoose which one is easier for you\n\n"
                    + " - Jun Yi, Whatsapp message containing his default response to anything to do\n"
                    + " with choices");
        yjyQuote.add("\n\nTake control. COMPal your life.\n\n"
                    + " - Jun Yi, README.md");
        yjyQuote.add("\n\nactually we all no need work so hard you know\n\n"
                    + "we have 235 commits \n\n"
                    + " - Jun Yi, Whatsapp message before he had any idea what was to come");
        yjyQuote.add("\n\nI don't eat lunch.\n\n"
                    + " - Jun Yi");
    }

    /**
     * Add all general quotes into genQuote Arraylist.
     */
    public void initialiseGenQuotes() {
        genQuote.add("");
    }
}
