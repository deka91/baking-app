package example.android.com.bakingapp.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import example.android.com.bakingapp.R;
import example.android.com.bakingapp.adapter.RecipeDetailAdapter;
import example.android.com.bakingapp.model.Ingredient;
import example.android.com.bakingapp.model.Recipe;
import example.android.com.bakingapp.widget.BakingWidgetService;

import static example.android.com.bakingapp.util.MyConstants.ALL_STEPS;
import static example.android.com.bakingapp.util.MyConstants.SELECTED_RECIPE;
import static example.android.com.bakingapp.util.MyConstants.SELECTED_STEP;
import static example.android.com.bakingapp.util.MyConstants.RECIPE_STEP;

/**
 * Created by Deniz Kalem on 05.10.17.
 */

public class RecipeDetailFragment extends Fragment
{
  private Recipe recipe;
  RecipeDetailAdapter recipeDetailAdapter;

  @Nullable
  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
  {
    RecyclerView recyclerView;
    TextView textView;

    if(savedInstanceState != null)
    {
      recipe = savedInstanceState.getParcelable(SELECTED_RECIPE);

    } else
    {
      recipe = getArguments().getParcelable(SELECTED_RECIPE);
    }

    List<Ingredient> ingredients = recipe.getIngredients();

    View rootView = inflater.inflate(R.layout.recipe_detail_fragment_body_part, container, false);
    textView = (TextView) rootView.findViewById(R.id.tv_ingredients);

    for(Ingredient ingredient : ingredients)
    {
      textView.append(ingredient.getIngredient() + "\n");
      textView.append("Quantity: " + ingredient.getQuantity().toString() + "\n");
      textView.append("Measure: " + ingredient.getMeasure() + "\n\n");
    }

    recyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_detail_recycler);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    recyclerView.setLayoutManager(linearLayoutManager);

    recipeDetailAdapter = new RecipeDetailAdapter(this::clickStep);
    recyclerView.setAdapter(recipeDetailAdapter);
    recipeDetailAdapter.setMasterRecipeData(recipe);

    BakingWidgetService.startActionUpdateRecipe(getActivity(), recipe);

    return rootView;
  }


  public void clickStep(Integer i)
  {
    RecipeStepFragment fragment = new RecipeStepFragment();
    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

    Bundle bundle = new Bundle();
    bundle.putParcelable(SELECTED_STEP, recipeDetailAdapter.getSelectedStep(i));
    bundle.putParcelableArrayList(ALL_STEPS, (ArrayList) recipeDetailAdapter.getSteps());
    fragment.setArguments(bundle);

    if(RecipeFragment.isTablet && getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
    {
      fragmentManager.beginTransaction()
        .replace(R.id.fragment_container2, fragment).addToBackStack(RECIPE_STEP)
        .commit();
    } else
    {
      fragmentManager.beginTransaction()
        .replace(R.id.fragment_container, fragment, "RecipeStepFragment").addToBackStack(RECIPE_STEP)
        .commit();
    }
  }

  @Override
  public void onSaveInstanceState(Bundle currentState)
  {
    super.onSaveInstanceState(currentState);
    currentState.putParcelable(SELECTED_RECIPE, recipe);
  }
}
