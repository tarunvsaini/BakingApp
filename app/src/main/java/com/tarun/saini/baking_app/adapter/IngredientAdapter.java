package com.tarun.saini.baking_app.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tarun.saini.baking_app.Model.Ingredient;
import com.tarun.saini.baking_app.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tarun on 23-06-2017.
 */

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private ArrayList<Ingredient> mIngredients;

    public IngredientAdapter(ArrayList<Ingredient> ingredients)
    {
        this.mIngredients=ingredients;
    }


    @Override
    public IngredientViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        final View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_layout,parent,false);

        final IngredientViewHolder vh=new IngredientViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                vh.mCheckBox.setChecked(!vh.mCheckBox.isChecked());

            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(final IngredientViewHolder holder, int position)
    {
        Ingredient count=mIngredients.get(position);

        holder.ingredient.setText(count.recipeIngredient());
        if (position % 2==0)
        {
            holder.mIngredientLayout.setBackgroundColor(Color.parseColor("#eeeeee"));
        }
        else
            {
                holder.mIngredientLayout.setBackgroundColor(Color.parseColor("#fafafa"));

            }


    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }




    static class IngredientViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ingredient)
        TextView ingredient;
        @BindView(R.id.checkboxIngredient)
        CheckBox mCheckBox;
        @BindView(R.id.ingredient_container)
        LinearLayout mIngredientLayout;


        IngredientViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);



        }
    }
}
