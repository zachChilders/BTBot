package com.zach.botcontrol;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
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

    private BluetoothAdapter bt;
    private Set<BluetoothDevice> pairedDevices;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonUp = (Button) findViewById(R.id.button_up);
        buttonUp.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popToast("UP");
            }
        });

        buttonDown = (Button) findViewById(R.id.button_down);
        buttonDown.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popToast("DOWN");
            }
        });

        buttonRight = (Button) findViewById(R.id.button_right);
        buttonRight.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popToast("RIGHT");
            }
        });

        buttonLeft = (Button) findViewById(R.id.button_left);
        buttonLeft.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                popToast("LEFT");
            }
        });

        bt = BluetoothAdapter.getDefaultAdapter();
        lv = (ListView) findViewById(R.id.listView);

        //Turn on BT
        bluetoothOn();
        list();
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


    public void list()
    {
        pairedDevices = bt.getBondedDevices();
        ArrayList list = new ArrayList();

        for (BluetoothDevice bt : pairedDevices)
        {
            list.add(bt.getName());
        }
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, list);
        lv.setAdapter(adapter);
    }


    public void bluetoothOn() {
        if (!bt.isEnabled()) {
            Intent turnOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnOn, 1);
        }
    }

    public void bluetoothOff()
    {
        bt.disable();
    }



    public void popToast(String msg) {
        Context context = getApplicationContext();
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

}
