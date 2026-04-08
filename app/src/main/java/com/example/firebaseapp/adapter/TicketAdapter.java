package com.example.firebaseapp.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.firebaseapp.databinding.ItemTicketBinding;
import com.example.firebaseapp.model.Ticket;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {
    private final List<Ticket> list;

    public TicketAdapter(List<Ticket> list) {
        this.list = list;
    }

    @Override
    public TicketViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemTicketBinding binding = ItemTicketBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new TicketViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(TicketViewHolder holder, int position) {
        Ticket ticket = list.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        holder.binding.tvMovieTitle.setText(ticket.getMovieTitle());
        holder.binding.tvTheater.setText(ticket.getTheaterName());
        holder.binding.tvSeat.setText("Ghế: " + ticket.getSeatNumber());
        holder.binding.tvShowtime.setText("Suất chiếu: " + sdf.format(new Date(ticket.getShowTime())));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {
        ItemTicketBinding binding;

        public TicketViewHolder(ItemTicketBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}