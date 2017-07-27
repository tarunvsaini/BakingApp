package com.tarun.saini.baking_app.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tarun.saini.baking_app.Model.Step;
import com.tarun.saini.baking_app.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tarun on 23-06-2017.
 */

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.StepViewHolder> {

    private ArrayList<Step> mSteps;

    public StepAdapter(ArrayList<Step> steps)
    {
        this.mSteps=steps;
    }



    @Override
    public StepAdapter.StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.step_layout,parent,false);
        return new StepViewHolder(view);

    }

    @Override
    public void onBindViewHolder(StepAdapter.StepViewHolder holder, int position) {

        Step count=mSteps.get(position+1);
        holder.descriptionSteps.setText(count.getDescription());
        //holder.step_number.setText(String.valueOf(count.getId()+1));
        if (position % 2==0)
        {
            holder.mStepLayout.setBackgroundColor(Color.parseColor("#eeeeee"));
        }
        else
        {
            holder.mStepLayout.setBackgroundColor(Color.parseColor("#fafafa"));

        }


    }

    @Override
    public int getItemCount() {
        return mSteps.size()-1;
    }

    class StepViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.step_description)
        TextView descriptionSteps;
        @BindView(R.id.step_container)
        LinearLayout mStepLayout;



        StepViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
