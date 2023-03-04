package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;

public class MainActivity extends AppCompatActivity {

    private Button btn;
    private OneTimeWorkRequest workRequest;
    private Context context = this;
    private ImageView imageView;
    public static Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.DownloadImage);
        imageView = (ImageView) findViewById(R.id.Image);

        handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                imageView.setImageBitmap((Bitmap) msg.obj);
            }
        };



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageBitmap(null);
                workRequest = new OneTimeWorkRequest.Builder(ImageGetter.class).build();
                WorkManager.getInstance(context).enqueue(workRequest);
            }
        });
    }


}
