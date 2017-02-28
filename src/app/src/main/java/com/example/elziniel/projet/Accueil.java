package com.example.elziniel.projet;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class Accueil extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accueil);
    }

    public void parcourir(View view) {
        Intent intent = new Intent(this, Parcourir.class);
        startActivity(intent);
    }

    public void rechercher(View view) {
        Intent intent = new Intent(this, Rechercher.class);
        startActivity(intent);
    }

    public void ajouter(View view) {
        Intent intent = new Intent(this, Ajouter.class);
        startActivity(intent);
    }
}
