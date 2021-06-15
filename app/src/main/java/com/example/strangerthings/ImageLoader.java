package com.example.strangerthings;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ImageLoader
{
    private final Executor executor;

    public ImageLoader()
    {
        executor = Executors.newCachedThreadPool();
    }

    @NonNull
    public Bitmap download(@NonNull URL url) throws IOException
    {
        URLConnection connection = url.openConnection();

        connection.setConnectTimeout(1000);

        connection.setReadTimeout(1000);

        connection.connect();

        Bitmap result = BitmapFactory.decodeStream(connection.getInputStream());

        if (result == null)
        {
            throw new IOException("Invalid bitmap");
        }

        return result;
    }

    @NonNull
    public CompletionStage<Bitmap> downloadAsync(@NonNull URL url)
    {
        CompletableFuture<Bitmap> future = new CompletableFuture<>();

        executor.execute(() -> {

            try
            {
                Bitmap bitmap = download(url);

                new Handler(Looper.getMainLooper()).post(
                        () -> future.complete(bitmap)
                );
            }
            catch (IOException ex)
            {
                Log.e("oof", "downloadAsync: ", ex);

                future.completeExceptionally(ex);
            }

        });

        return future;
    }

}
