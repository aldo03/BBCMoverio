package com.example.matteoaldini.bbcmoverio.bluetooth;

import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.regex.Pattern;

import android.os.Handler;
import android.util.Log;

import com.example.matteoaldini.bbcmoverio.utils.ParserUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by brando on 28/05/2015.
 */
public class ConnectedThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;
    private Handler handler;

    public ConnectedThread(BluetoothSocket socket,final Handler handler) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;
        this.handler = handler;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) { }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void run() {
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
                bytes = mmInStream.read(buffer);
                String str = new String(buffer, 0, bytes, "UTF-8");
                JSONObject jsonObject = new JSONObject(str);
                Log.i("RECEIVED", str);
                if(jsonObject.getInt("messageType")!=0){
                    int n = 0;
                    while (n<=str.lastIndexOf("}")) {
                        String s = str.substring(n, str.indexOf("}",n)+1);
                        Log.i("RECEIVED", s);
                        this.receiveMessage(s);
                        n=str.indexOf("}",n)+1;
                    }
                }else {
                   this.receiveMessage(str);
                }
            } catch (IOException e) {
                break;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private Object receiveMessage(String str) throws JSONException {
        JSONObject jsonObject = new JSONObject(str);
        int messageType = -1;
        Object object = null;
        switch (jsonObject.getInt("messageType")) {
            case 0:
                messageType=0;
                object = ParserUtils.getMatchFromJSONObject(jsonObject);
                break;
            case 1:
                messageType=1;
                object = ParserUtils.getTreasureChestFromJSONObject(jsonObject);
                break;
            case 2:
                messageType=2;
                object = ParserUtils.getConfirmedOrRefused(jsonObject);
                break;
            case 3:
                messageType=3;
                object = ParserUtils.getMoneyTheft(jsonObject);
                Log.i("Thief",jsonObject.toString());
                break;
            case 4:
                messageType=4;
                object = ParserUtils.getNewAmount(jsonObject);
                break;
            case 5:
                messageType=5;
                object = ParserUtils.getPosition(jsonObject);
                break;
            case 6:
                messageType=6;
                object = ParserUtils.getTreasureChestFromJSONObject(jsonObject);
                break;
            case 7:
                messageType=7;
                object = ParserUtils.getAlertToShow(jsonObject);
                break;
            case 8:
                messageType=8;
                object = ParserUtils.getMoneyTheft(jsonObject);
                break;
        }
        this.handler.obtainMessage(messageType,object).sendToTarget();
        return object;
    }

    /* Call this from the main activity to send data to the remote device */
    public void sendResponseToSmartphone(String response) throws JSONException, IOException {
        JSONObject jsonObject = ParserUtils.getResponseJSONObject(response);
        mmOutStream.write(jsonObject.toString().getBytes());
    }

    public void sendAlertToSmartphone(String msg) throws IOException, JSONException {
        JSONObject jsonObject = ParserUtils.getAlertJSONObject(msg);
        mmOutStream.write(jsonObject.toString().getBytes());
    }

    /* Call this from the main activity to shutdown the connection */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) { }
    }
}
