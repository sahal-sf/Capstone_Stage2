package net.sahal.capstone_stage2;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Notification extends AppWidgetProvider {

    private static String[] Exercises = {
            "Chest Exercise",
            "Shoulder Exercise",
            "Tricep Exercise",
            "Leg Exercise",
            "Nothing, just Rest",
            "Back Exercise",
            "Bicep Exercise"};

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.notification);

        String dayName = new SimpleDateFormat("EEEE").format(new Date());
        int dayNumber = Integer.parseInt(new SimpleDateFormat("u").format(new Date()));

        views.setTextViewText(R.id.appwidget_text, context.getResources().getString(
                R.string.appwidget_text, dayName, Exercises[dayNumber - 1]));

        String timeString = DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date());
        views.setTextViewText(R.id.update_value, context.getResources().getString(R.string.time, timeString));

        Intent intentUpdate = new Intent(context, Notification.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new int[]{appWidgetId});
        PendingIntent pendingUpdate = PendingIntent.getBroadcast(context, appWidgetId, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.update, pendingUpdate);

        Intent intentStart = new Intent(context, MainActivity.class);
        PendingIntent pendingStart = PendingIntent.getActivity(context, 0, intentStart, 0);
        views.setOnClickPendingIntent(R.id.appwidget_text, pendingStart);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            Toast.makeText(context, R.string.Widget_updated, Toast.LENGTH_SHORT).show();
        }
    }
}

