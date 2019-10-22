package commands;

import EPparser.CommandParser;
import EPstorage.Blacklist;
import EPstorage.PastCommands;
import ListCommands.WatchlistHandler;
import MovieUI.Controller;
import MovieUI.MovieHandler;
import exceptions.DukeException;
import movieRequesterAPI.RetrieveRequest;
import object.MovieInfoObject;
import object.PastCommandStructure;
import retractCommands.PastUserCommands;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;


public class ViewCommand extends CommandSuper {
    private Controller controller;

    public ViewCommand(Controller uicontroller) {
        super(COMMANDKEYS.view, CommandStructure.cmdStructure.get(COMMANDKEYS.view), uicontroller);
    }

    @Override
    public void executeCommands() {
        switch (this.getSubRootCommand()) {
            case watchlist:
                WatchlistHandler.print_list((MovieHandler) (this.getUIController()));
                break;
            case blacklist:
                ((MovieHandler) this.getUIController()).setFeedbackText(Blacklist.printList());
                break;
            case movies:
                executeMovieCommands();
                break;
            case tv:
                executeTVCommands();
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

    private void executeEntryCommands(int num) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String now = formatter.format(date);
        //String payload = getPayload();
        //int num = Integer.parseInt(payload);
        System.out.println("this is num +" + num);
        ((MovieHandler) this.getUIController()).showMovie(num);
        if (!(((MovieHandler) this.getUIController()).isViewBack())) {
            ((MovieHandler) this.getUIController()).updatePastCommands(now);
        }


    }

    private void executeBackCommands() {
        PastCommandStructure pastCommandStructure = ((MovieHandler) this.getUIController()).getPastCommands().getMap().get(
                ((MovieHandler) this.getUIController()).getPastCommands().getMap().size() - 2);
        String command = pastCommandStructure.getQuery();
        String[] getStrips = command.split(" ");
        System.out.println("this is past command " + command);
        ((MovieHandler) this.getUIController()).setViewBack(true);

        if (command.startsWith("view entry")) {
            //System.out.println("riyazzz");
            ((MovieHandler) this.getUIController()).setViewBackMoreInfo(true);
            String pastCommand = ((MovieHandler) this.getUIController()).getPastCommands().getMap().get(
                    ((MovieHandler) this.getUIController()).getPastCommands().getMap().size() - 3).getQuery();
            System.out.println("this is past command " + pastCommand);

            try {
                CommandParser.parseCommands(pastCommand, ((MovieHandler) this.getUIController()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //executeEntryCommands(num);
        } else {
            try {
                CommandParser.parseCommands(command, ((MovieHandler) this.getUIController()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


    private void executeMovieCommands() {
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
            ((MovieHandler) this.getUIController()).showCurrentMovies();
        } else if (payload.equals("upcoming")) {
            // ((MovieHandler) this.getUIController()).getAPIRequester().beginMovieRequest(
            //       RetrieveRequest.MoviesRequestType.UPCOMING_MOVIES,
            //      ((MovieHandler) this.getUIController()).getUserProfile().isAdult()
            //);
            ((MovieHandler) this.getUIController()).showUpcomingMovies();
        } else if (payload.equals("trending")) {
            ((MovieHandler) this.getUIController()).showTrendMovies();
        } else if (payload.equals("popular")) {
            ((MovieHandler) this.getUIController()).showPopMovies();
        }
        if (!(((MovieHandler) this.getUIController()).isViewBack())) {
            ((MovieHandler) this.getUIController()).updatePastCommands(now);
        }
    }

    private void executeTVCommands() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date date = new Date();
        String now = formatter.format(date);
        String payload = getPayload();
        if (payload.equals("current")) {
            ((MovieHandler) this.getUIController()).showCurrentTV();
            // movieHandler.setFeedbackText(DukeException.);
        } else if (payload.equals("upcoming")) {
            ((MovieHandler) this.getUIController()).showUpcomingTV();
        } else if (payload.equals("trending")) {
            ((MovieHandler) this.getUIController()).showTrendTV();
        } else if (payload.equals("popular")) {
            ((MovieHandler) this.getUIController()).showPopTV();
        }
        if (!(((MovieHandler) this.getUIController()).isViewBack())) {
            ((MovieHandler) this.getUIController()).updatePastCommands(now);
        }
    }
}
