package com.example.dell.sensordistance;

import android.annotation.TargetApi;
import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;
    private TextView textSocket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);
        textSocket = findViewById(R.id.info_socket);
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendOnChannel(View v){
        String message = textSocket.getText().toString();
        String title = new String("Nguyen Quang Huy");
        Notification notification = new Notification.Builder(MainActivity.this)
                .setChannelId(App.CHANNEL_DISTANCE)
                .setSmallIcon(R.drawable.ic_drive_eta_black_24dp)
                .setContentTitle(title)
                .setContentText(message)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1,notification);

    }

    public  void connectToServer(View v) {
        textSocket.setText(new String("Quang Huy"));

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket = null;
                try {
                    socket = new Socket("192.168.0.101",6000);
                    PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
                    textSocket.setText(pw.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(socket.isConnected()){
                    textSocket.setText(new String("Connected"));
                } else {
                    textSocket.setText(new String("Not Connected"));
                }
            }
        });

        thread.start();
    }
}
