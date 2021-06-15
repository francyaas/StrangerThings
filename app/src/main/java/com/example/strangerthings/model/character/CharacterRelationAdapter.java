package com.example.strangerthings.model.character;

import android.content.Context;
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
import java.util.function.Consumer;

public class CharacterRelationAdapter
        extends RecyclerView.Adapter<CharacterRelationAdapter.CharacterRelationViewHolder>
{
    private final List<Character> characterList;

    private final Context context;

    private final Consumer<Character> onClick;

    private final ImageLoader imageLoader;

    public CharacterRelationAdapter(
            @NonNull Context context,
            @NonNull List<Character> characterList,
            @NonNull Consumer<Character> onClick
    )
    {
        this.context = context;
        this.characterList = characterList;
        this.onClick = onClick;
        this.imageLoader = new ImageLoader();
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
        holder.setCharacter(characterList.get(position));
    }

    @Override
    public int getItemCount()
    {
        return characterList.size();
    }


    class CharacterRelationViewHolder extends RecyclerView.ViewHolder
    {
        private final ImageView pictureImageView;

        private final TextView nameTextView;
        private final TextView statusAndBirthTextView;

        private Character currentCharacter;

        public CharacterRelationViewHolder(@NonNull View itemView)
        {
            super(itemView);

            if (onClick != null)
            {
                itemView.setOnClickListener(itself -> {

                    if (currentCharacter != null)
                    {
                        onClick.accept(currentCharacter);
                    }
                });
            }

            nameTextView = itemView.findViewById(R.id.textViewCharacterRelationName);
            statusAndBirthTextView = itemView.findViewById(R.id.textViewCharacterRelationBornStatus);
            pictureImageView = itemView.findViewById(R.id.imageViewCharacterRelation);

            Objects.requireNonNull(nameTextView);
            Objects.requireNonNull(statusAndBirthTextView);
        }

        private void downloadImage(URL url)
        {
            imageLoader.downloadAsync(url).thenAccept(pictureImageView::setImageBitmap);
        }

        public void setCharacter(Character character)
        {
            this.currentCharacter = character;

            nameTextView.setText(character.getName());

            statusAndBirthTextView
                    .setText(String.format(context.getString(R.string.status_born),
                            character.getStatus(), character.getBirthYear()));

            downloadImage(character.getPhotoUrl());
        }
    }
}
