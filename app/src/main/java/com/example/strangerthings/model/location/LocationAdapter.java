package com.example.strangerthings.model.location;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.strangerthings.ImageLoader;
import com.example.strangerthings.PagerRowView;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.LocationViewHolder>
{
    private final List<Location> locations;

    private Consumer<Location> clickListener;

    private final ImageLoader imageLoader = new ImageLoader();

    private RecyclerView recyclerView;

    public LocationAdapter(@NonNull List<Location> locations)
    {
        this.locations = locations;
    }

    public void onLocationClick(@NonNull Consumer<Location> clickListener)
    {
        this.clickListener = clickListener;
    }

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
        PagerRowView view = new PagerRowView(parent.getContext());

        view.setHeightType(ViewGroup.LayoutParams.WRAP_CONTENT);

        return new LocationViewHolder(view);
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
        TextView addressTextView;
        ImageView imageView;

        public LocationViewHolder(@NonNull PagerRowView rowView)
        {
            super(rowView);

            nameTextView = rowView.getTopTextView();
            addressTextView = rowView.getBottomTextView();
            imageView = rowView.getImageView();

            rowView.getBottomTextView().setText("");

            rowView.setOnClickListener(v -> {

                if (clickListener != null) {
                    clickListener.accept(locations.get(getAdapterPosition()));
                }

            });
        }

        public void bindToLocation(@NonNull Location location)
        {
            nameTextView.setText(location.getName());
            addressTextView.setText(location.getAddress());

            saveImage(location.getPhotoUrl());
        }

        private void saveImage(@NonNull URL url)
        {
            imageLoader.downloadAsync(url).thenAccept(imageView::setImageBitmap);
        }
    }
}

