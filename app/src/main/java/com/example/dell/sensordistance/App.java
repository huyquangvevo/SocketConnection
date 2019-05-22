package com.example.dell.sensordistance;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String CHANNEL_DISTANCE = "Channel distance";
    private  static  final  String name = "Channel distance";
    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannels();

    }

    private void createNotificationChannels(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_DISTANCE,
                    name,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("Crush đang tiếp cận bạn");
            channel.setImportance(NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    };
}
