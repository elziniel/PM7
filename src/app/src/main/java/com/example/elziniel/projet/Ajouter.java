package com.example.elziniel.projet;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

public class Ajouter extends AppCompatActivity {
    private EditText nom, adresse, telephone, site, cuisine;
    private RatingBar note;
    private Spinner prix;
    private String newNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter);
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
        cuisine = (EditText) findViewById(R.id.cuisine);
        prix = (Spinner) findViewById(R.id.categorie);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categorie, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prix.setAdapter(adapter);
    }

    public void ajouter(View view) {
        String newNom = nom.getText().toString();
        String newAdresse = adresse.getText().toString();
        String newTelephone = telephone.getText().toString();
        String newSite = site.getText().toString();
        String newPrix = prix.getSelectedItem().toString();
        String newCuisine = cuisine.getText().toString();
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
        getContentResolver().insert(MyContentProvider.CONTENT_URI, values);
        Toast.makeText(getBaseContext(), "Restaurant " + newNom + " ajouté", Toast.LENGTH_LONG).show();
        finish();
    }
}
