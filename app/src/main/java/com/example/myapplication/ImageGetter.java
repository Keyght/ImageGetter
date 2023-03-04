package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class ImageGetter extends Worker {

    public ImageGetter(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {
        String str = "https://picsum.photos/200/300";
        Data.Builder data;
        try {
            HttpsURLConnection connection;
            URL url = new URL(str);
            connection = (HttpsURLConnection) url.openConnection();
            connection.connect();
            Bitmap bitmap = BitmapFactory.decodeStream(connection.getInputStream());
            Handler handler = MainActivity.handler;
            Message message = new Message();
            message.obj = bitmap;
            handler.sendMessage(message);
        } catch (MalformedURLException e) {
            Log.d("URL", "Malformed URL");
        } catch (IOException e) {
            Log.d("IO", "Input or Output problem");
        }



        return Worker.Result.success();
    }
}
