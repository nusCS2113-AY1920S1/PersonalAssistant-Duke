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
        lpzSet.add("pz");
        catSet.add("cat");
        catSet.add("catherine");
        jaeSet.add("jaedon");
        jaeSet.add("jae");
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
        int randInt = rand.nextInt(10000);
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
            int allSize = allQuotes.size();
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
                    + " - Sholihin, Whatsapp message before consult, clearly indicating that he needs\n to sleep");
        shoQuote.add("\n\nToday need stay back for anything after 2113t?\n\n"
                    +  "If not fuck it\n\n"
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
        shoQuote.add("\n\nBtw evdyeone do your own junit ah\n\n"
                    + "And only merge when Travis and codafy say can\n\n"
                    + "And thanks for today guys\n\n"
                    + "Hate the mod but atleast you guys made it easier\n\n"
                    + " - Sholihin, sentimental Whatsapp message after second code restructure");
        shoQuote.add("\n\nWe late\n\n"
                    + "Bus got into accident\n\n"
                    + " - Sholihin, the most unexpected Whatsapp message before CS2101 class");
        shoQuote.add("\n\nNo, I carry this around in my bag everyday.\n\n"
                    + " - Sholihin, on his Polaroid camera");
        shoQuote.add("\n\nI think we have to stay back one day and restructure our code\n\n"
                    + " - Sholihin, foreboding words before both code restructures");
        shoQuote.add("\n\nCode until cannot think already\n\n"
                    + " - Sholihin, feelings after first code restructure");
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
        lpzQuote.add("\n\nOOOOOHHHHHHH, Cheng Long!!\n\n"
                    + " - Peize, after realising we were not talking about Jackie Chan");
        lpzQuote.add("\n\nOOOOOOOOOOooooooooohhhhhhhhhh!!!\n\n"
                    + " - Peize's trademark exclamation");
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
        catQuote.add("\n\nCan you guys please follow the command syntax that I've put in the User Guide?\n\n"
                    + " - Catherine, wondering why no one did despite laying everything out clearly");
        catQuote.add("\n\nI think the amount of sugar I consume in a day is more than what you\n"
                    + "consume in a month.\n\n"
                    + " - Catherine on the amount of sugar Jun Yi consumes");
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
        jaeQuote.add("\n\nOkay im driving now ttyl\n\n"
                    + " - Jaedon, Whatsapp message telling us he intends to take over the driver of \n"
                    + " the bus after the accident");
        jaeQuote.add("\n\nHey I brought herbal tea for everyone\n\n"
                    + " - Jaedon, much-needed care and affection in hazy weather");
        jaeQuote.add("\n\nI'll book the Discussion Room for the meeting tomorrow\n\n"
                    + " - Jaedon, preparing for weekly team meetings");
    }

    /**
     * Add all quotes by Jun Yi into yjyQuote Arraylist.
     */
    public void initialiseYjyQuotes() {
        yjyQuote.add("\n\nEr guys i will be late today \n\n"
                    + "As in i will be extra late cos i REALLY overslept this time \n\n"
                    + " - Jun Yi, Whatsapp message on the day of pitch \n (but honestly should be a message he sends "
                    + "everyday)");
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
                    + " - Jun Yi, on lifestyle");
        yjyQuote.add("\n\nEh frag already frag already!\n\n"
                    + " - Jun Yi, watching Sholihin zone out while coding at the end of second code\n"
                    + " restructure");
        yjyQuote.add("\n\nGETDATE, GETTIME, GETDATE, GETTIME!!!\n\n"
                    + " - Jun Yi, freaking out during first code restructure");
    }

    /**
     * Add all general quotes into genQuote Arraylist.
     */
    public void initialiseGenQuotes() {
        genQuote.add("\n\nEmpty your memory, with a free(), like a pointer.\n"
                + "If you cast a pointer to a integer, it becomes the integer.\n"
                + "If you cast a pointer to a struct, it becomes the struct.\n"
                + "The pointer can crash, and can overflow.\n"
                + "Be a pointer my friend.\n"
                + " - Dennis Ritchie");
        genQuote.add("\n\nAlways code as if the guy maintaining your code will be a violent psychopath\n"
                + "who knows where you live.\n\n"
                + " - John Woods");
        genQuote.add("\n\nAny fool can write code that a computer can understand.\n"
                + "Good programmers write code that humans can understand.\n\n"
                + " - Martin Fowler");
        genQuote.add("\n\nGive a man a program, frustrate him for a day.\n"
                + "Teach a man how to program, frustrate him for a lifetime.\n\n"
                + " - Muhammad Waseem");
        genQuote.add("\n\nSometimes I'll start a sentence and I don't even know where it's going.\n"
                + "I just hope I find it along the way\n\n"
                + " - Michael Scott");
        genQuote.add("\n\nThere's a lot of beauty in ordinary things. Isn't thant kind of the point?\n\n"
                + " - Pam Beesly");
        genQuote.add("\n\nWhy say lot word when few word do trick?\n\n"
                + " - Kevin Malone");
        genQuote.add("\n\nSoftware is like sex: It's better when it's free.\n\n"
                + " - Linus Torvalds");
        genQuote.add("\n\nTo iterate is human, to recurse divine.\n\n"
                + " - L. Peter Deutsch");
        genQuote.add("\n\nSometimes it pays to stay in bed on Monday, rather than spending the rest\n"
                + "of the week debugging Monday's code."
                + " - Christopher Thompson");
        genQuote.add("\n\nFine, Java MIGHT be a good example of what a programming language should be\n"
                + "like. But Java applications are good examples of what applications SHOULDN'T\n"
                + "be like.\n\n"
                + " - pixadel");
        genQuote.add("\n\nJava is, in many ways, C++--.\n\n"
                + " - Michael Feldman");
        genQuote.add("\n\nIf you fall, I will be there.\n\n"
                +  " - Floor");
        genQuote.add("\n\nOnly 3 things are infinite: \n"
                + " 1. Universe.\n"
                + " 2. Human Stupidity\n"
                + " 3. Winrar's free trial\n"
                + " - Albert Einstein");
        genQuote.add("\n\nSoftware and cathedrals are much the same - first we build them,\n"
                + "then we pray.\n\n"
                + " - Sam Redwine");
    }
}
