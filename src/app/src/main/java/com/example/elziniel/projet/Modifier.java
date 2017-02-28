package com.example.elziniel.projet;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Modifier extends AppCompatActivity {
    private EditText nom, adresse, telephone, site, cuisine;
    private RatingBar note;
    private Spinner prix;
    private String id, newNom, newAdresse, newTelephone, newSite, newPrix, newCuisine, newNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifier);
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getString("id");
        nom = (EditText) findViewById(R.id.nom);
        adresse = (EditText) findViewById(R.id.adresse);
        telephone = (EditText) findViewById(R.id.telephone);
        site = (EditText) findViewById(R.id.site);
        note = (RatingBar) findViewById(R.id.appreciation);
        note.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                newNote = String.valueOf(rating);
            }
        });
        prix = (Spinner) findViewById(R.id.categorie);
        cuisine = (EditText) findViewById(R.id.cuisine);
        String[] projection = {
                "rowid as _id",
                RestaurantsDB.NOM,
                RestaurantsDB.ADRESSE,
                RestaurantsDB.TELEPHONE,
                RestaurantsDB.SITE,
                RestaurantsDB.NOTE,
                RestaurantsDB.PRIX,
                RestaurantsDB.CUISINE,};
        Uri uri = Uri.parse(MyContentProvider.CONTENT_URI + "/" + id);
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            id = cursor.getString(cursor.getColumnIndexOrThrow(RestaurantsDB.ID));
            newNom = cursor.getString(cursor.getColumnIndexOrThrow(RestaurantsDB.NOM));
            newAdresse = cursor.getString(cursor.getColumnIndexOrThrow(RestaurantsDB.ADRESSE));
            newTelephone = cursor.getString(cursor.getColumnIndexOrThrow(RestaurantsDB.TELEPHONE));
            newSite = cursor.getString(cursor.getColumnIndexOrThrow(RestaurantsDB.SITE));
            newNote = cursor.getString(cursor.getColumnIndexOrThrow(RestaurantsDB.NOTE));
            newPrix = cursor.getString(cursor.getColumnIndexOrThrow(RestaurantsDB.PRIX));
            newCuisine = cursor.getString(cursor.getColumnIndexOrThrow(RestaurantsDB.CUISINE));
        }
        nom.setText(newNom);
        adresse.setText(newAdresse);
        telephone.setText(newTelephone);
        site.setText(newSite);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categorie, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prix.setAdapter(adapter);
        prix.setSelection(adapter.getPosition(newPrix));
        cuisine.setText(newCuisine);
        if (newNote != null) {
            note.setRating(Float.parseFloat(String.valueOf(newNote)));
        }
        else {
            note.setRating(Float.parseFloat("0"));
        }
    }

    public void modifier(View view) {
        newNom = nom.getText().toString();
        newAdresse = adresse.getText().toString();
        newTelephone = telephone.getText().toString();
        newSite = site.getText().toString();
        newPrix = prix.getSelectedItem().toString();
        newCuisine = cuisine.getText().toString();
        if(newNom.trim().equalsIgnoreCase("")){
            Toast.makeText(getBaseContext(), "Veuillez entrer un nom", Toast.LENGTH_LONG).show();
            return;
        }
        if(newAdresse.trim().equalsIgnoreCase("")){
            Toast.makeText(getBaseContext(), "Veuillez entrer une adresse", Toast.LENGTH_LONG).show();
            return;
        }
        if(newTelephone.trim().equalsIgnoreCase("")){
            Toast.makeText(getBaseContext(), "Veuillez entrer un numéro de téléphone", Toast.LENGTH_LONG).show();
            return;
        }
        if(newSite.trim().equalsIgnoreCase("")){
            Toast.makeText(getBaseContext(), "Veuillez entrer un site internet", Toast.LENGTH_LONG).show();
            return;
        }
        ContentValues values = new ContentValues();
        values.put(RestaurantsDB.NOM, newNom);
        values.put(RestaurantsDB.ADRESSE, newAdresse);
        values.put(RestaurantsDB.TELEPHONE, newTelephone);
        values.put(RestaurantsDB.SITE, newSite);
        values.put(RestaurantsDB.NOTE, newNote);
        values.put(RestaurantsDB.PRIX, newPrix);
        values.put(RestaurantsDB.CUISINE, newCuisine);
        Uri uri = Uri.parse(MyContentProvider.CONTENT_URI + "/" + id);
        getContentResolver().update(uri, values, null, null);
        Toast.makeText(getBaseContext(), "Restaurant " + newNom + " modifié", Toast.LENGTH_LONG).show();
        Intent intent = new Intent();
        intent.putExtra("result", 1);
        setResult(1, intent);
        finish();
    }
}
