package com.example.strangerthings.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.strangerthings.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class CharacterRelationAdapter extends RecyclerView.Adapter<CharacterRelationViewHolder>
{
    private final List<Character> characterList;

    private final Context context;

    private final Consumer<Character> onClick;

    public CharacterRelationAdapter(Context context,
                                    List<Character> characterList,
                                    Consumer<Character> onClick
                                    )
    {
        this.characterList = characterList;
        this.context = context;
        this.onClick = onClick;
    }

    @NonNull
    @Override
    public CharacterRelationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.relations_layout, parent, false);

        return new CharacterRelationViewHolder(view, context, onClick);
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
}

class CharacterRelationViewHolder extends RecyclerView.ViewHolder
{
    private final ImageView pictureImageView;

    private final TextView nameTextView;
    private final TextView statusAndBirthTextView;

    private final Context context;

    private Character currentCharacter;

    public CharacterRelationViewHolder(@NonNull View itemView,
                                       Context context,
                                       Consumer<Character> onClick)
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

        this.context = context;

        nameTextView = itemView.findViewById(R.id.textViewCharacterRelationName);
        statusAndBirthTextView = itemView.findViewById(R.id.textViewCharacterRelationBornStatus);
        pictureImageView = itemView.findViewById(R.id.imageViewCharacterRelation);

        Objects.requireNonNull(nameTextView);
        Objects.requireNonNull(statusAndBirthTextView);
    }

    private void downloadImage(URL url)
    {
        new Thread(() -> {

            try
            {
                URLConnection connection = url.openConnection();

                connection.connect();

                InputStream stream = connection.getInputStream();

                Bitmap bitmap = BitmapFactory.decodeStream(stream);


                new Handler(Looper.getMainLooper()).post(
                        () -> pictureImageView.setImageBitmap(bitmap)
                );

            } catch (IOException ex)
            {
                Log.e("oof", "downloadImage: ", ex);
            }

        }).start();
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
