package com.example.strangerthings.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.strangerthings.R;

import java.util.List;

public class AssociateCharacterAdapter extends RecyclerView.Adapter<AssociateCharacterViewHolder>
{

    private final List<Character> characters;

    public AssociateCharacterAdapter(List<Character> characters)
    {
        this.characters = characters;
    }

    @NonNull
    @Override
    public AssociateCharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.relations_layout, parent, false);

        return new AssociateCharacterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AssociateCharacterViewHolder holder, int position)
    {
        holder.setCharacter(characters.get(position));
    }

    @Override
    public int getItemCount()
    {
        return characters.size();
    }

}


class AssociateCharacterViewHolder extends RecyclerView.ViewHolder
{
    public AssociateCharacterViewHolder(@NonNull View itemView)
    {
        super(itemView);
    }

    public void setCharacter(Character character)
    {
    }
}

