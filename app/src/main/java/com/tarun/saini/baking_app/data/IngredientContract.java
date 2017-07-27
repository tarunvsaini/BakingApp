package com.tarun.saini.baking_app.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Tarun on 25-07-2017.
 */

public class IngredientContract {


    private IngredientContract()
    {

    }

    public static final String CONTENT_AUTHORITY = "com.tarun.saini.baking_app";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_INGREDIENTS = "IngredientTable";
    public static final Uri INGREDIENT_CONTENT_URI = BASE_CONTENT_URI.buildUpon()
            .appendPath(PATH_INGREDIENTS).build();



    public static final class IngredientEntry implements BaseColumns {

        public static final String TABLE_NAME = "IngredientTable";
        public static final String KEY_ID = "id";
        public static final String KEY_NAME = "name";
        public static final String KEY_QUANTITY = "quantity";
        public static final String KEY_MEASURE = "measure";
        public static final String KEY_INGREDIENT = "ingredient";

    }
}
