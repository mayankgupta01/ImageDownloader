package com.flipkart.imagedownloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import java.util.logging.LogRecord;

public class MainActivityWithHandler extends AppCompatActivity {
    //    Day 4: Pass 2 : Fixing the memory leak problem
    private static class MyHandler extends Handler {
        private ImageView imageView;

        public MyHandler(ImageView imageView) {
            this.imageView = imageView;
        }
//      Day 4 : Pass 3: Don't need this overridden method once task is implement using runnable
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
//            Day 4: Pass 3: This code can be done away with, by using task in the handler in download method.
//            Bundle bundle = msg.getData();
//            Bitmap image = bundle.getParcelable("IMAGE");
//            imageView.setImageBitmap(image);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);
//        Day 4 : Pass 2 : This can be removed once Task using runnable is used.
        handler = new MyHandler(imageView);
    }

    private final String url = "http://images7.alphacoders.com/436/436329.jpg";
    private static String TAG = "MainActivityWithHandler";
    private ImageView imageView;
    private MyHandler handler;
//    private Handler handler = new Handler() {
////      Day 4 : Pass 1 : Following code has a memory leak problem. This is fixed in 2nd pass by making
////        Handler class as static class. As static class does not have a strong relationship with outer class
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
////          Day 4 : Pass 1: passing messages between threads using Handler
//            Bundle bundle = msg.getData();
//            Bitmap image = bundle.getParcelable("IMAGE");
//            imageView.setImageBitmap(image);
//        }
//    };

    public void download(View view) throws IOException {
//      Day 4 : Pass 1:
//       Bitmap image = downloadImage(url);
//        if (image !=null) {
//            Log.i(TAG, "Downloaded image successfully");
//        }

//        Day 4: Pass 2: Using background thread using runnable interface Java API.
        Log.i(TAG, "Inside download method");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    final Bitmap image = downloadImage(url);
                    if (image != null) {
                        Log.i(TAG, "Downloaded image successfully");

                        Runnable r = new Runnable() {
                            @Override
                            public void run() {
                                imageView.setImageBitmap(image);
                            }
                        };
                        handler.post(r);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

//                Day 4: Pass 2 code: This will go away with handler and task
////              Use factory methods to get a message objects because Android by itself reuses the message objects.
//                    Message msg = Message.obtain();
////              attach image in bundle
//                    Bundle bundle = new Bundle();
//                    bundle.putParcelable("IMAGE", image);
////              attach bundle to message
//                    msg.setData(bundle);
////              send message to Handler
//                    handler.sendMessage(msg);

//               Day 4: Pass 2: This will fail with CalledFromWrongThread exception.
//                    imageView.setImageBitmap(image);
            }
        };
        Thread thread = new Thread(r);
        thread.start();

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
