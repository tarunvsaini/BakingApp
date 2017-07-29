package com.tarun.saini.baking_app.ui;


import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tarun.saini.baking_app.Model.Ingredient;
import com.tarun.saini.baking_app.Model.Recipe;
import com.tarun.saini.baking_app.Model.Step;
import com.tarun.saini.baking_app.R;
import com.tarun.saini.baking_app.adapter.IngredientAdapter;
import com.tarun.saini.baking_app.adapter.StepAdapter;
import com.tarun.saini.baking_app.widget.IngredientWidget;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.tarun.saini.baking_app.adapter.RecipeAdapter.RECIPE;
import static com.tarun.saini.baking_app.data.IngredientContract.INGREDIENT_CONTENT_URI;
import static com.tarun.saini.baking_app.data.IngredientContract.IngredientEntry.KEY_ID;
import static com.tarun.saini.baking_app.data.IngredientContract.IngredientEntry.KEY_INGREDIENT;
import static com.tarun.saini.baking_app.data.IngredientContract.IngredientEntry.KEY_MEASURE;
import static com.tarun.saini.baking_app.data.IngredientContract.IngredientEntry.KEY_NAME;
import static com.tarun.saini.baking_app.data.IngredientContract.IngredientEntry.KEY_QUANTITY;
import static com.tarun.saini.baking_app.ui.RecipeListActivity.PANES;
import static com.tarun.saini.baking_app.ui.RecipeListActivity.POSITION;
import static com.tarun.saini.baking_app.ui.RecipeListActivity.RECIPE_ID;
import static com.tarun.saini.baking_app.ui.RecipeListActivity.RECIPE_INGREDIENTS;
import static com.tarun.saini.baking_app.ui.RecipeListActivity.RECIPE_NAME;
import static com.tarun.saini.baking_app.ui.RecipeListActivity.RECIPE_SERVES;
import static com.tarun.saini.baking_app.ui.RecipeListActivity.RECIPE_STEPS;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {
    private static final String SAVE_STEPS = "saveSteps";
    private static final String SAVE_INGREDIENT = "saveIngredients";
    private static final String SAVE_NAME = "saveRecipeName";
    private static final String SAVE_SERVES = "Saveserves";
    public static final String STEPS = "steps";
    private static final String SAVE_STATE_LAYOUT_MANAGER_STEPS = "step_recyclerView_state";
    private static final String SAVE_STATE_LAYOUT_MANAGER_INGREDIENTS = "ingredient_recyclerView_state";
    private static final String ARTICLE_SCROLL_POSITION = "ARTICLE_SCROLL_POSITION";
    private static final String SAVE_COLLAPSE_STATE = "collapsed";


    public Typeface lato_regular, lato_bold, lato_black = null;

    @BindView(R.id.recipeName)
    TextView recipeName;
    @BindView(R.id.serves)
    TextView serves;
    @BindView(R.id.ingredient_title)
    TextView ingredient_title;
    @BindView(R.id.steps_title)
    TextView directions;
    @BindView(R.id.show_steps)
    Button showStep;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.ingredient_recyclerView)
    RecyclerView ingredientRecyclerView;
    @BindView(R.id.steps_recyclerView)
    RecyclerView stepRecyclerView;
    @BindView(R.id.addWidgetBox)
    CheckBox addWidget;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.backdrop)
    ImageView mBackdrop;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nestedScrollView)
    NestedScrollView mScrollView;
    @BindView(R.id.appBar)
    AppBarLayout appBar;
    boolean collapsed;
    int mScrollPositionStep, mScrollPositionIngredient;


    private Recipe mRecipe;
    Boolean mTwoPane;
    boolean isShow;
    ArrayList<Step> steps;
    ArrayList<Ingredient> ingredients;
    String name;
    int servings;
    int id;
    int[] img_resources = {R.drawable.nutella, R.drawable.brownie, R.drawable.yelloc, R.drawable.cheesecake};
    LinearLayoutManager stepLinerLayout, ingredientLinerLayout;

    public DetailFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        ButterKnife.bind(this, rootView);
        lato_regular = Typeface.createFromAsset(getActivity().getAssets(), "lato_regular.ttf");
        lato_bold = Typeface.createFromAsset(getActivity().getAssets(), "lato_bold.ttf");
        lato_black = Typeface.createFromAsset(getActivity().getAssets(), "lato_black.ttf");

        recipeName.setTypeface(lato_black);
        serves.setTypeface(lato_regular);
        ingredient_title.setTypeface(lato_black);
        directions.setTypeface(lato_black);

        ingredientRecyclerView
                .setNestedScrollingEnabled(false);
        ingredientRecyclerView
                .setHasFixedSize(true);
        ingredientRecyclerView
                .setRecycledViewPool(new RecyclerView.RecycledViewPool());
        ingredientLinerLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        ingredientRecyclerView
                .setLayoutManager(ingredientLinerLayout);


        stepRecyclerView
                .setNestedScrollingEnabled(false);
        stepRecyclerView
                .setHasFixedSize(true);
        stepRecyclerView
                .setRecycledViewPool(new RecyclerView.RecycledViewPool());
        stepLinerLayout = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        stepRecyclerView
                .setLayoutManager(stepLinerLayout);


        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        addWidget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((addWidget.isChecked())) //check for condition if movie in database
                {

                    addIngredient(ingredients);
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Added to Home Screen Widget", Snackbar.LENGTH_LONG);


                    snackbar.show();

                } else {
                    deleteIngredient();
                    Snackbar snackbar = Snackbar
                            .make(coordinatorLayout, "Removed From Home Screen Widget", Snackbar.LENGTH_LONG);
                    // favBox.setButtonDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_favorite_border_red_500_18dp));

                    snackbar.show();

                }

            }
        });


        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) rootView.findViewById(R.id.appBar);
        appBarLayout.setExpanded(true);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {


                if (!mTwoPane) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout.getTotalScrollRange();
                    }

                    if (scrollRange + verticalOffset == 0) {

                        collapsingToolbar.setTitle(mRecipe.getName());
                        isShow = true;
                        collapsed = false;
                    } else if (isShow) {
                        collapsingToolbar.setTitle(" ");
                        isShow = false;
                        collapsed = true;
                    }
                } else {
                    if (verticalOffset == 0) {

                        collapsed = true;
                    } else {
                        collapsed = false;
                    }

                }

            }


        });


        mTwoPane = getArguments().getBoolean(PANES);

        if (savedInstanceState != null) {

            mScrollPositionStep = savedInstanceState.getInt(SAVE_STATE_LAYOUT_MANAGER_STEPS);
            mScrollPositionIngredient = savedInstanceState.getInt(SAVE_STATE_LAYOUT_MANAGER_INGREDIENTS);
            name = savedInstanceState.getString(SAVE_NAME);
            servings = savedInstanceState.getInt(SAVE_SERVES);
            steps = savedInstanceState.getParcelableArrayList(SAVE_STEPS);
            ingredients = savedInstanceState.getParcelableArrayList(SAVE_INGREDIENT);
            getRecipeDetails();
            collapsed = savedInstanceState.getBoolean(SAVE_COLLAPSE_STATE);
            appBar.setExpanded(collapsed);

            //Toast.makeText(getActivity(), collapsed+"", Toast.LENGTH_SHORT).show();

            final int[] position = savedInstanceState.getIntArray(ARTICLE_SCROLL_POSITION);
            if (position != null) {
                mScrollView.post(new Runnable() {
                    public void run() {
                        mScrollView.scrollTo(position[1], position[1]);
                    }
                });
            }


        } else {
            getRecipeDetails();
        }


        showStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent showStep = new Intent(getContext(), StepsActivity.class);
                showStep.putParcelableArrayListExtra(STEPS, steps);
                startActivity(showStep);
            }
        });


        if (checkIfAdded(name)) {
            addWidget.setChecked(true);
            // Toast.makeText(getActivity(), "ALREADY IN DATABASE", Toast.LENGTH_LONG).show();
        } else {
            addWidget.setChecked(false);
            // Toast.makeText(getActivity(), "NOT IN DATABASE", Toast.LENGTH_LONG).show();
        }


        return rootView;
    }


    public void phoneView() {
        mRecipe = getActivity().getIntent().getParcelableExtra(RECIPE);
        name = mRecipe.getName();
        servings = mRecipe.getServings();
        ingredients = mRecipe.getIngredients();
        steps = mRecipe.getSteps();
        id = mRecipe.getId();


    }


    public void tabView() {
        ingredients = getArguments().getParcelableArrayList(RECIPE_INGREDIENTS);
        steps = getArguments().getParcelableArrayList(RECIPE_STEPS);
        int position = getArguments().getInt(POSITION);
        name = getArguments().getString(RECIPE_NAME);
        servings = getArguments().getInt(RECIPE_SERVES);
        id = getArguments().getInt(RECIPE_ID);
        toolbar.setVisibility(View.GONE);

    }

    public void getRecipeDetails() {

        if (mTwoPane) {
            tabView();
        } else {
            phoneView();
        }

        recipeName.setText(name);
        serves.setText("Serves: " + String.valueOf(servings));
        IngredientAdapter mIngredientAdapter = new IngredientAdapter(ingredients);
        ingredientRecyclerView.setAdapter(mIngredientAdapter);
        StepAdapter mStepAdapter = new StepAdapter(steps);
        stepRecyclerView.setAdapter(mStepAdapter);


        if (id == 1) {
            Glide.with(getActivity()).load(img_resources[0]).into(mBackdrop);
        } else if (id == 2) {

            Glide.with(getActivity()).load(img_resources[1]).into(mBackdrop);
        } else if (id == 3) {

            Glide.with(getActivity()).load(img_resources[2]).into(mBackdrop);
        } else if (id == 4) {

            Glide.with(getActivity()).load(img_resources[3]).into(mBackdrop);
        }

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVE_NAME, name);
        outState.putInt(SAVE_SERVES, servings);
        outState.putParcelableArrayList(SAVE_STEPS, steps);
        outState.putParcelableArrayList(SAVE_INGREDIENT, ingredients);
        outState.putBoolean(SAVE_COLLAPSE_STATE, collapsed);
        outState.putIntArray(ARTICLE_SCROLL_POSITION,
                new int[]{mScrollView.getScrollX(), mScrollView.getScrollY()});

    }


    private void addIngredient(ArrayList<Ingredient> ingredients) {
        deleteIngredient();

        for (Ingredient i : ingredients) {
            ContentValues values = new ContentValues();
            values.put(KEY_ID, id);
            values.put(KEY_NAME, name);
            values.put(KEY_MEASURE, i.getMeasure());
            values.put(KEY_QUANTITY, i.getQuantity());
            values.put(KEY_INGREDIENT, i.getIngredient());
            getActivity().getContentResolver().insert(INGREDIENT_CONTENT_URI, values);
        }


    }

    private boolean checkIfAdded(String name) {
        String[] projection = {KEY_NAME};
        String selection = KEY_NAME + "=?";
        String[] selectionArgs = new String[]{name};
        Cursor cursor = getActivity().getContentResolver()
                .query(INGREDIENT_CONTENT_URI, projection, selection, selectionArgs, null);
        assert cursor != null;
        if (cursor.getCount() <= 0) {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }

    public void deleteIngredient() {

        getActivity().getContentResolver().delete(INGREDIENT_CONTENT_URI, null, null);
        IngredientWidget.sendRefreshBroadcast(getActivity());
    }


}
