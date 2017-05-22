package letsdecode.com.popularmovies;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import static letsdecode.com.popularmovies.NetworkUtils.buildPopularMovies;
import static letsdecode.com.popularmovies.NetworkUtils.buildTopRated;

public class MoviesGridActivity extends AppCompatActivity {

    ArrayList<MovieData> movieDatas = new ArrayList<>();
    MovieAdapter mMovieAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_grid);
        // Lookup the recyclerview in activity layout
        new MoviesQueryTask().execute(buildTopRated());
        RecyclerView rvMovies = (RecyclerView) findViewById(R.id.rvContacts);
        ItemOffSetDecoration itemDecoration = new ItemOffSetDecoration(getApplicationContext(), R.dimen.item_offset);
        rvMovies.addItemDecoration(itemDecoration);
        // Set layout manager to position the items
        rvMovies.setLayoutManager(new GridLayoutManager(this, 2));
        //initialize the adapter
        mMovieAdapter = new MovieAdapter(this, movieDatas);
        // Attach the adapter to the recyclerview to populate items
        rvMovies.setAdapter(mMovieAdapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClicked = item.getItemId();
        if (itemThatWasClicked == R.id.popular) {
            //Toast.makeText(getApplicationContext(), "popular item is clicked", Toast.LENGTH_SHORT).show();
            new MoviesQueryTask().execute(buildPopularMovies());

//            buildPopularMovies();
        }

        else if(itemThatWasClicked == R.id.top_rated) {
            Toast.makeText(getApplicationContext(), "top rated item is clicked", Toast.LENGTH_SHORT).show();
            new MoviesQueryTask().execute(buildTopRated());
        }
//        //rating
//        Collections.sort(movieDatas, new Comparator<MovieData>() {
//            @Override
//            public int compare(MovieData o1, MovieData o2) {
//                return o1.getVoteCount()  - o2.getVoteCount();
//            }
//        });


//        //Polularity
//        Collections.sort(movieDatas, new Comparator<MovieData>() {
//            @Override
//            public int compare(MovieData o1, MovieData o2) {
//                return o1.getPopularity()  - o2.getPopularity();
//            }
//        });
        mMovieAdapter.notifyDataSetChanged();

        return super.onOptionsItemSelected(item);

    }



    public class MoviesQueryTask extends AsyncTask<URL, Void, ArrayList<MovieData>> {

        @Override
        protected ArrayList<MovieData> doInBackground(URL... params) {
            URL searchUrl = params[0];
            ArrayList<MovieData> results = null;
            try {
                results = NetworkUtils.getResponseFromHttpUrl(searchUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return results;
        }

        @Override
        protected void onPostExecute(ArrayList<MovieData> posterPathResultFromNetwork) {
            mMovieAdapter.setMovieData(posterPathResultFromNetwork);
        }
    }

}













