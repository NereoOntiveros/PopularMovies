package com.example.popularmovies.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.model.Review;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder> {

    private ArrayList<Review> reviewsData;

    public class ReviewsViewHolder extends RecyclerView.ViewHolder{
        public final TextView mReviewAuthor;
        public final TextView mReviewContent;

        public ReviewsViewHolder(@NonNull View itemView) {

            super(itemView);

            mReviewAuthor = itemView.findViewById(R.id.review_author);
            mReviewContent = itemView.findViewById(R.id.review_content);
        }
    }

    @NonNull
    @Override
    public ReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.review;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean attachedToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem,parent,attachedToParentImmediately);
        return new ReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsViewHolder holder, int position) {
        Review review = reviewsData.get(position);

        holder.mReviewAuthor.setText(review.getAuthor());
        holder.mReviewContent.setText(review.getContent());
    }

    @Override
    public int getItemCount() {

        if (reviewsData==null)
            return 0;

        return reviewsData.size();
    }
    public void setReviewsData(ArrayList<Review> reviewsData){
        this.reviewsData = reviewsData;
        notifyDataSetChanged();
    }
}
