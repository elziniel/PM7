package com.example.elziniel.projet;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by Elziniel on 01/01/2016.
 */
public class MyContentProvider extends ContentProvider {
    private MyDatabaseHelper dbHelper;

    private static final int RESTAURANTS = 1;
    private static final int RESTAURANT = 2;

    private static final String AUTHORITY = "com.example.elziniel.projet.contentprovider";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/restaurants");

    private static final UriMatcher matcher;
    static {
        matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(AUTHORITY, "restaurants", RESTAURANTS);
        matcher.addURI(AUTHORITY, "restaurants/#", RESTAURANT);
    }

    private static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.example.elziniel.projet.contentprovider.restaurants";
    private static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.example.elziniel.projet.contentprovider.restaurants";

    @Override
    public boolean onCreate() {
        dbHelper = new MyDatabaseHelper(getContext());
        return false;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (matcher.match(uri)) {
            case RESTAURANTS:
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        long id = db.insert(RestaurantsDB.TABLE_NAME, null, values);
        getContext().getContentResolver().notifyChange(uri, null);
        return Uri.parse(CONTENT_URI + "/" + id);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setTables(RestaurantsDB.TABLE_NAME);
        switch (matcher.match(uri)) {
            case RESTAURANTS:
                break;
            case RESTAURANT:
                String id = uri.getPathSegments().get(1);
                queryBuilder.appendWhere(RestaurantsDB.ID + " = " + id);
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        return queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (matcher.match(uri)) {
            case RESTAURANTS:
                break;
            case RESTAURANT:
                String id = uri.getPathSegments().get(1);
                selection = RestaurantsDB.ID + " = " + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int deleteCount = db.delete(RestaurantsDB.TABLE_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return deleteCount;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        switch (matcher.match(uri)) {
            case RESTAURANTS:
                break;
            case RESTAURANT:
                String id = uri.getPathSegments().get(1);
                selection = RestaurantsDB.ID + " = " + id
                        + (!TextUtils.isEmpty(selection) ?
                        " AND (" + selection + ')' : "");
                break;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
        int updateCount = db.update(RestaurantsDB.TABLE_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return updateCount;
    }

    @Override
    public String getType(Uri uri) {
        switch (matcher.match(uri)) {
            case RESTAURANTS:
                return CONTENT_TYPE;
            case RESTAURANT:
                return CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}
