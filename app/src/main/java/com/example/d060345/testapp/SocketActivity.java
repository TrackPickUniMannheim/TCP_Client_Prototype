package com.example.d060345.testapp;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;

public class SocketActivity extends Activity {
    ConnectionService mService;
    boolean mBound = false;
    private Intent i;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        // connect to the server
        i = new Intent(this, ConnectionService.class);
        bindService(i, mConnection, Context.BIND_AUTO_CREATE);
    }

    public void onButtonPress(View view){
        if (mBound){
            System.out.println("button pressed");
            mService.sendMessage("test");
        }

        /* try {

            JSONObject obj = new JSONObject();
            Long time = System.currentTimeMillis();
            obj.put("client", Constants.LOGIN_NAME);
            obj.put("time", time);

            String message = obj.toString();

            //sends the message to the server
            if (mTcpClient != null) {
                mTcpClient.sendMessage(message);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }

    public void onStop(){
        super.onStop();
        this.stopService(i);
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            ConnectionService.LocalBinder binder = (ConnectionService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            mService.startConnection();
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };
}
