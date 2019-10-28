package entertainment.pro.logic.parsers.commands;

import entertainment.pro.commons.enums.COMMANDKEYS;
import entertainment.pro.commons.exceptions.Exceptions;
import entertainment.pro.logic.parsers.CommandParser;
import entertainment.pro.storage.user.Blacklist;
import entertainment.pro.storage.user.WatchlistHandler;
import entertainment.pro.ui.Controller;
import entertainment.pro.ui.MovieHandler;
import entertainment.pro.logic.parsers.CommandStructure;
import entertainment.pro.logic.parsers.CommandSuper;
import entertainment.pro.model.PastCommandStructure;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewCommand extends CommandSuper {
    private Controller controller;

    public ViewCommand(Controller uicontroller) {
        super(COMMANDKEYS.view, CommandStructure.cmdStructure.get(COMMANDKEYS.view), uicontroller);
    }

    @Override
    public void executeCommands() throws Exceptions {
        switch (this.getSubRootCommand()) {
        case watchlist:
            WatchlistHandler.print_list((MovieHandler) (this.getUiController()));
            break;
        case blacklist:
            ((MovieHandler) this.getUiController()).setFeedbackText(Blacklist.printList());
            break;
        case movies:
            executeMovieCommands();
            break;
        case tv:
            executeTvCommands();
            break;
        case back:
            executeBackCommands();
            break;
        case entry:
            executeEntryCommands(Integer.parseInt(getPayload()));
            break;
        default:
            break;
        }
    }

    private void executeEntryCommands(int num) throws Exceptions {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String now = formatter.format(date);
        //String payload = getPayload();
        //int num = Integer.parseInt(payload);
        System.out.println("this is num +" + num);
        ((MovieHandler) this.getUiController()).showMovie(num);
        if (!(((MovieHandler) this.getUiController()).isViewBack())) {
            ((MovieHandler) this.getUiController()).updatePastCommands(now);
        }


    }

    private void executeBackCommands() throws Exceptions {
        PastCommandStructure pastCommandStructure =
                ((MovieHandler) this.getUiController()).getPastCommands().getMap().get(
                ((MovieHandler) this.getUiController()).getPastCommands().getMap().size() - 2);
        String command = pastCommandStructure.getQuery();
        String[] getStrips = command.split(" ");
        System.out.println("this is past command " + command);
        ((MovieHandler) this.getUiController()).setViewBack(true);

        if (command.startsWith("view entry")) {
            //System.out.println("riyazzz");
            ((MovieHandler) this.getUiController()).setViewBackMoreInfo(true);
            String pastCommand = ((MovieHandler) this.getUiController()).getPastCommands().getMap().get(
                    ((MovieHandler) this.getUiController()).getPastCommands().getMap().size() - 3).getQuery();
            System.out.println("this is past command " + pastCommand);

            try {
                CommandParser.parseCommands(pastCommand, ((MovieHandler) this.getUiController()));
            } catch (IOException | Exceptions e) {
                e.printStackTrace();
            }
            //executeEntryCommands(num);
        } else {
            try {
                CommandParser.parseCommands(command, ((MovieHandler) this.getUiController()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void executeMovieCommands() throws Exceptions {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String now = formatter.format(date);
        String payload = getPayload();
        if (payload.equals("current")) {
            //  ((MovieHandler) this.getUIController()).getAPIRequester().beginMovieRequest(
            //        RetrieveRequest.MoviesRequestType.CURRENT_MOVIES,
            //       ((MovieHandler) this.getUIController()).getUserProfile().isAdult()
            // )
            //;
            ((MovieHandler) this.getUiController()).showCurrentMovies();
        } else if (payload.equals("upcoming")) {
            // ((MovieHandler) this.getUIController()).getAPIRequester().beginMovieRequest(
            //       RetrieveRequest.MoviesRequestType.UPCOMING_MOVIES,
            //      ((MovieHandler) this.getUIController()).getUserProfile().isAdult()
            //);
            ((MovieHandler) this.getUiController()).showUpcomingMovies();
        } else if (payload.equals("trending")) {
            ((MovieHandler) this.getUiController()).showTrendMovies();
        } else if (payload.equals("popular")) {
            ((MovieHandler) this.getUiController()).showPopMovies();
        }
        if (!(((MovieHandler) this.getUiController()).isViewBack())) {
            ((MovieHandler) this.getUiController()).updatePastCommands(now);
        }
    }

    private void executeTvCommands() throws Exceptions {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String now = formatter.format(date);
        String payload = getPayload();
        if (payload.equals("current")) {
            ((MovieHandler) this.getUiController()).showCurrentTV();
            // movieHandler.setFeedbackText(DukeException.);
        } else if (payload.equals("upcoming")) {
            ((MovieHandler) this.getUiController()).showUpcomingTV();
        } else if (payload.equals("trending")) {
            ((MovieHandler) this.getUiController()).showTrendTV();
        } else if (payload.equals("popular")) {
            ((MovieHandler) this.getUiController()).showPopTV();
        }
        if (!(((MovieHandler) this.getUiController()).isViewBack())) {
            ((MovieHandler) this.getUiController()).updatePastCommands(now);
        }
    }
}
