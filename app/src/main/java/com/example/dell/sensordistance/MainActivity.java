package com.example.dell.sensordistance;

import android.annotation.TargetApi;
import android.app.Notification;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;



public class MainActivity extends AppCompatActivity {

    private NotificationManagerCompat notificationManager;
    private TextView textSocket;
    private EditText ipHost;
    private  EditText portText;
    private  int portSocket = 123;
    private String host = "192.168.43.164";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notificationManager = NotificationManagerCompat.from(this);
        textSocket = findViewById(R.id.info_socket);
        ipHost = findViewById(R.id.ip_host_server);
        portText = findViewById(R.id.port_host_server);
        connectToServer(textSocket);

    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void sendOnChannel(View v){
        pushNotification();
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void pushNotification(){
        String message = textSocket.getText().toString();
        String title = new String("Crush đang muốn tiếp cận bạn");
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
        host = ipHost.getText().toString();
        portSocket = Integer.parseInt(String.valueOf(portText.getText()));
        //textSocket.setText((String) portSocket);

        Thread thread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                Socket socket = null;
                try {
                    socket = new Socket(host,portSocket);
                    textSocket.setText("Socket is clicked");
                    if(socket.isConnected()){
                        textSocket.setText(new String("Connected"));
                    } else {
                        textSocket.setText(new String("Not Connected"));
                    }
                  //  BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                  //  textSocket.setText(in.readLine());
                    //PrintWriter pw = new PrintWriter(socket.getOutputStream(),true);
                    while(true) {
                        BufferedReader inStream = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        textSocket.setText(inStream.readLine());
                        pushNotification();
                    }


                } catch (IOException e) {
                    Log.e("Loi ket noi",e.getMessage());
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }
}
