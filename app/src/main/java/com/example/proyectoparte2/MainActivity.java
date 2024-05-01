package com.example.proyectoparte2;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText usernameEditText;
    EditText passwordEditText;
    Button loginButton;
    baseDatos dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        usernameEditText = findViewById(R.id.username);
        passwordEditText = findViewById(R.id.password);
        loginButton = findViewById(R.id.login);
        // Inicializar DatabaseHelper
        dbHelper = new baseDatos(this);

        loginButton.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (dbHelper.userExists(username, password)) {
            Intent intent = new Intent(MainActivity.this, pantallaProgramas.class);
            Toast.makeText(MainActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
            startActivity(intent);

            // Conectar al dispositivo Bluetooth después de iniciar sesión
            BluetoothConnector bluetoothConnector = BluetoothConnector.getInstance();
            bluetoothConnector.connect();
        } else {
            Toast.makeText(MainActivity.this, "Datos Incorrectos", Toast.LENGTH_SHORT).show();
        }
    }
});
    }
}