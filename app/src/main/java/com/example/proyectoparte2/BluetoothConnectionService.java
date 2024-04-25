package com.example.proyectoparte2;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import java.io.IOException;
import java.util.UUID;

public class BluetoothConnectionService {
    public static final UUID MY_UUID_INSECURE =
            UUID.fromString("8ce255c0-200a-11e0-ac64-0800200c9a66");

    private final BluetoothAdapter bluetoothAdapter;
    private BluetoothDevice mmDevice;
    private BluetoothSocket mmSocket;

    public BluetoothConnectionService(Context context) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        start();
    }

    // Iniciar servicio
    public void start() {
        // Más código aquí
    }

    // Iniciar conexión al dispositivo Bluetooth
    public void startClient(BluetoothDevice device, UUID uuid) {
        mmDevice = device;
        BluetoothSocket tmp = null;

        try {
            tmp = mmDevice.createRfcommSocketToServiceRecord(uuid);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mmSocket = tmp;
        bluetoothAdapter.cancelDiscovery();

        try {
            mmSocket.connect();
        } catch (IOException e) {
            try {
                mmSocket.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        // Método para manejar la conexión exitosa
    }

    // Cerrar el socket
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
