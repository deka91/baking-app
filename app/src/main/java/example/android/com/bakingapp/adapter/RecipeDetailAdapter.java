package example.android.com.bakingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.com.bakingapp.R;
import example.android.com.bakingapp.model.Recipe;
import example.android.com.bakingapp.model.Step;
import example.android.com.bakingapp.util.Lambda;

/**
 * Created by Deniz Kalem on 06.10.17.
 */

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.ItemHolder>
{
  List<Step> steps;
  String recipeName;
  Lambda<Integer> itemClickHandler;
  int selectedStepPosition = 0;

  public RecipeDetailAdapter( Lambda<Integer> itemClickHandler)
  {
    this.itemClickHandler = itemClickHandler;
  }

  public void setMasterRecipeData(Recipe recipe)
  {
    steps = recipe.getSteps();
    recipeName = recipe.getName();
    notifyDataSetChanged();
  }

  public List<Step> getSteps()
  {
    return steps;
  }

  @Override
  public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType)
  {
    int layoutIdForListItem = R.layout.recipe_detail_item;

    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(layoutIdForListItem, parent, false);
    ItemHolder viewHolder = new ItemHolder(view, (i) -> itemClickHandler.execute(i));

    return viewHolder;
  }

  @Override
  public void onBindViewHolder(ItemHolder holder, int position)
  {
    ItemHolder itemHolder = (ItemHolder) holder;
    itemHolder.step.setText(steps.get(position).getId() + ". " + steps.get(position).getShortDescription());

    itemHolder.updateIndex(position);
  }

  @Override
  public int getItemCount()
  {
    return steps != null ? steps.size() : 0;
  }

  public Step getSelectedStep(int position)
  {
    return steps.get(position);
  }


  public class ItemHolder extends RecyclerView.ViewHolder
  {
    @BindView(R.id.tv_step)
    TextView step;
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
      notifyItemChanged(selectedStepPosition);
      selectedStepPosition = getAdapterPosition();
      notifyItemChanged(selectedStepPosition);

      clickHandler.execute(index);
    }

  }
}
