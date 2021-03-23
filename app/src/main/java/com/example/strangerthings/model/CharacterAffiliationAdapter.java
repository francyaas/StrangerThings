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

    private final List<Character> characters;

    public CharacterAffiliationAdapter(List<Character> characters)
    {
        this.characters = characters;
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
        holder.setCharacter(characters.get(position));
    }

    @Override
    public int getItemCount()
    {
        return characters.size();
    }

}


class CharacterAffiliationViewHolder extends RecyclerView.ViewHolder
{
    public CharacterAffiliationViewHolder(@NonNull View itemView)
    {
        super(itemView);
    }

    public void setCharacter(Character character)
    {
    }
}

