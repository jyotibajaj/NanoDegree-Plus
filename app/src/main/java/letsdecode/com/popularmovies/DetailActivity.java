package letsdecode.com.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_DATA_KEY = "movieDataKey";

    public static Intent createIntent(Activity activity, final MovieData movieData) {
        Intent i = new Intent(activity, DetailActivity.class);
        i.putExtra(DetailActivity.MOVIE_DATA_KEY, movieData);
        return i;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //initializing the views
        ImageView tv_PosterImageView = (ImageView) findViewById(R.id.selected_poster_image);
        TextView dateTextView = (TextView) findViewById(R.id.tv_release_date);
        TextView titleTextView = (TextView) findViewById(R.id.tv_title);
        TextView voteTextView = (TextView) findViewById(R.id.tv_vote);
        TextView overviewTextView = (TextView) findViewById(R.id.tv_overview);


        //receive data from intent
        Intent i = getIntent();
        MovieData movieData = i.getParcelableExtra(MOVIE_DATA_KEY);



        //setting the data into views
        dateTextView.setText(movieData.getRelease_date());
        titleTextView.setText(movieData.getTitle());
        String voteAverage = movieData.getVote_average() + "";
        voteTextView.setText(voteAverage);
        overviewTextView.setText(movieData.getOverview());
        Picasso.with(getApplicationContext()).load(movieData.getMoviePosterUrl()).into(tv_PosterImageView);


    }
}
