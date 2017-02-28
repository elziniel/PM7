package com.example.elziniel.projet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;

public class Rechercher extends AppCompatActivity {
    private String newNote;
    private EditText nom, adresse, telephone, site, cuisine;
    private Spinner prix;
    private RatingBar note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rechercher);
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
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.categorie, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prix.setAdapter(adapter);
        cuisine = (EditText) findViewById(R.id.cuisine);
    }

    public void rechercher(View view) {
        String newNom = nom.getText().toString();
        String newAdresse = adresse.getText().toString();
        String newTelephone = telephone.getText().toString();
        String newSite = site.getText().toString();
        String newPrix = prix.getSelectedItem().toString();
        String newCuisine = cuisine.getText().toString();
        String selection = (newNom.equals("") ? "" : RestaurantsDB.NOM + " LIKE \"%" + newNom + "%\" AND ") +
                        (newAdresse.equals("") ? "" : RestaurantsDB.ADRESSE + " LIKE \"%" + newAdresse + "%\" AND ") +
                        (newTelephone.equals("") ? "" : RestaurantsDB.TELEPHONE + " LIKE \"%" + newTelephone + "%\" AND ") +
                        (newSite.equals("") ? "" : RestaurantsDB.SITE + " LIKE \"%" + newSite + "%\" AND ") +
                        (newPrix.equals("Non défini") ? "" : RestaurantsDB.PRIX + " = \"" + newPrix + "\" AND ") +
                (newCuisine.equals("") ? "" : RestaurantsDB.CUISINE + " LIKE \"%" + newCuisine + "%\" AND ") +
                (newNote == null ? RestaurantsDB.NOTE + " >= 0" : RestaurantsDB.NOTE + " >= " + newNote);
        Intent intent = new Intent(getBaseContext(), Resultat.class);
        intent.putExtra("selection", selection);
        startActivity(intent);
    }
}
