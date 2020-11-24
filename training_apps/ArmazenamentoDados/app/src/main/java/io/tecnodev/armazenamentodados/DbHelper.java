package io.tecnodev.armazenamentodados;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

class DbHelper extends SQLiteOpenHelper {

    static final String DB_NAME = "treinaweb.db";
    static final int DB_VERSION = 3;

    static final String TABLE = "cadastro";

    static final String C_ID = "_id";
    static final String C_NOME = "nome";
    static final String C_SEXO = "sexo";
    static final String C_IDADE = "idade";
    static final String C_TELEFONE = "telefone";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    private List<ContentValues> dados;

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            String sql = "create table " + TABLE + " (" + C_ID + " integer primary key autoincrement, "
                    + C_NOME + " text, " + C_SEXO + " text, " + C_IDADE + " text, " + C_TELEFONE + " text)";

            db.execSQL(sql);
        } catch (Exception e) {
            Log.e("Error DbHelper: ", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            backup(db);

            String sql = "drop table if exists " + TABLE;
            db.execSQL(sql);

            onCreate(db);

            restore(db);
        } catch (Exception e) {
            Log.e("Error DbHelper: ", e.getMessage());
        }
    }

    private void backup(SQLiteDatabase db) {
        dados = new ArrayList<>();

        try {
            Cursor cursor = db.query(TABLE, null, null, null, null, null, null);

            try {
                while (cursor.moveToNext()) {
                    String nome = cursor.getString(1);
                    String sexo = cursor.getString(2);
                    String idade = cursor.getString(3);

                    ContentValues values = new ContentValues();

                    values.put(C_NOME, nome);
                    values.put(C_SEXO, sexo);
                    values.put(C_IDADE, idade);

                    dados.add(values);
                }
            } finally {
              cursor.close();
            }
        } catch (Exception e) {
            Log.e("Error DbHelper: ", e.getMessage());
        }
    }

    private void restore(SQLiteDatabase db) {
        try {
            for (ContentValues values : dados) {
                db.insertOrThrow(TABLE, null, values);
            }
        } catch (Exception e) {
            Log.e("Error DbHelper: ", e.getMessage());
        }
    }
}
