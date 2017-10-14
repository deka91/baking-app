
package example.android.com.bakingapp.ui;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
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
  private ArrayList<Recipe> recipes;

  @BindView(R.id.recipe_recycler)
  RecyclerView recyclerView;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
  {
    View rootView = inflater.inflate(R.layout.recipe_fragment_body_part, container, false);

    ButterKnife.bind(this, rootView);
    recipesAdapter = new RecipeAdapter(getActivity(), this::clickRecipe);
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

    if(savedInstanceState != null && savedInstanceState.getParcelableArrayList(ALL_RECIPES) != null)
    {
      recipes = savedInstanceState.getParcelableArrayList(ALL_RECIPES);
      recipesAdapter.setRecipes(recipes, getContext());
    } else
    {
      ProgressDialog progressDialog = new ProgressDialog(getActivity());
      progressDialog.setIndeterminate(true);
      progressDialog.setMessage(getString(R.string.loading_dialog));
      progressDialog.show();

      RecipeApi recipeApi = RetrofitBuilder.getData();
      Call<ArrayList<Recipe>> recipe = recipeApi.getRecipe();

      if(isNetworkAvailable())
      {
        recipe.enqueue(new Callback<ArrayList<Recipe>>()
        {
          @Override
          public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response)
          {
            if(progressDialog.isShowing())
            {
              progressDialog.dismiss();
            }

            recipes = response.body();

            Bundle recipesBundle = new Bundle();
            recipesBundle.putParcelableArrayList(ALL_RECIPES, recipes);

            recipesAdapter.setRecipes(recipes, getContext());
          }


          @Override
          public void onFailure(Call<ArrayList<Recipe>> call, Throwable t)
          {
            if(progressDialog.isShowing())
            {
              progressDialog.dismiss();
            }

            Toast.makeText(getActivity(), "An error has occured.", Toast.LENGTH_SHORT);
          }
        });
      }
    }

    return rootView;
  }

  public void clickRecipe(Integer i)
  {
    Bundle bundle = new Bundle();
    bundle.putParcelable(SELECTED_RECIPE, recipesAdapter.getSelectedRecipe(i));

    Intent intent = new Intent(getActivity(), RecipeDetailActivity.class);
    intent.putExtras(bundle);
    startActivity(intent);
  }

  @Override
  public void onSaveInstanceState(Bundle outState)
  {
    super.onSaveInstanceState(outState);
    outState.putParcelableArrayList(ALL_RECIPES, recipes);
  }

  public boolean isNetworkAvailable()
  {
    ConnectivityManager connectivityManager
      = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
  }


}
