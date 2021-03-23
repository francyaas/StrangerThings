package com.example.strangerthings.model;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.strangerthings.R;

import java.util.List;

public class CharacterRelationAdapter extends RecyclerView.Adapter<CharacterRelationViewHolder>
{
    private final List<Character> characterList;

    public CharacterRelationAdapter(List<Character> characterList)
    {
        this.characterList = characterList;
    }

    @NonNull
    @Override
    public CharacterRelationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.relations_layout, parent, false);

        return new CharacterRelationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterRelationViewHolder holder, int position)
    {

    }

    @Override
    public int getItemCount()
    {
        return characterList.size();
    }
}

class CharacterRelationViewHolder extends RecyclerView.ViewHolder
{
    public CharacterRelationViewHolder(@NonNull View itemView)
    {
        super(itemView);
    }

    public void setCharacter(Character character)
    {

    }
}
