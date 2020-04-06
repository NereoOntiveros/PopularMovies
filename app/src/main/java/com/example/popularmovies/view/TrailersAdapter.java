package com.example.popularmovies.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Trailer;

import java.util.ArrayList;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewHolder> {

    private ArrayList<Trailer> trailersData;
    private TrailersAdapterOnClickHandler mClickHandler;


    public interface TrailersAdapterOnClickHandler{
        void onClick(Trailer selectedTrailer);
    }
    public TrailersAdapter(TrailersAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }


    public class TrailersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public final ImageButton mTrailerImageButton;
        public final TextView mTrailerTextView;
        public TrailersViewHolder(@NonNull View itemView) {
            super(itemView);
            mTrailerImageButton = itemView.findViewById(R.id.play_arrow);
            mTrailerTextView = itemView.findViewById(R.id.tv_trailer);
            //itemView.setOnClickListener(this);
            mTrailerImageButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Trailer selectedTrailer = trailersData.get(adapterPosition);
            mClickHandler.onClick(selectedTrailer);
        }
    }



    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.play_arrow;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachedToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem,parent,attachedToParentImmediately);
        return new TrailersViewHolder( view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder holder, int position) {
        Trailer aTrailer = trailersData.get(position);
        int trailerNumber = position+1;
        holder.mTrailerTextView.setText("Trailer "+trailerNumber);

    }

    @Override
    public int getItemCount() {
        if (trailersData==null)
            return 0;

        return trailersData.size();
    }
    public void setTrailersData(ArrayList<Trailer> trailersData){
        this.trailersData = trailersData;
        notifyDataSetChanged();
    }
}
