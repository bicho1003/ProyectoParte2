package com.example.proyectoparte2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
public class baseDatos extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "userstore.db"; // Nombre de la base de datos
    private static final int DATABASE_VERSION = 1; // Versión de la base de datos
    public baseDatos(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Crear la tabla "users" en la base de datos
        db.execSQL("CREATE TABLE users (username TEXT, password TEXT)");
        db.execSQL("INSERT INTO users (username, password) VALUES ('admin', '1234')");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Actualizar la base de datos si la versión cambia
        db.execSQL("DROP TABLE IF EXISTS users");
        onCreate(db);
    }
    public boolean userExists(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[] {username, password});
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }
}