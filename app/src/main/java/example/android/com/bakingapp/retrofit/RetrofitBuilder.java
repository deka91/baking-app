package example.android.com.bakingapp.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import example.android.com.bakingapp.util.MyConstants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Deniz Kalem on 06.10.17.
 */

public class RetrofitBuilder
{
  static RecipeApi recipeApi;

  public static RecipeApi getData()
  {
    Gson gson = new GsonBuilder().create();

    OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

    recipeApi = new Retrofit.Builder()
      .baseUrl(MyConstants.BASE_URL)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .callFactory(httpClientBuilder.build())
      .build().create(RecipeApi.class);

    return recipeApi;
  }
}
