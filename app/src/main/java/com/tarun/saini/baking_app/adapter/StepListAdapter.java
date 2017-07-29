package com.tarun.saini.baking_app.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tarun.saini.baking_app.Model.Recipe;
import com.tarun.saini.baking_app.Model.Step;
import com.tarun.saini.baking_app.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tarun on 24-06-2017.
 */

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepListViewHolder> {


    private ArrayList<Step> mSteps;


    public static final String STEP="steps";
    public static final String STEPLIST="step_list";
    private final OnStepClickListener mClickListener;


    public interface OnStepClickListener
    {
        void onStepSelected(int position,ArrayList<Step> steps);
    }


    public StepListAdapter(ArrayList steps,OnStepClickListener listener)
    {
        this.mSteps=steps;
        mClickListener=listener;
    }
    @Override
    public StepListAdapter.StepListViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.steps_list_layout,parent,false);
        return new StepListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepListAdapter.StepListViewHolder holder, int position)
    {
        Step count=mSteps.get(position);
        holder.shortSteps.setText(count.getShortDescription());
        holder.step_number.setText(String.valueOf(count.getId()+1));

        if (count.getVideoURL().isEmpty() && count.getThumbnailURL().isEmpty())
        {
            holder.video.setVisibility(View.GONE);
        }



    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public class StepListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.short_step_description)
        TextView shortSteps;
        @BindView(R.id.step_number)
        TextView step_number;
        @BindView(R.id.video_present)
        ImageView video;

        public StepListViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this,itemView);

            itemView.setOnClickListener(this);



        }

        @Override
        public void onClick(View v)
        {
            int position=getAdapterPosition();
            mClickListener.onStepSelected(position,mSteps);


        }
    }
}
