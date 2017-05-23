package letsdecode.com.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    ImageView tv_PosterImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent i = getIntent();
        String date = i.getStringExtra("date");
        String title = i.getStringExtra("title");
        double vote = i.getDoubleExtra("vote", 0);
        String overview = i.getStringExtra("overview");
        String poster_url = i.getStringExtra("poster_image");
        tv_PosterImageView = (ImageView) findViewById(R.id.selected_poster_image);
        TextView dateTextView = (TextView) findViewById(R.id.tv_release_date);
        TextView titleTextView = (TextView) findViewById(R.id.tv_title);
        TextView voteTextView = (TextView) findViewById(R.id.tv_vote);
        TextView overviewTextView = (TextView) findViewById(R.id.tv_overview);
        dateTextView.setText(date);
        titleTextView.setText(title);
        String voteAverage = vote + "";
        voteTextView.setText(voteAverage);
        overviewTextView.setText(overview);
        Picasso.with(getApplicationContext()).load(poster_url).into(tv_PosterImageView);


    }
}
