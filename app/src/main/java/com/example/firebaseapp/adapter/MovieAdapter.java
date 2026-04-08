package com.example.firebaseapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.firebaseapp.databinding.ItemMovieBinding;
import com.example.firebaseapp.model.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    public interface OnMovieClick {
        void onClick(Movie movie);
    }

    private final List<Movie> list;
    private final OnMovieClick listener;

    public MovieAdapter(List<Movie> list, OnMovieClick listener) {
        this.list = list;
        this.listener = listener;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemMovieBinding binding = ItemMovieBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = list.get(position);
        holder.binding.tvTitle.setText(movie.getTitle());
        holder.binding.tvDesc.setText(movie.getDescription());
        Glide.with(holder.itemView.getContext()).load(movie.getPosterUrl()).into(holder.binding.imgPoster);

        holder.itemView.setOnClickListener(v -> listener.onClick(movie));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        ItemMovieBinding binding;
        public MovieViewHolder(ItemMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}