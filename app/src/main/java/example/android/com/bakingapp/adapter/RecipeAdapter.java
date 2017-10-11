package example.android.com.bakingapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.com.bakingapp.R;
import example.android.com.bakingapp.model.Recipe;
import example.android.com.bakingapp.util.Lambda;

/**
 * Created by Deniz Kalem on 06.10.17.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ItemHolder>
{
  ArrayList<Recipe> recipes;
  Lambda<Integer> itemClickHandler;
  Context context;
  int selectedRecipePosition = 0;

  public RecipeAdapter(Context context, Lambda<Integer> itemClickHandler)
  {
    this.context = context;
    this.itemClickHandler = itemClickHandler;
  }

  public void setRecipes(ArrayList<Recipe> recipes, Context context)
  {
    this.recipes = recipes;
    this.context = context;
    notifyDataSetChanged();
  }

  @Override
  public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    int layoutIdForListItem = R.layout.recipe_item;

    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(layoutIdForListItem, parent, false);
    ItemHolder viewHolder = new ItemHolder(view, (i) -> itemClickHandler.execute(i));

    return viewHolder;
  }

  @Override
  public void onBindViewHolder(ItemHolder holder, int position)
  {
    ItemHolder recipItemHolder = (ItemHolder) holder;
    recipItemHolder.text.setText(recipes.get(position).getName());
    String imageUrl = recipes.get(position).getImage();

    if(imageUrl != "")
    {
      Uri builtUri = Uri.parse(imageUrl).buildUpon().build();
      Picasso.with(context).load(builtUri).into(recipItemHolder.image);
    }

    recipItemHolder.updateIndex(position);
  }

  @Override
  public int getItemCount()
  {
    return recipes != null ? recipes.size() : 0;
  }

  public Recipe getSelectedRecipe(int position)
  {
    return recipes.get(position);
  }


  public class ItemHolder extends RecyclerView.ViewHolder
  {
    @BindView(R.id.tv_text)
    TextView text;
    @BindView(R.id.iv_image)
    ImageView image;
    Lambda<Integer> clickHandler;
    int index;


    public ItemHolder(View itemView, Lambda<Integer> clickHandler)
    {
      super(itemView);
      this.clickHandler = clickHandler;
      ButterKnife.bind(this, itemView);
      itemView.setOnClickListener(this::click);
    }

    public void updateIndex(int index)
    {
      this.index = index;
    }

    public void click(View v)
    {
      // Updating old as well as new positions
      notifyItemChanged(selectedRecipePosition);
      selectedRecipePosition = getAdapterPosition();
      notifyItemChanged(selectedRecipePosition);

      clickHandler.execute(index);
    }
  }

}
