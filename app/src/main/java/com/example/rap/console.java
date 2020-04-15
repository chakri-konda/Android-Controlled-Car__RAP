package com.example.rap;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class console extends AppCompatActivity {

    private TextView RDBuffer;
    private ImageButton forwardBtn, rightBtn, backBtn, leftBtn;
    private Button brakeBtn, disconnectBtn;

    private String address;
    private ProgressDialog progress;
    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private boolean isBTConnected = false;
    //Default Universally Unique Identifier, HC-05's default
    static final UUID mUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private boolean on = false;
    private Toast previousToast;

    public console() throws IOException {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_console);
        Intent receivedIntent = getIntent();
        address = receivedIntent.getStringExtra(MainActivity.EXTRA_ADDRESS);

        forwardBtn = findViewById(R.id.forwardBtn);
        backBtn = findViewById(R.id.backBtn);
        leftBtn = findViewById(R.id.leftBtn);
        rightBtn = findViewById(R.id.rightBtn);
        brakeBtn = findViewById(R.id.brakeBtn);
        disconnectBtn = findViewById(R.id.disconnectBtn);
        RDBuffer = findViewById(R.id.readBuffer);

        new ConnectBluetooth().execute();

        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the bluetooth socket is busy
                if (bluetoothSocket != null) {
                    try {
                        //To prevent functioning of bulb on next start of app
                        bluetoothSocket.getOutputStream().write("f".getBytes());
                        RDBuffer.setText("f");
                    } catch (IOException e) {
                        showToast("Error!!!");
                    }
                }
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the bluetooth socket is busy
                if (bluetoothSocket != null) {
                    try {
                        //To prevent functioning of bulb on next start of app
                        bluetoothSocket.getOutputStream().write("b".getBytes());
                        RDBuffer.setText("b");
                    } catch (IOException e) {
                        showToast("Error!!!");
                    }
                }
            }
        });

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the bluetooth socket is busy
                if (bluetoothSocket != null) {
                    try {
                        //To prevent functioning of bulb on next start of app
                        bluetoothSocket.getOutputStream().write("l".getBytes());
                        RDBuffer.setText("l");
                    } catch (IOException e) {
                        showToast("Error!!!");
                    }
                }
            }
        });

        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the bluetooth socket is busy
                if (bluetoothSocket != null) {
                    try {
                        //To prevent functioning of bulb on next start of app
                        bluetoothSocket.getOutputStream().write("r".getBytes());
                        RDBuffer.setText("r");
                    } catch (IOException e) {
                        showToast("Error!!!");
                    }
                }
            }
        });

        brakeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the bluetooth socket is busy
                if (bluetoothSocket != null) {
                    try {
                        //To prevent functioning of bulb on next start of app
                        bluetoothSocket.getOutputStream().write("s".getBytes());
                        RDBuffer.setText("s");
                    } catch (IOException e) {
                        showToast("Error!!!");
                    }
                }
            }
        });

        disconnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //If the bluetooth socket is busy
                if (bluetoothSocket != null) {
                    try {
                        //To prevent functioning of bulb on next start of app
                        bluetoothSocket.getOutputStream().write("0".getBytes());
                        showToast("Disconnected");
                        bluetoothSocket.close(); //Close connection
                    } catch (IOException e) {
                        showToast("Error!!!");
                    }
                }
                finish();   //Return to the previous activity
            }
        });
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
        //Hide the previous toast
        if (previousToast != null)
            previousToast.cancel();
        previousToast = toast;
    }

    private class ConnectBluetooth extends AsyncTask<Void, Void, Void> {

        private boolean connectSuccess = true;

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(console.this, "Connecting...", "Please wait!!!");
        }

        @Override
        protected Void doInBackground(Void... devices) {
            try {
                if (bluetoothSocket == null || !isBTConnected) {
                    bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
                    bluetoothSocket = device.createInsecureRfcommSocketToServiceRecord(mUUID);
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    bluetoothSocket.connect();
                }
            } catch (IOException e) {
                connectSuccess = false;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (!connectSuccess) {
                showToast("Connection failed");
                finish();
            } else {
                showToast("Fasten your Seat Belts\nABRACADABRA");
                isBTConnected = true;
            }
            progress.dismiss();
        }
    }
}
