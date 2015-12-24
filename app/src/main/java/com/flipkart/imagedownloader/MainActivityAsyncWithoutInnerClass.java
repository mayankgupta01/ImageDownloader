package com.flipkart.imagedownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivityAsyncWithoutInnerClass extends AppCompatActivity {
    private final String url = "http://images7.alphacoders.com/436/436329.jpg";
    private static String TAG = "MainActivityWithHandler";
    private ImageView imageView;
    private ImageDownloaderAsyncTask imageDownloaderAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        //        Day 4 : Pass 2 : This can be removed once Task using runnable is used.

    }

    public void download(View view) throws IOException {
        Log.i(TAG, "Inside download method");
        imageDownloaderAsyncTask = new ImageDownloaderAsyncTask(imageView);
        imageDownloaderAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);

    }
}
