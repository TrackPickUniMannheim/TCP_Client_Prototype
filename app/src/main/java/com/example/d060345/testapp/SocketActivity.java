package com.example.d060345.testapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.StringWriter;
import java.security.Timestamp;

public class SocketActivity extends Activity {

    private TcpClient mTcpClient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);

        Button send = (Button)findViewById(R.id.send_button);

        // connect to the server
        new connectTask().execute("");

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {

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
                }

            }
        });

    }
    public void onStop(){
        super.onStop();

        mTcpClient.stopClient();
    }


    public class connectTask extends AsyncTask<String,String,TcpClient> {

        @Override
        protected TcpClient doInBackground(String... message) {

            //we create a TCPClient object and
            mTcpClient = new TcpClient();
            mTcpClient.run();

            return null;
        }

    }
}
