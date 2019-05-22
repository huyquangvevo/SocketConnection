package com.example.dell.sensordistance;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MessageSensor extends AsyncTask<String,Void,Void> {

    Socket socket;
    DataOutputStream dataOutputStream;
    PrintWriter printWriter;


    @Override
    protected Void doInBackground(String... voids) {

        String message = voids[0];

        try {
            socket = new Socket("172.30.69.129",6000);
            printWriter = new PrintWriter(socket.getOutputStream());
          //  printWriter.write(message);

           // printWriter.flush();
           // printWriter.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
