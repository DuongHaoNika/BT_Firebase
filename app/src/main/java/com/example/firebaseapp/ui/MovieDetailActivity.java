package com.example.firebaseapp.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.firebaseapp.databinding.ActivityMovieDetailBinding;
import com.example.firebaseapp.model.Showtime;
import com.example.firebaseapp.model.Ticket;
import com.example.firebaseapp.model.Theater;
import com.example.firebaseapp.utils.FirebaseUtil;
import com.example.firebaseapp.utils.ReminderUtil;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class MovieDetailActivity extends AppCompatActivity {
    private ActivityMovieDetailBinding binding;
    private String movieId, movieTitle, movieDesc, moviePoster;
    private final List<Showtime> showtimes = new ArrayList<>();
    private final List<String> showtimeDisplay = new ArrayList<>();
    private Theater selectedTheater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMovieDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        movieId = getIntent().getStringExtra("movieId");
        movieTitle = getIntent().getStringExtra("movieTitle");
        movieDesc = getIntent().getStringExtra("movieDesc");
        moviePoster = getIntent().getStringExtra("moviePoster");

        binding.tvMovieTitle.setText(movieTitle);
        binding.tvMovieDesc.setText(movieDesc);
        Glide.with(this).load(moviePoster).into(binding.imgPoster);

        loadFirstTheaterAndShowtimes();

        binding.btnBook.setOnClickListener(v -> bookTicket());
    }

    private void loadFirstTheaterAndShowtimes() {
        FirebaseUtil.db().collection("theaters").limit(1).get()
                .addOnSuccessListener(snapshot -> {
                    if (!snapshot.isEmpty()) {
                        selectedTheater = snapshot.getDocuments().get(0).toObject(Theater.class);
                        binding.tvTheater.setText(selectedTheater.getName() + " - " + selectedTheater.getAddress());
                        loadShowtimes();
                    }
                });
    }

    private void loadShowtimes() {
        FirebaseUtil.db().collection("showtimes")
                .whereEqualTo("movieId", movieId)
                .get()
                .addOnSuccessListener(snapshot -> {
                    showtimes.clear();
                    showtimeDisplay.clear();

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                    for (var doc : snapshot.getDocuments()) {
                        Showtime s = doc.toObject(Showtime.class);
                        if (s != null) {
                            showtimes.add(s);
                            showtimeDisplay.add(sdf.format(new Date(s.getStartTime())) + " - " + s.getRoom());
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(
                            this,
                            android.R.layout.simple_spinner_dropdown_item,
                            showtimeDisplay
                    );
                    binding.spinnerShowtimes.setAdapter(adapter);
                });
    }

    private void bookTicket() {
        if (FirebaseUtil.auth().getCurrentUser() == null) {
            Toast.makeText(this, "Bạn chưa đăng nhập", Toast.LENGTH_SHORT).show();
            return;
        }

        String seat = binding.edtSeat.getText().toString().trim();
        if (TextUtils.isEmpty(seat)) {
            Toast.makeText(this, "Nhập số ghế", Toast.LENGTH_SHORT).show();
            return;
        }

        if (showtimes.isEmpty()) {
            Toast.makeText(this, "Chưa có suất chiếu", Toast.LENGTH_SHORT).show();
            return;
        }

        Showtime selectedShowtime = showtimes.get(binding.spinnerShowtimes.getSelectedItemPosition());
        String ticketId = UUID.randomUUID().toString();
        String uid = FirebaseUtil.auth().getCurrentUser().getUid();

        Ticket ticket = new Ticket(
                ticketId,
                uid,
                movieId,
                movieTitle,
                selectedTheater != null ? selectedTheater.getName() : "Unknown theater",
                selectedShowtime.getId(),
                selectedShowtime.getStartTime(),
                seat,
                System.currentTimeMillis()
        );

        FirebaseUtil.db().collection("tickets").document(ticketId)
                .set(ticket)
                .addOnSuccessListener(unused -> {
                    ReminderUtil.scheduleReminder(this, ticket);
                    FirebaseMessaging.getInstance().subscribeToTopic("showtime_" + selectedShowtime.getId());

                    Toast.makeText(this, "Đặt vé thành công", Toast.LENGTH_LONG).show();
                    finish();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}