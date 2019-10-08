package object;


import java.util.Date;

public class MovieInfoObject {
    private long mID;
    private String mTitle;
    private Date mReleaseDate;
    private String mSummary;
    private String mPosterPath;
    private String mFullPosterPath;
    private String mBackdropPath;
    private String mFullBackdropPath;
    //private String[] mCastIDs;
    private double mRating;
    private long[] mGenreIDs;


    public MovieInfoObject(long ID, String title, Date date, String summary, double rating, long[] genreIDs, String posterPath, String backdropPath) {
        mID = ID;
        mTitle = title;
        mReleaseDate = date;
        mSummary = summary;
        mRating = rating;
        mPosterPath = posterPath;
        mBackdropPath = backdropPath;
        mGenreIDs = genreIDs;
        //mCastIDs = castID;
    }

    public void setPosterRootPath(String rootPath, String posterSize, String backdropSize) {
        mFullPosterPath = String.format("%s%s%s", rootPath, posterSize, mPosterPath);
        mFullBackdropPath = String.format("%s%s%s", rootPath, posterSize, mBackdropPath);
    }

    public long getID()
    {
        return mID;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public Date getReleaseDate()
    {
        return mReleaseDate;
    }

    public String getSummary()
    {
        return mSummary;
    }

    public double getRating()
    {
        return mRating;
    }

    public long[] getGenreIDs()
    {
        return mGenreIDs;
    }

    public String getFullPosterPath()
    {
        return mFullPosterPath;
    }

    public String getFullBackdropPath()
    {
        return mFullBackdropPath;
    }

    //public String[] getmCastIDs() {
      //  return mCastIDs;
    //}
}
