package com.example.strangerthings;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;

import com.example.strangerthings.model.location.Location;
import com.example.strangerthings.model.location.LocationDatabase;

public class RandomLocationWidget extends AppWidgetProvider
{
    @Override
    public void onUpdate(
            @NonNull Context context,
            @NonNull AppWidgetManager appWidgetManager,
            @NonNull int[] appWidgetIds
    )
    {
        for (int appWidgetId : appWidgetIds)
        {
            new Action(context, appWidgetManager, appWidgetId).run();
        }
    }

    private static class Action
    {
        @NonNull
        private final Context context;

        @NonNull
        private final AppWidgetManager appWidgetManager;

        private final int appWidgetId;

        @NonNull
        private final RemoteViews views;

        public Action(
                @NonNull Context context,
                @NonNull AppWidgetManager appWidgetManager,
                int widgetId
        )
        {
            this.context = context;
            this.appWidgetManager = appWidgetManager;
            this.appWidgetId = widgetId;
            views = new RemoteViews(context.getPackageName(), R.layout.random_location_widget);
        }

        public void run()
        {
            LocationDatabase database = new LocationDatabase(context);

            Location location = database.getRandomLocation();

            displayLocation(location);

            setupButtonEvent();

            setupImageEvent(location.getName());

            appWidgetManager.updateAppWidget(appWidgetId, views);
        }

        private void setupButtonEvent()
        {
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
        }

        private void setupImageEvent(String locationName)
        {
            Intent intent = new Intent(context, LocationListActivity.class);

            intent.putExtra(LocationListActivity.EXTRA_LOCATION_NAME, locationName);

            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context,
                    appWidgetId,
                    intent,
                    PendingIntent.FLAG_ONE_SHOT
            );

            views.setOnClickPendingIntent(R.id.imageViewLocation, pendingIntent);
        }

        private void displayLocation(Location location)
        {
            views.setTextViewText(R.id.textViewLocation, location.getName());

            ImageLoader imageLoader = new ImageLoader();

            imageLoader.downloadAsync(location.getPhotoUrl()).whenComplete((bitmap, error) -> {

                if (bitmap != null)
                {
                    Log.d("nice", "displayLocation: image loaded");
                    views.setImageViewBitmap(R.id.imageViewLocation, bitmap);
                } else
                {
                    Log.e("oof", "updateAppWidget: ", error);
                    views.setImageViewResource(R.id.imageViewLocation, R.drawable.st_geoicon);
                }

                appWidgetManager.updateAppWidget(appWidgetId, views);

            });

        }


    }
}