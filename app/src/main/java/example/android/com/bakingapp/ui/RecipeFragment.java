
package example.android.com.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import example.android.com.bakingapp.R;
import example.android.com.bakingapp.adapter.RecipeAdapter;
import example.android.com.bakingapp.model.Recipe;
import example.android.com.bakingapp.retrofit.RecipeApi;
import example.android.com.bakingapp.retrofit.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static example.android.com.bakingapp.util.MyConstants.ALL_RECIPES;
import static example.android.com.bakingapp.util.MyConstants.SELECTED_RECIPE;


/**
 * Created by Deniz Kalem on 06.10.17.
 */

public class RecipeFragment extends Fragment
{
  RecipeAdapter recipesAdapter;
  public static boolean isTablet;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    RecyclerView recyclerView;

    View rootView = inflater.inflate(R.layout.recipe_fragment_body_part, container, false);

    recyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_recycler);
    recipesAdapter = new RecipeAdapter(getActivity(), this::click);
    recyclerView.setAdapter(recipesAdapter);

    isTablet = getResources().getBoolean(R.bool.isTablet);

    if(RecipeFragment.isTablet)
    {
      GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
      recyclerView.setLayoutManager(mLayoutManager);
    } else
    {
      LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
      recyclerView.setLayoutManager(mLayoutManager);
    }

    RecipeApi recipeApi = RetrofitBuilder.Retrieve();
    Call<ArrayList<Recipe>> recipe = recipeApi.getRecipe();

    recipe.enqueue(new Callback<ArrayList<Recipe>>()
    {
      @Override
      public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response)
      {
        ArrayList<Recipe> recipes = response.body();

        Bundle recipesBundle = new Bundle();
        recipesBundle.putParcelableArrayList(ALL_RECIPES, recipes);

        recipesAdapter.setRecipes(recipes, getContext());
      }

      @Override
      public void onFailure(Call<ArrayList<Recipe>> call, Throwable t)
      {
        Log.v("http fail: ", t.getMessage());
      }
    });

    return rootView;
  }

  public void click(Integer i)
  {
    Bundle bundle = new Bundle();
    bundle.putParcelable(SELECTED_RECIPE, recipesAdapter.getSelectedRecipe(i));

    Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
    intent.putExtras(bundle);
    startActivity(intent);
  }

}
