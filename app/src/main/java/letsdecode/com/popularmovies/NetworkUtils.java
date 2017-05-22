package letsdecode.com.popularmovies;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by aashi on 14/05/17.
 */

public class NetworkUtils {
    final static String BASE_URL = "https://api.themoviedb.org/";
    final static String API_VERSION = "3/";
    final static String END_POINT_POPULAR = "movie/popular";
    final static String END_POINT_TOP_RATED = "movie/top_rated";

    final static String API_KEY_VALUE = "8a9d26b445a4a51d18e9815214036acc";
    final static String API_KEY = "api_key";

    final static String MOVIE_BASE_URL = BASE_URL + API_VERSION;


    //https://api.themoviedb.org/3/movie/popular?api_key=8a9d26b445a4a51d18e9815214036acc
    final static String MOVIE_POPULAR_URL =
            MOVIE_BASE_URL + END_POINT_POPULAR;

    final static String MOVIE_RATED_URL =
            MOVIE_BASE_URL
                    + END_POINT_TOP_RATED + API_KEY + "=" + API_KEY_VALUE;




    private static Uri.Builder createUrlBuilder(String suffix) {
        return Uri.parse(MOVIE_BASE_URL + suffix).buildUpon()
                .appendQueryParameter(API_KEY, API_KEY_VALUE);
    }

    /**
     * Builds the URL used to query GitHub.
     *
     * @return The URL to use to query the GitHub.
     */
    public static URL buildUrl() {

        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon()
                .appendQueryParameter(API_KEY, API_KEY_VALUE)

                //.appendQueryParameter(PARAM_SORT, sortBy)
                .build();

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    //    private static formUrl()
    public static URL buildPopularMovies() {
        Uri.Builder builder = createUrlBuilder(END_POINT_POPULAR);
        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;

    }

    public static URL buildTopRated() {
        Uri.Builder builder = createUrlBuilder(END_POINT_TOP_RATED);
        URL url = null;
        try {
            url = new URL(builder.build().toString());
//            "http://api.themoviedb.org/3/movie/popular?api_key=8a9d26b445a4a51d18e9815214036acc"
//             https://api.themoviedb.org/3/movie/popular/?api_key=8a9d26b445a4a51d18e9815214036acc
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;

    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static ArrayList<MovieData> getResponseFromHttpUrl(URL url) throws IOException {
        ArrayList<MovieData> list = new ArrayList<>();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        String[] posterPathArray;

        try {

            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.addRequestProperty("Content-Type", "application/json"); // Required to get TMDB to play nicely.
            conn.setDoInput(true);
            conn.connect();
            int responseCode = conn.getResponseCode();
            Log.d("NetworkUtils", "The response code is: " + responseCode + " " + conn.getResponseMessage());

            InputStream in = conn.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();


            if (hasInput) {
                try {

                    JSONObject movieJSON = new JSONObject(scanner.next());
                    JSONArray resultsArray = movieJSON.getJSONArray("results");
                    Log.d("RESULT", resultsArray.toString());
                    posterPathArray = new String[resultsArray.length()];
                    for (int i = 0; i < resultsArray.length(); i++) {
                        JSONObject arrayObject = resultsArray.getJSONObject(i);
                        Log.d("ArrayObject", arrayObject.toString());
                        String posterPath = arrayObject.getString("poster_path");
                        posterPathArray[i] = posterPath;
                        list.add(new MovieData(posterPath));
                        Log.d("JSON", posterPath);
                    }
                    return list;
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                return list;
            }
        } finally {
            conn.disconnect();
        }
        return list;

    }
}
