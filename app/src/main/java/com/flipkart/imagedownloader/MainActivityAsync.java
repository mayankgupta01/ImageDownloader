package com.flipkart.imagedownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class MainActivityAsync extends AppCompatActivity {
    private final String url = "http://images7.alphacoders.com/436/436329.jpg";
    private static String TAG = "MainActivityWithHandler";
    private ImageView imageView;

    private static class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        ImageView imageView;

        public ImageDownloaderTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                Bitmap image = downloadImage(params[0]);
                return image;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            if(bitmap != null){
                imageView.setImageBitmap(bitmap);
            }
        }

        public Bitmap downloadImage(String url) throws IOException {
            Log.i(TAG, "Inside downloadImage method");
            Bitmap image = null;
//      Day 4: Pass 1 : Use URL object for calls. HTTP client has been deprecated from marshmellow
            URL link = new URL(url);
            URLConnection connection = link.openConnection();

//        Input stream to the server
            InputStream inStr = connection.getInputStream();

//        Download image
            image = BitmapFactory.decodeStream(inStr);

            return image;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
//        Day 4 : Pass 2 : This can be removed once Task using runnable is used.

    }

    public void download(View view) throws IOException {
        Log.i(TAG, "Inside download method");
        ImageDownloaderTask imageDownloaderTask = new ImageDownloaderTask(imageView);
        imageDownloaderTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url);

    }
}
