package com.flipkart.imagedownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by mayank.gupta on 17/12/15.
 */
public class ImageDownloaderAsyncTask extends AsyncTask<String, Void, Bitmap> {

    ImageView imageView;

    public ImageDownloaderAsyncTask(ImageView imageView) {
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

    public Bitmap downloadImage(String url) throws IOException {
//        Log.i(TAG, "Inside downloadImage method");
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

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);

        if(bitmap != null){
            imageView.setImageBitmap(bitmap);
        }
    }
}
