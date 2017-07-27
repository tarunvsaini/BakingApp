package com.tarun.saini.baking_app.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by Tarun on 25-07-2017.
 */

public class WidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this.getApplicationContext(),intent);
    }
}
