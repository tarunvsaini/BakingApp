package com.tarun.saini.baking_app.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.tarun.saini.baking_app.Model.Recipe;
import com.tarun.saini.baking_app.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tarun on 22-06-2017.
 */

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {


    public static final String RECIPE = "recipe";
    private ArrayList<Recipe> mRecipes;
    private Context mContext;
    private final OnRecipeClickListener mClickListener;
    private int[] img_resources={R.drawable.nutella,R.drawable.brownie,R.drawable.yelloc,R.drawable.cheesecake};



        public interface OnRecipeClickListener
        {
            void onRecipeSelected(int position,List<Recipe> recipe);
        }

    public RecipeAdapter(ArrayList<Recipe> mRecipes,Context context,OnRecipeClickListener listener)
    {
        this.mRecipes=mRecipes;
        this.mContext=context;
        mClickListener=listener;

    }
    @Override
    public RecipeAdapter.RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View mView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_card, parent, false);
         return new RecipeViewHolder(mView);

    }

    @Override
    public void onBindViewHolder(RecipeAdapter.RecipeViewHolder holder, int position) {
        Recipe current=mRecipes.get(position);
        holder.recipeName2.setTypeface(Typeface.createFromAsset(mContext.getAssets(),"lato_black.ttf"));
        holder.recipeName.setTypeface(Typeface.createFromAsset(mContext.getAssets(),"lato_bold.ttf"));
        holder.serving.setTypeface(Typeface.createFromAsset(mContext.getAssets(),"lato_regular.ttf"));
        holder.recipeName2.setText(current.getName());
        holder.recipeName.setText(current.getName());
        holder.serving.setText("Servings: "+String.valueOf(current.getServings()));
        for(int i=0;i<getItemCount();i++) {
            if (position == i) {
                Glide.with(mContext).load(img_resources[i]).into(holder.recipe_background);
            }
        }



    }

    @Override
    public int getItemCount() {
        Log.d("SIZE",mRecipes.size()+"");
        return mRecipes.size();
    }



    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.recipeNameList) TextView recipeName;
        @BindView(R.id.serving) TextView serving;
        @BindView(R.id.recipeName2) TextView recipeName2;
        @BindView(R.id.backdrop) ImageView recipe_background;



        RecipeViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            int position=getAdapterPosition();
            mClickListener.onRecipeSelected(position,mRecipes);
        }


    }
}
