package com.example.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class PopularMoviesAdapter extends RecyclerView.Adapter<PopularMoviesAdapter.MoviesViewHolder> {

    private ArrayList<Movie> moviesData;
    private final PMadapterOnClickHandler mClickHandler;

    //public PopularMoviesAdapter(){}

    public interface PMadapterOnClickHandler{
        void onClick(Movie selectedMovie);
    }
    public PopularMoviesAdapter(PMadapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    public class MoviesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public final ImageView mMovieImageView;


        public MoviesViewHolder(@NonNull View itemView) {
            super(itemView);
            mMovieImageView = itemView.findViewById(R.id.image_iv);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v){
            int adapterPosition = getAdapterPosition();
            Movie selectedMovie = moviesData.get(adapterPosition);
            mClickHandler.onClick(selectedMovie);
        }
    }


    @NonNull
    @Override
    public MoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachedToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem,viewGroup,attachedToParentImmediately);

        return new MoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesViewHolder holder, int position) {
        Movie aMovie = moviesData.get(position);
        String aMoviePoster = aMovie.getMovie_poster();
        String pathToPoster = "https://image.tmdb.org/t/p/w185"+aMoviePoster;

        Picasso.get().load(pathToPoster)
                .fit()
                .into(holder.mMovieImageView);


    }

    @Override
    public int getItemCount() {
        if (moviesData==null)
        return 0;

        return moviesData.size();
    }

    public void setMoviesData(ArrayList<Movie> moviesData){
        this.moviesData = moviesData;
        notifyDataSetChanged();
    }

}
