package example.android.com.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;

import example.android.com.bakingapp.model.Recipe;


public class BakingWidgetService extends IntentService
{
  public static final String ACTION_RECIPE_UPDATE =
    "com.example.android.bakingapp.widget.action.recipe_update";
  private static final String BUNDLE_RECIPE_WIDGET_DATA =
    "com.example.android.bakingapp.widget.widget_data";

  public BakingWidgetService()
  {
    super("BakingWidgetService");
  }

  public static void startActionUpdateRecipe(Context context, Recipe recipe)
  {
    Intent intent = new Intent(context, BakingWidgetService.class);
    intent.setAction(ACTION_RECIPE_UPDATE);
    intent.putExtra(BUNDLE_RECIPE_WIDGET_DATA, recipe);
    context.startService(intent);
  }

  @Override
  protected void onHandleIntent(Intent intent)
  {
    if(intent != null)
    {
      final String action = intent.getAction();
      if(ACTION_RECIPE_UPDATE.equals(action) &&
        intent.getParcelableExtra(BUNDLE_RECIPE_WIDGET_DATA) != null)
      {
        handleActionUpdateWidgets((Recipe) intent.getParcelableExtra(BUNDLE_RECIPE_WIDGET_DATA));
      }
    }
  }

  private void handleActionUpdateWidgets(Recipe recipe)
  {
    AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
    int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));
    BakingWidgetProvider.updateBakingWidgets(this, appWidgetManager, appWidgetIds, recipe);
  }
}