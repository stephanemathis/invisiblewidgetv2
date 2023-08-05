package fr.mathis.invisiblewidget;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class SmallWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.invisible_widget);

        views.setInt(R.id.iv_widget_background, "setBackgroundColor", getColor(context));

        if (DataManager.GetMemorizedValue("alpha", context) != 0) {
            Intent iSetting = new Intent(context, MainActivity.class);
            PendingIntent piSetting = PendingIntent.getActivity(context, 0, iSetting, PendingIntent.FLAG_IMMUTABLE);
            views.setOnClickPendingIntent(R.id.iv_widget_background, piSetting);
        }
        else {
            views.setOnClickPendingIntent(R.id.iv_widget_background, null);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
        AppWidgetManager mgr = AppWidgetManager.getInstance(context);
        ComponentName me = new ComponentName(context, SmallWidget.class);
        mgr.updateAppWidget(me, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static int getColor(Context c) {
        @SuppressLint("ResourceType") int blueColor = Color.parseColor(c.getString(R.color.appPrimaryVibrant));
        return Color.argb(DataManager.GetMemorizedValue("alpha", c), Color.red(blueColor), Color.green(blueColor), Color.blue(blueColor));
    }
}