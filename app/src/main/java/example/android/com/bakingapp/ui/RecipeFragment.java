
package example.android.com.bakingapp.ui;

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
import example.android.com.bakingapp.retrofit.RecipeAPI;
import example.android.com.bakingapp.retrofit.RetrofitBuilder;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static example.android.com.bakingapp.ui.RecipeActivity.ALL_RECIPES;

/**
 * Created by Deniz Kalem on 06.10.17.
 */

public class RecipeFragment extends Fragment
{

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    RecyclerView recyclerView;

    View rootView = inflater.inflate(R.layout.recipe_fragment_body_part, container, false);

    recyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_recycler);
    RecipeAdapter recipesAdapter = new RecipeAdapter(this::click);
    recyclerView.setAdapter(recipesAdapter);


    if(rootView.getTag() != null && rootView.getTag().equals("phone-land"))
    {
      GridLayoutManager mLayoutManager = new GridLayoutManager(getContext(), 4);
      recyclerView.setLayoutManager(mLayoutManager);
    } else
    {
      LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
      recyclerView.setLayoutManager(mLayoutManager);
    }

    RecipeAPI recipeAPI = RetrofitBuilder.Retrieve();
    Call<ArrayList<Recipe>> recipe = recipeAPI.getRecipe();

//    SimpleIdlingResource idlingResource = (SimpleIdlingResource) ((RecipeActivity) getActivity()).getIdlingResource();
//
//    if(idlingResource != null)
//    {
//      idlingResource.setIdleState(false);
//    }


    recipe.enqueue(new Callback<ArrayList<Recipe>>()
    {
      @Override
      public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response)
      {
        Integer statusCode = response.code();
        Log.v("status code: ", statusCode.toString());

        ArrayList<Recipe> recipes = response.body();

        Bundle recipesBundle = new Bundle();
        recipesBundle.putParcelableArrayList(ALL_RECIPES, recipes);

        recipesAdapter.setRecipes(recipes, getContext());
//        if(idlingResource != null)
//        {
//          idlingResource.setIdleState(true);
//        }

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

  }


}
