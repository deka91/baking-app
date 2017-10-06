package example.android.com.bakingapp.retrofit;

import java.util.ArrayList;

import example.android.com.bakingapp.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Deniz Kalem on 06.10.17.
 */

public interface RecipeAPI
{
  @GET("baking.json")
  Call<ArrayList<Recipe>> getRecipe();
}
