package example.android.com.bakingapp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Deniz Kalem on 06.10.17.
 */

public class RetrofitBuilder
{
  static RecipeApi recipeApi;

  public static RecipeApi Retrieve()
  {
    Gson gson = new GsonBuilder().create();

    OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

    recipeApi = new Retrofit.Builder()
      .baseUrl("https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/")
      .addConverterFactory(GsonConverterFactory.create(gson))
      .callFactory(httpClientBuilder.build())
      .build().create(RecipeApi.class);

    return recipeApi;
  }
}
