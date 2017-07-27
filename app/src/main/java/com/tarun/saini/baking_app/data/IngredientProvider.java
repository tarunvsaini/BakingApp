package com.tarun.saini.baking_app.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.tarun.saini.baking_app.data.IngredientContract.IngredientEntry;

import static com.tarun.saini.baking_app.data.IngredientContract.CONTENT_AUTHORITY;
import static com.tarun.saini.baking_app.data.IngredientContract.PATH_INGREDIENTS;

/**
 * Created by Tarun on 25-07-2017.
 */

public class IngredientProvider extends ContentProvider {

    private static final int INGREDIENT=100;
    private static final int ID=101;
    public static final String LOG_TAG = IngredientProvider.class.getSimpleName();

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static
    {
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_INGREDIENTS,INGREDIENT);
        sUriMatcher.addURI(CONTENT_AUTHORITY, PATH_INGREDIENTS + "/#",ID);
    }



    private IngredientDbHelper ingredientDbHelper;


    @Override
    public boolean onCreate() {
        ingredientDbHelper=new IngredientDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        SQLiteDatabase database=ingredientDbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);
        switch (match) {
            case INGREDIENT:

                cursor = database.query(IngredientEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            case ID:

                selection = IngredientEntry.KEY_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };

                cursor = database.query(IngredientEntry.TABLE_NAME, projection, selection, selectionArgs,
                        null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }


        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case INGREDIENT:
                return insertIngredient(uri, values);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertIngredient(Uri uri, ContentValues values)
    {
        SQLiteDatabase database = ingredientDbHelper.getWritableDatabase();
        long id = database.insert(IngredientEntry.TABLE_NAME, null, values);

        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase database = ingredientDbHelper.getWritableDatabase();
        int rowsDeleted;
        if (null == selection) selection = "1";

        final int match = sUriMatcher.match(uri);
        switch (match) {
            case INGREDIENT:
                rowsDeleted=database.delete(IngredientEntry.TABLE_NAME, null, null);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
