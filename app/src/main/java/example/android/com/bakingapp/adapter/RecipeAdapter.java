package example.android.com.bakingapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import example.android.com.bakingapp.R;
import example.android.com.bakingapp.model.Recipe;
import example.android.com.bakingapp.util.Lambda;

/**
 * Created by Deniz Kalem on 06.10.17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{
  ArrayList<Recipe> recipes;
  Lambda<Integer> itemClickHandler;
  Context context;

  public RecipeAdapter(Lambda<Integer> itemClickHandler)
  {
    this.itemClickHandler = itemClickHandler;
  }

  public void setRecipes(ArrayList<Recipe> recipes, Context context)
  {
    this.recipes = recipes;
    this.context = context;
    notifyDataSetChanged();
  }

  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    int layoutIdForListItem = R.layout.recipe_item;

    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(layoutIdForListItem, parent, false);
    RecyclerView.ViewHolder viewHolder = new ItemHolder(view, (i) -> itemClickHandler.execute(i));

    return viewHolder;
  }

  @Override
  public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
  {
    ItemHolder itemHolder = (ItemHolder) holder;
    itemHolder.text.setText(recipes.get(position).getName());
    String imageUrl = recipes.get(position).getImage();

    if(imageUrl != "")
    {
      Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
      Picasso.with(context).load(builtUri).into(itemHolder.image);
    }
  }

  @Override
  public int getItemCount()
  {
    return recipes != null ? recipes.size() : 0;
  }

}
