package com.example.firebaseapp.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.example.firebaseapp.model.Ticket;
import com.example.firebaseapp.service.ReminderReceiver;

public class ReminderUtil {
    public static void scheduleReminder(Context context, Ticket ticket) {
        long remindTime = ticket.getShowTime() - (30 * 60 * 1000);

        if (remindTime < System.currentTimeMillis()) return;

        Intent intent = new Intent(context, ReminderReceiver.class);
        intent.putExtra("movieTitle", ticket.getMovieTitle());
        intent.putExtra("theaterName", ticket.getTheaterName());
        intent.putExtra("seatNumber", ticket.getSeatNumber());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context,
                ticket.getId().hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, remindTime, pendingIntent);
        }
    }
}