package com.example.firebaseapp.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.firebaseapp.adapter.TicketAdapter;
import com.example.firebaseapp.databinding.ActivityMyTicketsBinding;
import com.example.firebaseapp.model.Ticket;
import com.example.firebaseapp.utils.FirebaseUtil;

import java.util.ArrayList;
import java.util.List;

public class MyTicketsActivity extends AppCompatActivity {
    private ActivityMyTicketsBinding binding;
    private final List<Ticket> ticketList = new ArrayList<>();
    private TicketAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyTicketsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        adapter = new TicketAdapter(ticketList);
        binding.recyclerTickets.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerTickets.setAdapter(adapter);

        loadTickets();
    }

    private void loadTickets() {
        String uid = FirebaseUtil.auth().getCurrentUser().getUid();

        FirebaseUtil.db().collection("tickets")
                .whereEqualTo("userId", uid)
                .get()
                .addOnSuccessListener(snapshot -> {
                    ticketList.clear();
                    for (var doc : snapshot.getDocuments()) {
                        Ticket ticket = doc.toObject(Ticket.class);
                        if (ticket != null) ticketList.add(ticket);
                    }
                    adapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}