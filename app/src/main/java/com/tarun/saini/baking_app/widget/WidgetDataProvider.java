package com.tarun.saini.baking_app.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.tarun.saini.baking_app.R;
import com.tarun.saini.baking_app.data.IngredientContract;

import java.util.ArrayList;
import java.util.List;

import static com.tarun.saini.baking_app.data.IngredientContract.BASE_CONTENT_URI;
import static com.tarun.saini.baking_app.data.IngredientContract.IngredientEntry.KEY_ID;
import static com.tarun.saini.baking_app.data.IngredientContract.IngredientEntry.KEY_INGREDIENT;
import static com.tarun.saini.baking_app.data.IngredientContract.IngredientEntry.KEY_MEASURE;
import static com.tarun.saini.baking_app.data.IngredientContract.IngredientEntry.KEY_QUANTITY;
import static com.tarun.saini.baking_app.data.IngredientContract.PATH_INGREDIENTS;

/**
 * Created by Tarun on 25-07-2017.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {

    List<String> collection=new ArrayList<>();
    Context context;
    Intent intent;
    Cursor cursor;
    private static final String[] INGREDIENT_COLUMNS = {
            KEY_ID,
            IngredientContract.IngredientEntry.KEY_NAME,
            KEY_INGREDIENT,
            IngredientContract.IngredientEntry.KEY_QUANTITY,
            IngredientContract.IngredientEntry.KEY_MEASURE
    };



    public WidgetDataProvider(Context context, Intent intent) {
        this.context = context;
        this.intent = intent;
    }

    @Override
    public void onCreate() {



    }

    @Override
    public void onDataSetChanged() {


        Uri INGREDIENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_INGREDIENTS);

        if (cursor!=null)
        {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            cursor = context.getContentResolver().query(INGREDIENT_URI,
                    INGREDIENT_COLUMNS,
                    null,
                    null,
                    null,
                    null);
            Binder.restoreCallingIdentity(identityToken);
        }

    }



    @Override
    public void onDestroy()
    {
        if (cursor !=null)
        {
            cursor.close();
        }

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {

        if (cursor == null || !cursor.moveToPosition(position)||cursor.getCount()==0) {
            return null;
        }

       RemoteViews remoteViews=new RemoteViews(context.getPackageName(),
              R.layout.step_layout);
        String ingredient=cursor.getString(cursor.getColumnIndex(KEY_INGREDIENT)).toLowerCase();
        String measure=cursor.getString(cursor.getColumnIndex(KEY_MEASURE)).toLowerCase();
        String quantity=cursor.getString(cursor.getColumnIndex(KEY_QUANTITY));
        remoteViews.setTextViewText(R.id.step_description,quantity+" "+measure+" "+ingredient);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return new RemoteViews(context.getPackageName(),
                R.layout.step_layout);
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return cursor.moveToPosition(position) ? cursor.getLong(0) : position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
