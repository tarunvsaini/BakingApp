package com.tarun.saini.baking_app.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.tarun.saini.baking_app.adapter.RecipeAdapter;
import com.tarun.saini.baking_app.adapter.RecipeAdapter.OnRecipeClickListener;
import com.tarun.saini.baking_app.Model.Recipe;
import com.tarun.saini.baking_app.Network.RestManager;
import com.tarun.saini.baking_app.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecipeListFragment extends Fragment {

    private static final String SAVE_STATE ="save_state" ;
    private ArrayList<Recipe> mRecipeList;
    private Recipe mRecipe;
    public static final String TAG = RecipeListActivity.class.getSimpleName();
    @BindView(R.id.recipe_recyclerView) RecyclerView mRecyclerView;
    private RestManager manager;
    private Typeface lato_regular = null;
    private OnRecipeClickListener mCallBack;
    public int[] img_resources={R.drawable.nutella,R.drawable.brownie,R.drawable.yelloc,R.drawable.cheesecake};





    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallBack = (OnRecipeClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnRecipeClickListener");
        }
    }

    public RecipeListFragment()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);
        ButterKnife.bind(this,rootView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());
        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        lato_regular = Typeface.createFromAsset(getActivity().getAssets(), "lato_regular.ttf");




        if (savedInstanceState == null) {

            getRecipes();

        }
        else
        {
            mRecipeList = savedInstanceState.getParcelableArrayList(SAVE_STATE);
            if (mRecipeList != null)
            {    mRecyclerView.setAdapter(new RecipeAdapter(mRecipeList, getContext(),mCallBack));

            }

        }

        return rootView;


    }


    private void getRecipes()
    {

        manager = new RestManager();
        Call<ArrayList<Recipe>> listCall = manager.getRecipeService().getAllRecipes();
        listCall.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {

                mRecipeList = response.body();
                mRecyclerView.setAdapter(new RecipeAdapter(mRecipeList, getContext(),mCallBack));


            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {


                Toast.makeText(getContext(), t + "", Toast.LENGTH_LONG).show();
                Log.d("JSON fAILURE", t + "");


            }
        });


    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVE_STATE,mRecipeList);
    }
}



