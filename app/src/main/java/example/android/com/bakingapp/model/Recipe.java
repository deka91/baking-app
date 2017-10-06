package example.android.com.bakingapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Deniz Kalem on 06.10.17.
 */

public class Recipe implements Parcelable
{
  private Integer id;
  private String name;
  private List<Ingredient> ingredients;
  private List<Step> steps;
  private Integer servings;
  private String image;

  public Integer getId()
  {
    return id;
  }

  public void setId(Integer id)
  {
    this.id = id;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public List<Ingredient> getIngredients()
  {
    return ingredients;
  }

  public void setIngredients(List<Ingredient> ingredients)
  {
    this.ingredients = ingredients;
  }

  public List<Step> getSteps()
  {
    return steps;
  }

  public void setSteps(List<Step> steps)
  {
    this.steps = steps;
  }

  public Integer getServings()
  {
    return servings;
  }

  public void setServings(Integer servings)
  {
    this.servings = servings;
  }

  public String getImage()
  {
    return image;
  }

  public void setImage(String image)
  {
    this.image = image;
  }

  @Override
  public int describeContents()
  {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags)
  {
    dest.writeInt(this.id);
    dest.writeString(this.name);
    dest.writeList(this.ingredients);
    dest.writeList(this.steps);
    dest.writeInt(this.servings);
    dest.writeString(this.image);
  }

  public Recipe()
  {
  }

  protected Recipe(Parcel in)
  {
    this.id = in.readInt();
    this.name = in.readString();
    this.ingredients = new ArrayList<>();
    in.readList(this.ingredients, Ingredient.class.getClassLoader());
    this.steps = new ArrayList<>();
    in.readList(this.steps, Step.class.getClassLoader());
    this.servings = in.readInt();
    this.image = in.readString();
  }

  public static final Parcelable.Creator<Recipe> CREATOR = new Parcelable.Creator<Recipe>()
  {
    @Override
    public Recipe createFromParcel(Parcel source)
    {
      return new Recipe(source);
    }

    @Override
    public Recipe[] newArray(int size)
    {
      return new Recipe[size];
    }
  };
}
