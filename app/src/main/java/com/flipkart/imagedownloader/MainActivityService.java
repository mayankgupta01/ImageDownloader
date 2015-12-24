package com.flipkart.imagedownloader;

import android.content.Intent;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;


import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

public class MainActivityService extends AppCompatActivity {
    private final String url = "http://images7.alphacoders.com/436/436329.jpg";
    private static String TAG = "MainActivityWithHandler";
    private ImageView imageView;
//    With this implementation, if the activity is terminated and service gives the result, then exception will come
//    As there is nothing to receive it. Solution is to implement a BroadCastReceiver
    ResultReceiver resultReceiver = new ResultReceiver(new Handler()) {
        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            Bitmap image = resultData.getParcelable("IMAGE");
//            Day 4: Pass 1: Getting image from intent
//            imageView.setImageBitmap(image);
//            Day 4: Pass 2: Getting image from shared object
            MyApplication myApp = (MyApplication) getApplication();
            imageView.setImageBitmap(myApp.sharedImage);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
        //        Day 4 : Pass 2 : This can be removed once Task using runnable is used.

    }

    public void download(View view) throws IOException {
        Log.i(TAG, "Inside download method");
        Intent intent = new Intent(this, ImageDownloaderService.class);
        intent.putExtra("RECEIVER", resultReceiver);
        intent.putExtra("IMAGE_URL", url);
        startService(intent);
    }
}
