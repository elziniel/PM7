package com.example.elziniel.projet;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Resultat extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    private String selection;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parcourir);
        Bundle bundle = this.getIntent().getExtras();
        selection = bundle.getString("selection");
        String[] from = new String[]{
                RestaurantsDB.NOM,
                RestaurantsDB.ADRESSE,
                RestaurantsDB.TELEPHONE,
                RestaurantsDB.SITE};
        int[] to = new int[] {
                R.id.rNom,
                R.id.rAdresse,
                R.id.rTelephone,
                R.id.rSite,
        };
        adapter = new SimpleCursorAdapter(this, R.layout.restaurant_info, null, from, to, 0);
        ListView list = (ListView) findViewById(R.id.restaurants);
        list.setAdapter(adapter);
        getLoaderManager().initLoader(0, null, this);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);
                String rId = cursor.getString(cursor.getColumnIndexOrThrow(RestaurantsDB.ID));
                Intent intent = new Intent(getBaseContext(), Fiche.class);
                intent.putExtra("rId", rId);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {
                "rowid _id",
                RestaurantsDB.NOM,
                RestaurantsDB.ADRESSE,
                RestaurantsDB.TELEPHONE,
                RestaurantsDB.SITE};
        return new CursorLoader(this, MyContentProvider.CONTENT_URI, projection, selection, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}
