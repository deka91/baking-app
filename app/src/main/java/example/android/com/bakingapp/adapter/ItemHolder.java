package example.android.com.bakingapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import example.android.com.bakingapp.R;
import example.android.com.bakingapp.util.Lambda;

/**
 * Created by Deniz Kalem on 06.10.17.
 */

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

  }

  @OnClick(R.id.layout_recipe_item)
  public void click()
  {
    clickHandler.execute(index);
  }

}