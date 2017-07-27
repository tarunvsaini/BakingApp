package com.tarun.saini.baking_app.ui;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.tarun.saini.baking_app.Model.Recipe;
import com.tarun.saini.baking_app.R;

import static com.tarun.saini.baking_app.adapter.RecipeAdapter.RECIPE;
import static com.tarun.saini.baking_app.ui.RecipeListActivity.PANES;

public class DetailActivity extends AppCompatActivity {

    private static final String SAVE_STATE = "save_state";
    private Recipe mRecipe;
    public static final String STEPS = "steps";
    Fragment detailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mRecipe = getIntent().getParcelableExtra(RECIPE);
        boolean mTwoPane = getIntent().getBooleanExtra(PANES, false);
        Bundle bundle = new Bundle();

        if (savedInstanceState == null) {
            detailFragment = new DetailFragment();
            bundle.putBoolean(PANES, mTwoPane);
            detailFragment.setArguments(bundle);

            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.recipe_detail_container, detailFragment);
            transaction.commit();
        } else {
            if (detailFragment != null && detailFragment.isAdded()) {
                detailFragment = getSupportFragmentManager().getFragment(savedInstanceState, SAVE_STATE);
            }
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if (detailFragment != null && detailFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, SAVE_STATE, detailFragment);
        }
    }
}
