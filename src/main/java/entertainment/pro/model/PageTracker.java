package entertainment.pro.model;

/**
 * to track which page of the gui the user is at to help with refreshing to show live changes users make.
 */
public class PageTracker {
    private boolean mainPage;
    private boolean playlistList;
    private boolean playlistInfo;
    private boolean playlistMovieInfo;

    /**
     * constructor for PageTracker.
     */
    public PageTracker() {
        mainPage = true;
        playlistList = false;
        playlistInfo = false;
        playlistMovieInfo = false;
    }

    public boolean isMainPage() {
        return mainPage;
    }

    public void setMainPage(boolean mainPage) {
        this.mainPage = mainPage;
    }

    /**
     * set current page to MainPage.
     */
    public void setToMainPage() {
        mainPage = true;
        playlistList = false;
        playlistInfo = false;
        playlistMovieInfo = false;
    }

    public boolean isPlaylistList() {
        return playlistList;
    }

    public void setPlaylistList(boolean playlistList) {
        this.playlistList = playlistList;
    }

    /**
     * set current page to PlaylistList.
     */
    public void setToPlaylistList() {
        mainPage = false;
        playlistList = true;
        playlistInfo = false;
        playlistMovieInfo = false;
    }

    public boolean isPlaylistInfo() {
        return playlistInfo;
    }

    public void setPlaylistInfo(boolean playlistInfo) {
        this.playlistInfo = playlistInfo;
    }

    /**
     * set current page to PlaylistInfo.
     */
    public void setToPlaylistInfo() {
        mainPage = false;
        playlistList = false;
        playlistInfo = true;
        playlistMovieInfo = false;
    }

    public boolean isPlaylistMovieInfo() {
        return playlistMovieInfo;
    }

    public void setPlaylistMovieInfo(boolean playlistMovieInfo) {
        this.playlistMovieInfo = playlistMovieInfo;
    }

    /**
     * set current page to PlaylistMovieInfo.
     */
    public void setToPlaylistMovieInfo() {
        mainPage = false;
        playlistList = false;
        playlistInfo = false;
        playlistMovieInfo = true;
    }

    /**
     * to find out which page the gui is currently on.
     */
    public String getCurrentPage() {
        if (mainPage) {
            return "mainPage";
        } else if (playlistList) {
            return "playlistList";
        } else if (playlistInfo) {
            return "playlistInfo";
        } else {
            return "playlistMovieInfo";
        }
    }
}
