package com.example.firebaseapp.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.firebaseapp.utils.NotificationUtil;

public class ReminderReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String movieTitle = intent.getStringExtra("movieTitle");
        String theaterName = intent.getStringExtra("theaterName");
        String seatNumber = intent.getStringExtra("seatNumber");

        NotificationUtil.showNotification(
                context,
                "Sắp đến giờ chiếu",
                movieTitle + " - " + theaterName + " - Ghế " + seatNumber,
                (int) System.currentTimeMillis()
        );
    }
}