package com.example.strangerthings.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.strangerthings.R;

import java.util.List;

public class CharacterAffiliationAdapter extends RecyclerView.Adapter<CharacterAffiliationViewHolder>
{

    private final List<String> affiliations;

    public CharacterAffiliationAdapter(List<String> affiliations)
    {
        this.affiliations = affiliations;
    }

    @NonNull
    @Override
    public CharacterAffiliationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.relations_layout, parent, false);

        return new CharacterAffiliationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterAffiliationViewHolder holder, int position)
    {
        holder.bind(affiliations.get(position));
    }

    @Override
    public int getItemCount()
    {
        return affiliations.size();
    }

}


class CharacterAffiliationViewHolder extends RecyclerView.ViewHolder
{
    public CharacterAffiliationViewHolder(@NonNull View itemView)
    {
        super(itemView);
    }

    public void bind(String string)
    {
    }
}

