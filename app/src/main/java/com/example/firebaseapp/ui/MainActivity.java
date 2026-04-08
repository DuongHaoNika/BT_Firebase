package com.example.firebaseapp.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firebaseapp.adapter.MovieAdapter;
import com.example.firebaseapp.databinding.ActivityMainBinding;
import com.example.firebaseapp.model.Movie;
import com.example.firebaseapp.utils.FirebaseUtil;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private final List<Movie> movieList = new ArrayList<>();
    private MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        askNotificationPermission();

        adapter = new MovieAdapter(movieList, movie -> {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra("movieId", movie.getId());
            intent.putExtra("movieTitle", movie.getTitle());
            intent.putExtra("movieDesc", movie.getDescription());
            intent.putExtra("moviePoster", movie.getPosterUrl());
            startActivity(intent);
        });

        binding.recyclerMovies.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerMovies.setAdapter(adapter);

        binding.btnMyTickets.setOnClickListener(v ->
                startActivity(new Intent(this, MyTicketsActivity.class)));

        binding.btnLogout.setOnClickListener(v -> {
            FirebaseUtil.auth().signOut();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        updateFcmToken();
        loadMovies();
    }

    private void loadMovies() {
        FirebaseUtil.db().collection("movies")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    movieList.clear();
                    for (var doc : queryDocumentSnapshots.getDocuments()) {
                        Movie movie = doc.toObject(Movie.class);
                        if (movie != null) movieList.add(movie);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }

    private void updateFcmToken() {
        if (FirebaseUtil.auth().getCurrentUser() == null) return;
        String uid = FirebaseUtil.auth().getCurrentUser().getUid();

        FirebaseMessaging.getInstance().getToken().addOnSuccessListener(token ->
                FirebaseUtil.db().collection("users").document(uid)
                        .update("fcmToken", token));
    }

    private void askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                    != PackageManager.PERMISSION_GRANTED) {
                registerForActivityResult(
                        new ActivityResultContracts.RequestPermission(),
                        isGranted -> {}
                ).launch(Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }
}