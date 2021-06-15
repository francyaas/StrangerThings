package com.example.strangerthings.model.location;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.strangerthings.ImageLoader;
import com.example.strangerthings.R;

import java.net.URL;
import java.util.List;
import java.util.Objects;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder>
{
    private final List<Location> locations;

    public LocationAdapter(@NonNull List<Location> locations)
    {
        this.locations = locations;
    }

    private final ImageLoader imageLoader = new ImageLoader();

    private RecyclerView recyclerView;

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);

        this.recyclerView = recyclerView;
    }

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

    public void scrollByName(@NonNull String locationName) {

        Objects.requireNonNull(recyclerView);

        locations
                .stream()
                .filter(l -> l.getName().equals(locationName))
                .findFirst()
                .ifPresent(
                        location -> recyclerView.scrollToPosition(locations.indexOf(location))
                );

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

