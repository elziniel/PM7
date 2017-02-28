package com.example.elziniel.projet;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Fiche extends AppCompatActivity {
    private String id, newNom, newAdresse, newTelephone, newSite, newPrix, newCuisine, newNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche);
        Bundle bundle = this.getIntent().getExtras();
        id = bundle.getString("rId");
        TextView nom = (TextView) findViewById(R.id.nom);
        TextView adresse = (TextView) findViewById(R.id.adresse);
        TextView telephone = (TextView) findViewById(R.id.telephone);
        TextView site = (TextView) findViewById(R.id.site);
        RatingBar note = (RatingBar) findViewById(R.id.note);
        TextView prix = (TextView) findViewById(R.id.prix);
        TextView cuisine = (TextView) findViewById(R.id.cuisine);
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
        prix.setText(newPrix);
        if (newCuisine.equals("")) {
            cuisine.setText("Non défini");
        }
        else {
            cuisine.setText(newCuisine);
        }
        if (newNote == null) {
            note.setRating(Float.parseFloat("0"));
        }
        else {
            note.setRating(Float.parseFloat(String.valueOf(newNote)));
        }
    }

    @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            recreate();
        }
    }

    public void modifier(View view) {
        Intent intent = new Intent(getBaseContext(), Modifier.class);
        intent.putExtra("id", id);
        startActivityForResult(intent, 1);
    }

    public void partager(View view) {
        String message1 = "Je t'invite au restaurant " + newNom + " au " + newAdresse + ".";
        String message2 = "";
        if (!newPrix.equals("Non défini")) {
            if (!newCuisine.equals("")) {
                message2 = "C'est un restaurant " + newCuisine + " ou le prix varie " + newPrix + ".";
            }
            else {
                message2 = "C'est un restaurant ou le prix varie " + newPrix + ".";
            }
        }
        else {
            if (!newCuisine.equals("")) {
                message2 = "C'est un restaurant " + newCuisine + ".";
            }
            else {
                message2 = "C'est un bon restaurant.";
            }
        }
        String message = message1 + " " + message2;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("sms:"));
        intent.putExtra("sms_body",message);
        startActivity(intent);
    }

    public void supprimer(View view) {
        new AlertDialog.Builder(this)
            .setTitle("Suppression")
            .setMessage("Voulez-vous vraiment supprimer ce restaurant ?")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setPositiveButton(R.string.oui, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                    Uri uri = Uri.parse(MyContentProvider.CONTENT_URI + "/" + id);
                    getContentResolver().delete(uri, null, null);
                    Toast.makeText(getBaseContext(), "Restaurant " + newNom + " supprimé", Toast.LENGTH_LONG).show();
                    finish();
                }})
            .setNegativeButton(R.string.non, null).show();
    }
}
