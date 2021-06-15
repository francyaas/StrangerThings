package com.example.strangerthings.model;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.example.strangerthings.R;

/**
 * Implementation of App Widget functionality.
 */
public class RandomLocationWidget extends AppWidgetProvider
{
    private static final String SHARED_PREFERENCES_FILE = "com.example.android.strangerthings";
    private static final String COUNT_KEY = "counter";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, int appWidgetId)
    {
        SharedPreferences preferences = context.getSharedPreferences(SHARED_PREFERENCES_FILE, 0);
        int count = preferences.getInt(COUNT_KEY + appWidgetId, 0);
        count++;

        CharSequence widgetText = context.getString(R.string.appwidget_text);

        // GET RANDOM LOCATION GOES HERE


        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.random_location_widget);
        views.setTextViewText(R.id.textViewLocation, widgetText);
        views.setImageViewResource(R.id.imageViewLocation, R.drawable.st_geoicon);

        SharedPreferences.Editor preferencesEditor = preferences.edit();
        preferencesEditor.putInt(COUNT_KEY + appWidgetId, count);
        preferencesEditor.apply();

        Intent intentUpdate = new Intent(context, RandomLocationWidget.class);
        intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);

        int[] idArray = new int[]{appWidgetId};
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);

        PendingIntent pendingUpdate = PendingIntent.getBroadcast
                (
                        context,
                        appWidgetId,
                        intentUpdate,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        views.setOnClickPendingIntent(R.id.buttonRefresh, pendingUpdate);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        for (int appWidgetId : appWidgetIds)
        {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context)
    {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context)
    {
        // Enter relevant functionality for when the last widget is disabled
    }
}