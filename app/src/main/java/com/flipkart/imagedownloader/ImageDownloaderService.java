package com.flipkart.imagedownloader;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class ImageDownloaderService extends IntentService {
    private static final String TAG = "ImageDownloaderService";

    public ImageDownloaderService() {
        super("ImageDownloaderService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            String url = intent.getStringExtra("IMAGE_URL");
            ResultReceiver resultReceiver = intent.getParcelableExtra("RECEIVER");
            Bitmap image = null;
            try {
                image = downloadImage(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (image != null) {
                Log.i(TAG, "Image Downloaded successfully!!");
                Bundle bundle = new Bundle();
//                Day 4 : Pass 1: sharing image via intent
//                bundle.putParcelable("IMAGE", image);
//                resultReceiver.send(101, bundle);

                MyApplication myApp = (MyApplication) getApplication();
                myApp.sharedImage = image;
                resultReceiver.send(101, bundle);
            }
        }
    }

    public Bitmap downloadImage(String url) throws IOException {
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
