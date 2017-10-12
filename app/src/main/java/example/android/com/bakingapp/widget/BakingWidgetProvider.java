package example.android.com.bakingapp.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import example.android.com.bakingapp.R;
import example.android.com.bakingapp.model.Ingredient;
import example.android.com.bakingapp.model.Recipe;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider
{
  static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                              int appWidgetId, Recipe recipe)
  {

    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
    views.removeAllViews(R.id.ll_recipe_widget_ingredient_list);
    views.setTextViewText(R.id.recipe_widget_title, recipe.getName());

    for(Ingredient ingredient : recipe.getIngredients())
    {
      RemoteViews rvIngredient = new RemoteViews(context.getPackageName(),
        R.layout.baking_widget_list_item);
      rvIngredient.setTextViewText(R.id.tv_recipe_widget_ingredient_item,
        String.valueOf(ingredient.getQuantity()) +
          String.valueOf(ingredient.getMeasure()) + " " + ingredient.getIngredient());
      views.addView(R.id.ll_recipe_widget_ingredient_list, rvIngredient);
    }

    appWidgetManager.updateAppWidget(appWidgetId, views);
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
  {
  }

  @Override
  public void onEnabled(Context context)
  {
    // Enter relevant functionality for when the first widget is created
  }

  @Override
  public void onDisabled(Context context)
  {
    // Enter relevant functionality for when the last widget is disabled
  }

  public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager,
                                         int[] appWidgetIds, Recipe recipe)
  {
    for(int appWidgetId : appWidgetIds)
    {
      updateAppWidget(context, appWidgetManager, appWidgetId, recipe);
    }
  }
}

