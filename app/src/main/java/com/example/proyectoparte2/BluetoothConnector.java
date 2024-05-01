package com.example.proyectoparte2;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothConnector {
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mDevice;
    private BluetoothSocket mSocket;
    private OutputStream mOutputStream;
    private static final String DEVICE_ADDRESS = "00:21:13:00:7A:AC";
    private static BluetoothConnector instance = null;

    public BluetoothConnector() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mDevice = mBluetoothAdapter.getRemoteDevice(DEVICE_ADDRESS);
    }

    public static BluetoothConnector getInstance() {
        if (instance == null) {
            instance = new BluetoothConnector();
        }
        return instance;
    }

    public void connect() {
        try {
            mSocket = mDevice.createRfcommSocketToServiceRecord(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"));
            mSocket.connect();
            mOutputStream = mSocket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendData(String data) {
        if (mOutputStream != null) {
            try {
                mOutputStream.write(data.getBytes());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}