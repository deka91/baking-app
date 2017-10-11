package example.android.com.bakingapp.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import example.android.com.bakingapp.R;

/**
 * Created by Deniz Kalem on 05.10.17.
 */

public class RecipeActivity extends AppCompatActivity
{
  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @Override
  public void onCreate(Bundle savedInstanceState)
  {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe);

    ButterKnife.bind(this);
    setSupportActionBar(toolbar);
    getSupportActionBar().setHomeButtonEnabled(false);
    getSupportActionBar().setDisplayHomeAsUpEnabled(false);
  }

  @Override
  protected void onSaveInstanceState(Bundle outState)
  {
    super.onSaveInstanceState(outState);
  }
}
