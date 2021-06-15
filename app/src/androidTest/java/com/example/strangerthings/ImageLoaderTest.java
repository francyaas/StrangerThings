package com.example.strangerthings;

import android.graphics.Bitmap;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.Assume.assumeNoException;

public class ImageLoaderTest
{
    URL url;
    ImageLoader imageLoader;
    CountDownLatch lock;

    @Before
    public void before() throws MalformedURLException
    {
        url = new URL("https://i.redd.it/mqxayon694i11.jpg");
        imageLoader = new ImageLoader();
        lock = new CountDownLatch(1);
    }

    @Test
    public void testDownload() throws IOException
    {
        Bitmap bitmap = imageLoader.download(url);

        assertBitmap(bitmap);
    }

    @Test
    public void testDownloadAsync() throws InterruptedException
    {
        AtomicReference<Bitmap> result = new AtomicReference<>();

        imageLoader.downloadAsync(url).thenAccept(bitmap -> {
            result.set(bitmap);
            lock.countDown();
        }).exceptionally(ex -> {
            assumeNoException(ex);
            return null;
        });

        lock.await(2000, TimeUnit.MILLISECONDS);

        assertBitmap(result.get());
    }

    @Test
    public void testError() throws MalformedURLException, InterruptedException
    {
        URL url = new URL("https://duckduckgo.com");

        AtomicReference<Throwable> result = new AtomicReference<>();

        imageLoader.downloadAsync(url).thenAccept(
                bitmap -> fail("No error was found")
        ).exceptionally(error -> {

            result.set(error.getCause());

            lock.countDown();

            return null;
        });

        lock.await(30000, TimeUnit.MILLISECONDS);

        assertThat(result.get(), is(notNullValue()));

        assertThat(result.get(), is(instanceOf(IOException.class)));

    }


    private void assertBitmap(Bitmap bitmap)
    {
        assertThat(bitmap, is(notNullValue()));

        assertThat(bitmap.getByteCount(), is(greaterThanOrEqualTo(78_030)));
    }

}