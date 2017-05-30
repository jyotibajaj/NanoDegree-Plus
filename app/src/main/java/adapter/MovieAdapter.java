package letsdecode.com.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aashi on 13/05/17.
 */

public class MovieAdapter extends
        RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    CustomItemClickInterface clickListener;


    // Store a member variable for the contacts
    private List<MovieData> mMovieData;
    // Store the context for easy access
    private Context mContext;
    private int countOnCreateViewHolder = 0;

    public MovieAdapter(Context context, List<MovieData> list) {
        mContext = context;
        this.mMovieData = list;
    }

    public interface CustomItemClickInterface {
        public void onItemClick(View view, int itemClicked);
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Usually involves inflating a layout from XML and returning the holder
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View movieView = inflater.inflate(R.layout.custom_movie_layout, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(movieView);
        countOnCreateViewHolder++;
        Log.i("createViewhOlderCount", countOnCreateViewHolder + "");
        return viewHolder;
    }

    public void setClickListener(CustomItemClickInterface itemClickListener) {
        this.clickListener = itemClickListener;
    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
        // Involves populating data into the item through holder
        // Get the data model based on position
        MovieData movieData = mMovieData.get(position);
        Picasso.with(mContext).load(movieData.getMoviePosterUrl()).into(holder.posterImageView);

    }

    @Override
    public int getItemCount() {
        if (null == mMovieData) return 0;
        return mMovieData.size();
    }

    public void setMovieData(ArrayList<MovieData> movieData) {
        mMovieData.clear();
        mMovieData.addAll(movieData);
        notifyDataSetChanged();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView posterImageView;

        //  constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            super(itemView);
            posterImageView = (ImageView) itemView.findViewById(R.id.poster_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClick(v, getAdapterPosition());
            }

        }
    }
}




