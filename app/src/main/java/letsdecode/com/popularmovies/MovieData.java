package letsdecode.com.popularmovies;

/**
 * Created by aashi on 13/05/17.
 */

public class MovieData {
    public static final String BASE_URL = "http://image.tmdb.org/t/p/";

    private String imageUrl;
   // private int voteCount;


    public MovieData(String moviePosterUrl) {
        this.imageUrl = moviePosterUrl;

    }

    public String getMoviePosterUrl() {
        return BASE_URL + "w500" + imageUrl;
    }

//    public int getVoteCount() {
//        return voteCount;
//    }


    public String getMovieBackdropUrl() {
        return BASE_URL + "w370" + imageUrl;
    }


}






