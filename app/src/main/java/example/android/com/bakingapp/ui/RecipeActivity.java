package example.android.com.bakingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import example.android.com.bakingapp.R;

/**
 * Created by Deniz Kalem on 05.10.17.
 */

public class RecipeActivity extends AppCompatActivity
{
  public static String ALL_RECIPES = "All_Recipes";

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe);
  }
}
