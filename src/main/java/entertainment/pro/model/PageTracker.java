package entertainment.pro.model;

public class PageTracker {
    private boolean mainPage;
    private boolean playlistList;
    private boolean playlistInfo;
    private boolean playlistMovieInfo;

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

    public void setToPlaylistMovieInfo() {
        mainPage = false;
        playlistList = false;
        playlistInfo = false;
        playlistMovieInfo = true;
    }

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
