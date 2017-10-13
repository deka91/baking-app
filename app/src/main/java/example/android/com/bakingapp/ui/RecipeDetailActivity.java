package example.android.com.bakingapp.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.com.bakingapp.R;
import example.android.com.bakingapp.model.Recipe;

import static example.android.com.bakingapp.util.MyConstants.SELECTED_RECIPE;
import static example.android.com.bakingapp.util.MyConstants.RECIPE_DETAIL;
import static example.android.com.bakingapp.util.MyConstants.RECIPE_STEP;

/**
 * Created by Deniz Kalem on 06.10.17.
 */

public class RecipeDetailActivity extends AppCompatActivity
{
  private Recipe recipe;
  private FragmentManager fragmentManager;
  private RecipeStepFragment recipeStepFragment;

  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_detail);

    fragmentManager = getSupportFragmentManager();

    if(savedInstanceState == null)
    {
      Bundle bundle = getIntent().getExtras();

      recipe = bundle.getParcelable(SELECTED_RECIPE);

      final RecipeDetailFragment fragment = new RecipeDetailFragment();
      fragment.setArguments(bundle);
      fragmentManager.beginTransaction()
        .replace(R.id.fragment_container, fragment).addToBackStack(RECIPE_DETAIL)
        .commit();

      if(RecipeFragment.isTablet && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
      {
        RecipeStepFragment fragment2 = new RecipeStepFragment();
        bundle.putParcelable(SELECTED_RECIPE, recipe);
        fragment2.setArguments(bundle);
        fragmentManager.beginTransaction()
          .replace(R.id.fragment_container2, fragment2).addToBackStack(RECIPE_STEP)
          .commit();
      }
    } else
    {
      recipe = savedInstanceState.getParcelable(SELECTED_RECIPE);
    }

    ButterKnife.bind(this);
    setSupportActionBar(toolbar);

    recipeStepFragment = (RecipeStepFragment) fragmentManager.findFragmentByTag("RecipeStepFragment");

    if(recipeStepFragment != null && getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
    {
      getSupportActionBar().hide();
    } else
    {
      getSupportActionBar().setHomeButtonEnabled(true);
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      if(recipe != null)
      {
        getSupportActionBar().setTitle(recipe.getName());
      }
    }

    toolbar.setNavigationOnClickListener(new View.OnClickListener()
    {
      @Override
      public void onClick(View v)
      {
        goBack();
      }
    });
  }

  @Override
  protected void onSaveInstanceState(Bundle outState)
  {
    super.onSaveInstanceState(outState);
    outState.putParcelable(SELECTED_RECIPE, recipe);
  }

  public void goBack()
  {
    if(findViewById(R.id.fragment_container2) == null)
    {
      if(fragmentManager.getBackStackEntryCount() > 1)
      {
        fragmentManager.popBackStack(RECIPE_DETAIL, 0);
      } else if(fragmentManager.getBackStackEntryCount() > 0)
      {
        finish();
      }

    } else
    {
      finish();
    }
  }

  @Override
  public void onBackPressed()
  {
    goBack();
  }
}
