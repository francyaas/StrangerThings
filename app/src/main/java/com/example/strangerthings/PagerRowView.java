package com.example.strangerthings;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Objects;

public class PagerRowView extends ConstraintLayout
{
    private final ImageView imageView;
    private final TextView topTextView;
    private final TextView bottomTextView;


    public PagerRowView(@NonNull Context context)
    {
        this(context, null);
    }

    public PagerRowView(@NonNull Context context, @Nullable AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public PagerRowView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        this(context, attrs, defStyleAttr, 0);
    }

    public PagerRowView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes)
    {
        super(context, attrs, defStyleAttr, defStyleRes);

        LayoutInflater.from(context).inflate(R.layout.pager_row_view, this);

        topTextView =  findViewById(R.id.textViewRowTop);
        bottomTextView = findViewById(R.id.textViewRowBottom);
        imageView = findViewById(R.id.imageViewPagerRow);

        Objects.requireNonNull(topTextView);
        Objects.requireNonNull(bottomTextView);
        Objects.requireNonNull(imageView);

        setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        ));
    }


    @NonNull
    public ImageView getImageView()
    {
        Objects.requireNonNull(imageView);
        return imageView;
    }

    @NonNull
    public TextView getTopTextView()
    {
        Objects.requireNonNull(topTextView);
        return topTextView;
    }

    @NonNull
    public TextView getBottomTextView()
    {
        Objects.requireNonNull(bottomTextView);
        return bottomTextView;
    }
}
