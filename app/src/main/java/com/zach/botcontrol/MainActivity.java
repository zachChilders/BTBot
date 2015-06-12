package com.zach.botcontrol;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends Activity {

    Button buttonUp, buttonDown, buttonLeft, buttonRight;

    private static final String TAG = "Main";
    private Bluetooth bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonUp = (Button) findViewById(R.id.button_up);
        buttonUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.sendMessage("w");
            }
        });

        buttonDown = (Button) findViewById(R.id.button_down);
        buttonDown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.sendMessage("s");
            }
        });

        buttonRight = (Button) findViewById(R.id.button_right);
        buttonRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                bt.sendMessage("d");
            }
        });

        buttonLeft = (Button) findViewById(R.id.button_left);
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               bt.sendMessage("a");
            }
        });


        //Turn on BT
        bt = new Bluetooth(this, mHandler);
        connectService();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void connectService() {
        try {
            popToast("Connecting...");
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            if (bluetoothAdapter.isEnabled()) {
                bt.start();
                bt.connectDevice("HC-06");
                Log.d(TAG, "Btservice started - listening");
                popToast("Connected");
            } else {
                Log.w(TAG, "Btservice started - bluetooth is not enabled");
                popToast("Bluetooth Not enabled");
            }
        } catch (Exception e) {
            Log.e(TAG, "Unable to start bt ", e);
            popToast("Unable to connect " + e);
        }
    }


    public void popToast(String msg) {
        Context context = getApplicationContext();
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    private static final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Bluetooth.MESSAGE_STATE_CHANGE:
                    Log.d(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                    break;
                case Bluetooth.MESSAGE_WRITE:
                    Log.d(TAG, "MESSAGE_WRITE ");
                    break;
                case Bluetooth.MESSAGE_READ:
                    Log.d(TAG, "MESSAGE_READ ");
                    break;
                case Bluetooth.MESSAGE_DEVICE_NAME:
                    Log.d(TAG, "MESSAGE_DEVICE_NAME " + msg);
                    break;
                case Bluetooth.MESSAGE_TOAST:
                    Log.d(TAG, "MESSAGE_TOAST " + msg);
                    break;
            }
        }
    };

}

