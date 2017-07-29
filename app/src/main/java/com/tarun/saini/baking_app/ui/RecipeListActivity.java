package com.tarun.saini.baking_app.ui;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.tarun.saini.baking_app.adapter.RecipeAdapter;
import com.tarun.saini.baking_app.Model.Recipe;
import com.tarun.saini.baking_app.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tarun.saini.baking_app.adapter.RecipeAdapter.RECIPE;

public class RecipeListActivity extends AppCompatActivity implements RecipeAdapter.OnRecipeClickListener {


    private static final String SAVE_STATE = "myFragment";
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private boolean mTwoPane;
    public static final String POSITION = "position";
    public static final String PANES = "panes";
    public static final String RECIPE_STEPS = "recipe steps";
    public static final String RECIPE_INGREDIENTS = "recipe ingredients";
    public static final String RECIPE_NAME = "name";
    public static final String RECIPE_SERVES = "serves";
    public static final String RECIPE_ID = "id";
    private Fragment recipeListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        ButterKnife.bind(this);
        mToolbar.setTitle(R.string.app_name);
        setSupportActionBar(mToolbar);
        mToolbar.setFitsSystemWindows(true);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(true);
        tintManager.setTintColor(Color.parseColor("#20000000"));


        if (savedInstanceState == null) {
            recipeListFragment = new RecipeListFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.recipe_list_container, recipeListFragment);
            fragmentTransaction.commit();
        } else {
            if (recipeListFragment != null && recipeListFragment.isAdded()) {
                recipeListFragment = getSupportFragmentManager().getFragment(savedInstanceState, SAVE_STATE);

            }

        }


        if (mTwoPane = findViewById(R.id.recipe_linear_layout) != null) {
            mTwoPane = true;

        } else {
            mTwoPane = false;
        }


    }

    @Override
    public void onRecipeSelected(int position, List<Recipe> recipe) {


        Bundle bundle = new Bundle();
        if (mTwoPane) {

            DetailFragment detailFragment = new DetailFragment();
            bundle.putInt(POSITION, position);
            bundle.putBoolean(PANES, mTwoPane);
            bundle.putParcelableArrayList(RECIPE_STEPS, recipe.get(position).getSteps());
            bundle.putParcelableArrayList(RECIPE_INGREDIENTS, recipe.get(position).getIngredients());
            bundle.putString(RECIPE_NAME, recipe.get(position).getName());
            bundle.putInt(RECIPE_SERVES, recipe.get(position).getServings());
            bundle.putInt(RECIPE_ID, recipe.get(position).getId());
            detailFragment.setArguments(bundle);
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.recipe_detail_container, detailFragment);
            transaction.commit();
        } else {
            Intent intent = new Intent(this, DetailActivity.class);
            //bundle.putInt(POSITION, position);
            bundle.putBoolean(PANES, mTwoPane);
            intent.putExtra(RECIPE, recipe.get(position));
            intent.putExtras(bundle);
            startActivity(intent);

        }


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (recipeListFragment != null && recipeListFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, SAVE_STATE, recipeListFragment);

        }
    }

}
