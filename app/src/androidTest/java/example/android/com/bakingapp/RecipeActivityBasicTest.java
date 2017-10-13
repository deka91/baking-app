package example.android.com.bakingapp;

import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.Toolbar;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import example.android.com.bakingapp.ui.RecipeActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.Is.is;

/**
 * Created by Deniz Kalem on 13.10.17.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeActivityBasicTest
{
  public static final String RECIPE_NAME = "Cheesecake";

  @Rule
  public ActivityTestRule<RecipeActivity> activityTestRule = new ActivityTestRule<RecipeActivity>(RecipeActivity.class);

  @Test
  public void clickRecipe()
  {
    try
    {
      Thread.sleep(3000);
    } catch(InterruptedException e)
    {
      e.printStackTrace();
    }

    onView(withId(R.id.recipe_recycler))
      .perform(RecyclerViewActions.actionOnItem(
        hasDescendant(withText(RECIPE_NAME)), click()));

    matchToolbarTitle(RECIPE_NAME);
  }


  private static ViewInteraction matchToolbarTitle(CharSequence title)
  {
    return onView(isAssignableFrom(Toolbar.class))
      .check(matches(withToolbarTitle(is(title))));
  }

  private static Matcher<Object> withToolbarTitle(final Matcher<CharSequence> textMatcher)
  {
    return new BoundedMatcher<Object, Toolbar>(Toolbar.class)
    {
      @Override
      public boolean matchesSafely(Toolbar toolbar)
      {
        return textMatcher.matches(toolbar.getTitle());
      }

      @Override
      public void describeTo(Description description)
      {
        description.appendText("with toolbar title: ");
        textMatcher.describeTo(description);
      }
    };
  }
}
