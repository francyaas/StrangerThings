package com.example.strangerthings.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.strangerthings.ImageLoader;
import com.example.strangerthings.R;
import com.example.strangerthings.model.location.Location;

import java.net.URL;
import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder>
{
    private final List<Location> locations;

    public LocationAdapter(@NonNull List<Location> locations)
    {
        this.locations = locations;
    }

    private final ImageLoader imageLoader = new ImageLoader();

    @NonNull
    @Override
    public LocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View locationView = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.location_layout, parent, false);

        return new LocationViewHolder(locationView);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationViewHolder holder, int position)
    {
        Location location = locations.get(position);

        holder.bindToLocation(location);
    }

    @Override
    public int getItemCount()
    {
        return locations.size();
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder
    {
        TextView nameTextView;
        ImageView imageView;

        public LocationViewHolder(@NonNull View itemView)
        {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.textViewCardLocationName);
            imageView = itemView.findViewById(R.id.imageViewCardLocation);
        }

        public void bindToLocation(@NonNull Location location)
        {
            nameTextView.setText(location.getName());

            saveImage(location.getPhotoUrl());
        }

        private void saveImage(@NonNull URL url)
        {
            imageLoader.downloadAsync(url).thenAccept(imageView::setImageBitmap);
        }
    }
}

